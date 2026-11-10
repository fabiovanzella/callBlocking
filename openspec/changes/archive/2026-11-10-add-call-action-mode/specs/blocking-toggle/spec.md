# Spec Delta

## MODIFIED Requirements

### Requirement: Controle dentro do app
A tela principal MUST exibir um controle liga/desliga que reflete e altera o estado do bloqueio e um seletor que permite ao usuário escolher entre o modo de bloqueio direto ("Rejeitar chamada") e o modo silencioso ("Silenciar na tela"), com efeito imediato na próxima ligação recebida.

#### Scenario: Desativar pelo app
- **WHEN** o usuário desliga o controle na tela principal
- **THEN** a próxima ligação de desconhecido toca normalmente

#### Scenario: Alternar para modo Silenciar na tela
- **WHEN** o usuário seleciona o modo "Silenciar na tela"
- **THEN** a próxima ligação de desconhecido entra silenciada, acendendo a tela sem emitir som ou vibração
