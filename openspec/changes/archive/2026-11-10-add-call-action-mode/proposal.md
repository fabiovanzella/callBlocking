# Proposal

## Why

Atualmente, toda ligação de número desconhecido é rejeitada imediatamente sem tocar. No entanto, o usuário muitas vezes deseja poder visualizar que uma chamada está entrando em tempo real (para decidir se atende no momento ou ignora, por exemplo se estiver aguardando um entregador ou médico), mas sem o incômodo do som e da vibração.

## What Changes

- Introduzir a configuração de **Modo de Ação para Desconhecidos**:
  1. **Bloquear e Rejeitar (Padrão/Atual):** A chamada é recusada imediatamente; não toca, não vibra e não acende a tela.
  2. **Silenciar e Exibir na Tela (Novo):** A chamada chama para quem está ligando e aparece na tela do discador permitindo atender ou recusar, mas o telefone fica em silêncio absoluto (sem som e sem vibração).
- Adicionar opção configurável na tela principal do aplicativo para o usuário alternar facilmente entre os dois modos.
- Atualizar a resposta do `CallScreeningService` para usar `setSilenceCall(true)` quando o modo silencioso estiver ativo.
- Ajustar os registros do log para indicar se a chamada foi "Rejeitada" ou "Silenciada".

## Capabilities

### New Capabilities
<!-- Nenhuma nova capacidade; estende o comportamento de triagem e configurações -->

### Modified Capabilities
- `call-screening`: Adicionar suporte ao modo de silenciamento de chamadas desconhecidas (`setSilenceCall(true)` e `setDisallowCall(false)`), mantendo a chamada visível na tela sem emitir som ou vibração.
- `blocking-toggle`: Expandir as preferências para armazenar e alternar entre o modo "Rejeitar" e o modo "Silenciar na Tela".

## Impact

- **DataStore:** Nova chave de preferência `call_action_mode` (REJECT ou SILENCE).
- **Triagem (`BlockerScreeningService`):** Lógica condicional para responder com rejeição ou com silenciamento via `setSilenceCall(true)`.
- **UI Compose:** Seletor de modo na tela principal (segment control ou radio options).
- **Banco de Dados (Room):** Campo opcional de tipo de ação (`action: String`) em `BlockedCall` com default `REJECTED`.
