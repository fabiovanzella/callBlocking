# Tasks

## 1. Ambiente e scaffolding

- [x] 1.1 Instalar o Android Studio (SDK Platform 36, Build-Tools, Platform-Tools/`adb`) e verificar com `adb version` e `sdkmanager --list_installed`
- [x] 1.2 Criar o projeto Gradle (Kotlin DSL, version catalog, módulo `app`, `applicationId com.fabio.callblocker`, minSdk 29, target/compile 36, toolchain 17) com Compose, Material 3, Navigation, Lifecycle, Room + KSP e DataStore, e verificar que `./gradlew assembleDebug` gera o APK
- [x] 1.3 Adicionar `.gitignore` Android e `README.md` com pré-requisitos e comandos de build/instalação (`./gradlew installDebug`), e verificar que os comandos documentados funcionam
- [x] 1.4 Habilitar a depuração USB no POCO X8 Pro (incluindo "Instalar via USB" do HyperOS) e verificar que `adb devices` lista o aparelho e `./gradlew installDebug` instala o app

## 2. Camada de dados

- [x] 2.1 Implementar `SettingsRepository` (DataStore) com `blockingEnabled` (padrão true), `onboardingDone`, contador e último bloqueio pendentes de notificação, e verificar com teste unitário dos valores padrão e da alternância
- [x] 2.2 Implementar a entidade `BlockedCall`, `BlockedCallDao` (Flow ordenado desc, insert, delete por ids, clearAll), `AppDatabase` e `BlockedCallRepository`, e verificar com teste instrumentado de Room (insert/consulta/ordem/clear)
- [x] 2.3 Implementar `CallBlockerApp` com container manual (DB, Settings, Notifier) registrado no manifest, e verificar que o app abre sem crash
- [x] 2.4 Implementar a função pura de agrupamento (dia -> itens por número com contagem e horário mais recente; "Hoje"/"Ontem"/data) e o resumo hoje/mês, e verificar com testes unitários (virada de dia, número oculto, repetições)
- [x] 2.5 Implementar `PhoneFormatter` (formatação pelo país do aparelho, fallback bruto, `null` -> "Número oculto"), e verificar com testes unitários para números BR com e sem +55

## 3. Triagem de chamadas

- [x] 3.1 Implementar `BlockerScreeningService` conforme design D2/D3 (somente entrada; bloqueio desligado -> permitir; detecção de oculto; gravar + notificar com timeout de 2 s; responder no `finally`) e declarar no manifest com `BIND_SCREENING_SERVICE`, e verificar com teste unitário da lógica de decisão extraída (entrada/saída, ativo/inativo, oculto)
- [ ] 3.2 Criar uma tela provisória com botão que solicita `ROLE_CALL_SCREENING` via `RoleManager`, e verificar no aparelho que o app aparece selecionado em "App de identificação de chamadas e spam"
- [ ] 3.3 Spike no aparelho: ligar de um número fora da agenda (com o app aberto e depois com o app fechado há mais de 1 h) e de um contato, e verificar que o desconhecido é rejeitado sem tocar, aparece no histórico do Telefone, gera registro no Room (`adb shell` / App Inspection) e que o contato toca normalmente; registrar no README se foram necessários ajustes de autostart/bateria
- [ ] 3.4 Verificar no aparelho que a ligação rejeitada não gera notificação de "chamada perdida" do sistema e anotar como o histórico do Telefone do Google a rotula

## 4. Ligar/desligar o bloqueio

- [x] 4.1 Implementar `BlockingTileService` (estado ativo/inativo, ícone de escudo, alternância em `onClick`) declarado com `BIND_QUICK_SETTINGS_TILE`, e verificar no aparelho que o tile alterna e reflete o estado
- [x] 4.2 Garantir a sincronia app -> tile via `TileService.requestListeningState` ao alterar o estado pelo app, e verificar que mudar no app atualiza o tile e vice-versa
- [ ] 4.3 Verificar no aparelho que, com o bloqueio desligado, um desconhecido toca normalmente e não gera registro, e que o estado sobrevive a um reboot

## 5. Notificação discreta

- [x] 5.1 Criar o canal `blocked_calls` (`IMPORTANCE_LOW`) no `Application` e implementar `BlockedCallNotifier` (ID fixo, texto agrupado "N ligações bloqueadas" + última, checagem de notificações habilitadas), e verificar com teste unitário da montagem do texto/contagem
- [x] 5.2 Implementar `contentIntent` (abre o log, zera o contador, cancela a notificação) e `deleteIntent` com `NotificationDismissReceiver` não exportado, e verificar no aparelho que tocar/dispensar zera a contagem e o próximo bloqueio mostra "1 ligação bloqueada"
- [ ] 5.3 Verificar no aparelho que a notificação não toca som, não vibra e não aparece como pop-up, e que, com a permissão negada, o bloqueio e o log continuam funcionando

## 6. Tela de log

- [x] 6.1 Implementar o tema Material 3 (cores dinâmicas + fallback, claro/escuro) e as strings pt-BR, e verificar a renderização nos dois modos via Compose Preview
- [x] 6.2 Implementar `LogViewModel` (Flow do Room -> agrupamento -> UI state; estado do bloqueio; `isRoleHeld` em `onResume`), e verificar com teste unitário do mapeamento de estados (vazio, com itens, sem papel)
- [x] 6.3 Implementar `LogScreen`: top bar com switch, card de resumo, `LazyColumn` com cabeçalhos por dia, itens com número formatado, badge "Nx" e horário, estado vazio e banner de "proteção inativa" com botão de reativação, e verificar com teste de UI Compose que os cabeçalhos, o badge e o estado vazio aparecem
- [x] 6.4 Implementar o bottom sheet de ações (Ligar, Salvar contato, Copiar, Pesquisar, Remover; oculto sem ações de número) e "Limpar log" com confirmação, e verificar no aparelho cada intent e que limpar não altera o histórico do sistema
- [ ] 6.5 Verificar que a lista e o resumo se atualizam sozinhos quando um bloqueio ocorre com a tela aberta

## 7. Onboarding

- [x] 7.1 Implementar o fluxo de onboarding (explicação + papel de filtro com tratamento de recusa; `POST_NOTIFICATIONS` em API 33+; etapa Xiaomi de autostart com fallback para detalhes do app e opção de pular; `requestAddTileService` em API 33+) e substituir a tela provisória da 3.2, e verificar com teste de UI a navegação entre etapas
- [x] 7.2 Persistir `onboardingDone` e definir a rota inicial na `MainActivity`, e verificar que a segunda abertura vai direto ao log
- [ ] 7.3 Verificar no aparelho, a partir de uma instalação limpa (`adb uninstall` + `installDebug`), que o fluxo completo concede o papel, a notificação, abre os ajustes do HyperOS e adiciona o tile

## 8. Validação integrada no aparelho

- [ ] 8.1 Executar o roteiro completo no POCO X8 Pro: desconhecido bloqueado com o app fechado há horas, contato toca, número oculto bloqueado, repetição "3x" no log, notificação agrupada, tile desliga/liga, reboot preserva o estado, papel retirado mostra o banner; registrar os resultados no README
- [ ] 8.2 Rodar `./gradlew test connectedAndroidTest lint` e verificar que tudo passa sem erros
