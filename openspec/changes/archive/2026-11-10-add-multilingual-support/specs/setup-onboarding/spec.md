# Spec Delta

## MODIFIED Requirements

### Requirement: Solicitar papel de filtro de chamadas
Na primeira execução, o app MUST explicar o que faz e solicitar ao sistema o papel de app de identificação de chamadas e spam, com textos exibidos no idioma configurado no aparelho (ou inglês padrão).

#### Scenario: Usuário concede o papel
- **WHEN** o usuário aceita o pedido do sistema para tornar o app o filtro de chamadas
- **THEN** o fluxo avança para a próxima etapa e a proteção passa a funcionar

#### Scenario: Usuário recusa o papel
- **WHEN** o usuário recusa o pedido do sistema
- **THEN** o app explica que não consegue bloquear sem esse papel e permite tentar novamente
