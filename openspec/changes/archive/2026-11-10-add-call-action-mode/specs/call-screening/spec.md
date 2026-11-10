# Spec Delta

## MODIFIED Requirements

### Requirement: Rejeitar ligações de desconhecidos
Quando o bloqueio estiver ativo e o app tiver o papel de filtro de chamadas, o app MUST aplicar a ação configurada (Rejeitar ou Silenciar) a toda ligação recebida de número que não esteja na agenda de contatos do aparelho, antes de emitir som ou vibração.

#### Scenario: Ligação de número desconhecido com bloqueio ativo
- **WHEN** chega uma ligação de um número que não está nos contatos e o bloqueio está ativo no modo padrão de rejeição
- **THEN** a ligação é rejeitada sem tocar e sem exibir a tela de chamada recebida

#### Scenario: Ligação de contato salvo
- **WHEN** chega uma ligação de um número salvo nos contatos
- **THEN** a ligação toca normalmente e não é registrada no log de bloqueadas

#### Scenario: Ligação de número desconhecido no modo Silenciar
- **WHEN** chega uma ligação de desconhecido com bloqueio ativo e modo configurado como "Silenciar"
- **THEN** a chamada é silenciada pelo sistema operacional (sem som e sem vibração), sendo exibida na tela do discador com opção de atender ou recusar
