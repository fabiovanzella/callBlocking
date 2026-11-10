# Spec Delta

## MODIFIED Requirements

### Requirement: Controle pelos Ajustes rápidos
O app MUST oferecer um botão nos Ajustes rápidos do sistema que alterna o estado do bloqueio com um toque, sem abrir o app, e que exibe visualmente se o bloqueio está ativo ("Protegido") ou desativado ("Pausado"), utilizando o rótulo principal "Bloquear Chamadas" para evitar qualquer ambiguidade de leitura.

#### Scenario: Alternar pelos Ajustes rápidos
- **WHEN** o usuário toca no botão do app nos Ajustes rápidos com o bloqueio ativo
- **THEN** o bloqueio é desativado e o botão passa a exibir o estado inativo com subtítulo "Pausado"

#### Scenario: Sincronia entre os controles
- **WHEN** o estado é alterado pelos Ajustes rápidos
- **THEN** a tela principal do app exibe o mesmo estado ao ser aberta
