# Proposal

## Why

O filtro de spam nativo (Telefone do Google / HyperOS) funciona por reputação e deixa passar muitas ligações de golpe e telemarketing feitas de números novos. O aparelho do usuário (POCO X8 Pro) não oferece uma regra "aceitar só contatos da agenda". Um app de triagem baseado em **lista de permissão** (a agenda) resolve o problema já na primeira ligação de um número desconhecido.

## What Changes

- Novo app Android nativo (Kotlin + Jetpack Compose, Material 3 com cores dinâmicas), mínimo Android 10 (API 29).
- O app se registra como **app de identificação de chamadas e spam** (`ROLE_CALL_SCREENING`) e rejeita automaticamente qualquer ligação de número fora da agenda, inclusive números ocultos/privados.
- O app **não** pede `READ_CONTACTS`: o próprio sistema só repassa para triagem ligações de quem não está nos contatos.
- As ligações rejeitadas **continuam aparecendo no histórico de chamadas do sistema** e também ficam registradas num **log próprio do app**, com visual elegante (agrupado por dia, contador de repetições, ações rápidas).
- O bloqueio pode ser **ativado/desativado facilmente** por um switch no app e por um **botão nos Ajustes rápidos** (Quick Settings tile), sem perder o papel de filtro.
- Ao bloquear, o app mostra uma **notificação discreta** (sem som/vibração), agrupada, que leva ao log. A notificação de "chamada perdida" do sistema é suprimida para evitar duplicidade.
- Fluxo de configuração inicial: pedir o papel de filtro, a permissão de notificações e orientar os ajustes de bateria/início automático do HyperOS.

**Fora do escopo:** chamadas de WhatsApp/apps VoIP, lista negra manual, base de spam online, sincronização na nuvem, publicação na Play Store.

## Capabilities

### New Capabilities
- `call-screening`: triagem de ligações recebidas; rejeita desconhecidos e números ocultos quando o bloqueio está ativo, mantendo o registro no histórico do sistema.
- `blocking-toggle`: estado ligado/desligado do bloqueio, persistido, controlável pelo app e pelo botão dos Ajustes rápidos.
- `blocked-call-log`: armazenamento local e tela de listagem das ligações rejeitadas, com agrupamento, contagem e ações (ligar, salvar contato, copiar, pesquisar, limpar).
- `blocked-call-notification`: notificação discreta e agrupada para ligações bloqueadas.
- `setup-onboarding`: fluxo de primeira execução para obter papel de filtro, permissão de notificação e orientar ajustes do HyperOS.

### Modified Capabilities
<!-- Nenhuma: projeto novo, sem specs existentes. -->

## Impact

- **Código:** novo projeto Android (Gradle, módulo `app`) criado do zero neste repositório.
- **APIs do sistema:** `CallScreeningService`, `RoleManager`, `TileService`, `NotificationManager`, `PhoneNumberUtils`, intents de `ContactsContract` (inserir contato) e `ACTION_DIAL`.
- **Dependências:** AndroidX (Compose BOM, Material 3, Navigation Compose, Lifecycle), Room, DataStore Preferences, KSP.
- **Permissões:** `POST_NOTIFICATIONS` (Android 13+). Nenhuma permissão de contatos ou de telefone sensível.
- **Dispositivo-alvo:** POCO X8 Pro / HyperOS, sujeito às restrições de segundo plano da Xiaomi (validar cedo).
