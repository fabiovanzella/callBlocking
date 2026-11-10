# Design

## Context

- Projeto novo: o repositório só contém a configuração do OpenSpec, sem código Android.
- Ambiente de desenvolvimento verificado: há JDK 20 instalado, mas **nenhum Android SDK, `adb` ou Gradle** foi encontrado (`ANDROID_HOME` vazio, sem `~/Android/Sdk`). Instalar o Android Studio (que já traz SDK e JBR 21) é pré-requisito para compilar e instalar no aparelho.
- Aparelho-alvo: POCO X8 Pro com HyperOS (Android 15/16) e Telefone do Google como discador.
- Motivação e escopo: ver `proposal.md`. Comportamento exigido: ver `specs/`.

## Goals / Non-Goals

**Goals:**
- Decisão de bloqueio rápida e confiável dentro do limite de tempo que o sistema dá para triagem.
- Arquitetura pequena, sem frameworks além do necessário, fácil de manter por uma pessoa.
- Nenhum acesso a contatos nem à rede.

**Non-Goals:**
- Injeção de dependência com framework (Hilt/Koin), modularização em vários módulos Gradle.
- Ser o app de discador padrão (`ROLE_DIALER`).
- Suporte a Android < 10.

## Decisions

### D1. `CallScreeningService` + `ROLE_CALL_SCREENING`, sem `READ_CONTACTS`
O Telecom só passa para triagem as ligações de números fora da agenda quando o app não tem `READ_CONTACTS`. Assim, toda ligação que chega em `onScreenCall` já é de um desconhecido e a regra é só "bloqueio ativo? então rejeita".
- *Alternativa:* pedir `READ_CONTACTS` e consultar `PhoneLookup`. Rejeitada porque traz mais permissão sensível, normalização de números (+55, 0xx, código de operadora) e mais pontos de falha, sem benefício para o escopo.
- *Alternativa:* `ROLE_DIALER` (app de discador completo). Rejeitada porque exige implementar toda a UI de chamada.

Filtro de direção: tratar apenas `Call.Details.DIRECTION_INCOMING`. Ligações de saída recebem a resposta padrão (permitir).

### D2. Resposta de bloqueio
```
CallResponse.Builder()
  .setDisallowCall(true)
  .setRejectCall(true)
  .setSkipCallLog(false)       // aparece no histórico do sistema
  .setSkipNotification(true)   // sem notificação de chamada perdida do sistema
```
Com bloqueio desativado: `CallResponse.Builder().build()` (permitir, tudo padrão).

Número oculto: `details.handle == null`, `schemeSpecificPart` vazio ou `handlePresentation != PRESENTATION_ALLOWED`. Nesse caso o número é gravado como `null` e exibido como "Número oculto".

### D3. Ordem de execução no serviço
```
onScreenCall(details)
  |
  +-- não é entrada? --> respondToCall(allow); return
  |
  +-- launch(serviceScope) {
        enabled = settings.blockingEnabled.first()
        if (!enabled) { respond(allow); return }
        try {
          withTimeout(2s) {
            repo.insert(number, now)       // Room
            notifier.onBlocked(number, now)
          }
        } finally {
          respond(block)                   // sempre responde
        }
      }
```
Gravar **antes** de responder evita perder registros caso o processo seja encerrado logo após a resposta. O `withTimeout` + `finally` garante a resposta bem abaixo do limite de ~5 s do sistema mesmo se o disco estiver lento. O escopo do serviço é cancelado em `onDestroy`.
- *Alternativa:* responder primeiro e gravar depois num escopo de aplicação. Rejeitada porque há risco real de perder o log em aparelhos Xiaomi agressivos com processos.

### D4. Persistência
- **DataStore Preferences** (`settings`): `blocking_enabled` (padrão `true`), `onboarding_done` (padrão `false`), `pending_notification_count` e `last_blocked_*` para a notificação agrupada.
- **Room** (`blocked_calls`): `id` (PK auto), `number: String?` (E.164/bruto como recebido; `null` = oculto), `timestamp: Long` (epoch ms), com índice em `timestamp`. O DAO expõe `Flow<List<BlockedCall>>`, `insert`, `deleteByNumberAndDay`/`deleteIds` e `clearAll`.
- Agrupamento por dia e por número, e contagem "Nx", feitos em Kotlin no ViewModel (função pura e testável), não em SQL. O volume esperado (centenas a poucos milhares de linhas) torna isso trivial.
- Sem expurgo automático no v1. O usuário limpa manualmente.

### D5. Ligar/desligar
- Fonte única: `blocking_enabled` no DataStore.
- `BlockingTileService` (`TileService`): em `onStartListening` lê o estado e atualiza o tile (ativo/inativo, ícone de escudo). Em `onClick` alterna o estado e atualiza o tile.
- Quando o estado muda pelo app, chamar `TileService.requestListeningState(context, ComponentName)` para atualizar o tile.
- A UI observa o mesmo `Flow` e se mantém sincronizada.
- O papel de filtro **não** é liberado ao desligar (`RoleManager.isRoleHeld` só é checado em `onResume` para exibir o aviso de "proteção inativa").

### D6. Notificação discreta
- Canal `blocked_calls` com `IMPORTANCE_LOW`: sem som, sem vibração, sem heads-up. Criado na inicialização do `Application`.
- ID fixo de notificação (uma só, atualizada). Texto: "N ligações bloqueadas" + "Última: <número formatado> às HH:mm".
- `contentIntent`: abre `MainActivity` na rota do log. Ao abrir o log, zera `pending_notification_count` e cancela a notificação.
- `deleteIntent`: `BroadcastReceiver` interno (não exportado) que zera o contador.
- Antes de notificar, verificar `NotificationManagerCompat.areNotificationsEnabled()`. Se for `false`, apenas pular.

### D7. UI
- Jetpack Compose + Material 3, `dynamicColorScheme` (Android 12+) com fallback para uma paleta própria, claro/escuro automático.
- Uma `MainActivity` com Navigation Compose e duas rotas: `onboarding` e `log`. A rota inicial depende de `onboarding_done`.
- Tela de log: `LargeTopAppBar` com o switch de bloqueio; card de resumo (hoje / mês); `LazyColumn` com `stickyHeader` por dia; itens em `ListItem` com avatar/ícone, número formatado, badge "Nx" e horário; ação por toque que abre um `ModalBottomSheet` (Ligar, Salvar contato, Copiar, Pesquisar, Remover); menu de topo com "Limpar log" e confirmação; estado vazio ilustrado; banner de alerta quando o papel não está concedido.
- Formatação: `PhoneNumberUtils.formatNumber(number, Locale.getDefault().country)` com fallback ao número bruto. Sem libphonenumber.
- Intents de ação: `ACTION_DIAL tel:`; `ContactsContract.Intents.Insert.ACTION` com `PHONE`; `ClipboardManager`; `ACTION_VIEW` em `https://www.google.com/search?q=<número>`. Nenhuma exige permissão.
- Textos da UI em português (pt-BR) via `strings.xml`.

### D8. Onboarding
Passos sequenciais num `HorizontalPager`/stepper:
1. Explicação + `RoleManager.createRequestRoleIntent(ROLE_CALL_SCREENING)` via `ActivityResultContracts.StartActivityForResult`.
2. `POST_NOTIFICATIONS` (somente API 33+).
3. Só se `Build.MANUFACTURER` for Xiaomi/Redmi/POCO: tentar abrir `com.miui.securitycenter/com.miui.permcenter.autostart.AutoStartManagementActivity`. Se falhar, `Settings.ACTION_APPLICATION_DETAILS_SETTINGS`. A etapa pode ser pulada.
4. `StatusBarManager.requestAddTileService` (API 33+). Em versões anteriores, instrução textual.
Ao concluir: `onboarding_done = true`.

### D9. Estrutura do projeto e build
```
app/src/main/java/<pkg>/
  CallBlockerApp.kt          // Application + container manual (DB, DataStore, Notifier)
  screening/BlockerScreeningService.kt
  tile/BlockingTileService.kt
  data/ (BlockedCall, BlockedCallDao, AppDatabase, SettingsRepository, BlockedCallRepository)
  notify/BlockedCallNotifier.kt, NotificationDismissReceiver.kt
  ui/ (MainActivity, theme/, log/, onboarding/)
  util/ (PhoneFormatter, grouping)
```
- Gradle Kotlin DSL + version catalog (`libs.versions.toml`), KSP para Room.
- `minSdk 29`, `targetSdk`/`compileSdk` 36, Java/Kotlin toolchain 17.
- Pacote/applicationId assumido: `com.fabio.callblocker`, nome do app "Bloqueador" (podem ser trocados sem impacto).
- Manifest: serviço com `android.permission.BIND_SCREENING_SERVICE` + intent-filter `android.telecom.CallScreeningService`; tile com `BIND_QUICK_SETTINGS_TILE`; permissão `POST_NOTIFICATIONS`.

## Risks / Trade-offs

- [HyperOS pode impedir o bind do serviço com o app em segundo plano] → Etapa de onboarding de início automático/bateria e teste no aparelho logo no início (task dedicada), com o app fechado há horas.
- [Outro app (ex.: Telefone do Google) pode retomar o papel de filtro após atualizações] → Checagem de `isRoleHeld` em `onResume` com banner e botão de reativação.
- [Contatos com número salvo em formato diferente podem ser considerados desconhecidos pelo sistema] → Fica a cargo do matching do Telecom, que tolera formatos comuns. O log com "Salvar contato" permite corrigir rapidamente.
- [Ligações legítimas (entregas, bancos, médicos) serão rejeitadas] → Aceito por decisão do usuário. Mitigado pelo tile de desligar em um toque e pelo log com ações.
- [Gravar antes de responder adiciona latência] → Insert Room + notificação levam dezenas de ms. O teto de 2 s garante a resposta.
- [Como o Telefone do Google rotula a chamada rejeitada no histórico (rejeitada x bloqueada) pode variar] → Validar visualmente no aparelho. Não muda o comportamento do app.
- [Ambiente sem Android SDK] → Instalar o Android Studio antes de começar a implementação.

## Migration Plan

Não se aplica (primeira versão). Distribuição por APK instalado via `adb`/Android Studio no aparelho do usuário. Rollback: desinstalar ou trocar o app de identificação de chamadas e spam nas configurações.

## Open Questions

- Ícone definitivo do app e do tile (usar um escudo vetorial do Material Symbols no v1).
