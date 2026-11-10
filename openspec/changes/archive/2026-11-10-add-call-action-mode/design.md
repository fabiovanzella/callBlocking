# Design

## Context
O aplicativo atualmente responde sempre com rejeição direta (`setDisallowCall(true)` e `setRejectCall(true)`) para chamadas recebidas fora da agenda de contatos. A API do Android `CallScreeningService.CallResponse.Builder` possui o método `setSilenceCall(true)` que suprime toque e vibração mas mantém a chamada ativa na tela para atendimento manual se o usuário quiser.

## Goals / Non-Goals
**Goals:**
- Oferecer escolha entre:
  - `REJECT`: Rejeitar de imediato sem exibir tela nem emitir som.
  - `SILENCE`: Silenciar (sem som e sem vibração), permitindo atender manualmente na tela.
- Salvar a preferência do usuário no DataStore (`call_action_mode`).
- Exibir seletor intuitivo na tela principal.
- Registrar o tipo de ação no banco de dados e exibir distinção sutil no log.

**Non-Goals:**
- Tentar transcrever áudio ou criar secretária eletrônica com IA (inviável por limitações do Android).

## Decisions

### 1. Modelo de Preferência
Enum `CallActionMode`:
```kotlin
enum class CallActionMode {
    REJECT,  // Bloqueio total
    SILENCE  // Silencioso na tela
}
```
Armazenado em `SettingsRepository` com fallback padrão `REJECT`.

### 2. Resposta no `BlockerScreeningService`
```kotlin
val mode = app.settingsRepository.callActionMode.first()

val response = if (mode == CallActionMode.SILENCE) {
    CallResponse.Builder()
        .setDisallowCall(false)
        .setSilenceCall(true)
        .setSkipCallLog(false)
        .build()
} else {
    CallResponse.Builder()
        .setDisallowCall(true)
        .setRejectCall(true)
        .setSkipCallLog(false)
        .setSkipNotification(true)
        .build()
}
```

### 3. Interface do Seletor
Na tela principal (`LogScreen`), abaixo do card de resumo ou no topo da lista, adicionar um seletor tipo *SegmentedButtons* / *SingleChoiceSegmentedButtonRow* do Material 3 com ícones:
- [ Ícone Rejeitar ] Rejeitar chamadas
- [ Ícone Volume Off ] Silenciar na tela

## Risks / Trade-offs
- No modo `SILENCE`, se o usuário não atender, a chamada será registrada pelo próprio discador do sistema como "Chamada perdida". Isso é o comportamento esperado pelo usuário.
