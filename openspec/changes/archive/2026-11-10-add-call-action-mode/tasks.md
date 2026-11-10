# Tasks

## 1. Modelo de Dados e Preferências

- [x] 1.1 Criar enum `CallActionMode` (REJECT, SILENCE) e adicionar chave `call_action_mode` em `SettingsRepository` com métodos get/set, verificando com teste unitário de alternância
- [x] 1.2 Atualizar strings pt-BR para os novos rótulos de modo de chamada ("Rejeitar chamada" e "Silenciar na tela"), verificando integridade do `strings.xml`

## 2. Triagem e Serviço

- [x] 2.1 Atualizar `BlockerScreeningService` para ler `callActionMode` e responder com `setSilenceCall(true)` e `setDisallowCall(false)` quando o modo for `SILENCE`, verificando com teste unitário da lógica de decisão

## 3. Interface do Usuário (Compose)

- [x] 3.1 Atualizar `LogViewModel` e `LogUiState` para expor e modificar `callActionMode`, verificando a emissão de estado
- [x] 3.2 Adicionar seletor de modo (`SingleChoiceSegmentedButtonRow` ou chips) na `LogScreen`, verificando renderização nos previews e testes de UI

## 4. Validação e Deploy no Aparelho

- [x] 4.1 Compilar o projeto com `./gradlew testDebugUnitTest assembleDebug` e verificar que o build passa sem erros
- [x] 4.2 Instalar no POCO X8 Pro via `adb install -r` e testar no celular o comportamento da chamada no modo "Silenciar na tela" e no modo "Rejeitar"
