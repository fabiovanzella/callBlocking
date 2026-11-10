# blocking-toggle Specification

## Purpose
Permitir que o usuário ative e desative o bloqueio de desconhecidos de forma rápida, tanto dentro do app quanto pelos Ajustes rápidos, com o estado preservado entre reinicializações.

## Requirements

### Requirement: Estado de bloqueio persistente
O app MUST manter um estado único de bloqueio (ativo/desativado), persistido localmente e preservado após fechar o app ou reiniciar o aparelho. O estado inicial após a instalação MUST ser ativo.

#### Scenario: Estado preservado após reiniciar
- **WHEN** o usuário desativa o bloqueio e reinicia o aparelho
- **THEN** o bloqueio continua desativado

#### Scenario: Estado inicial
- **WHEN** o app é instalado e configurado pela primeira vez
- **THEN** o bloqueio está ativo

### Requirement: Controle dentro do app
A tela principal MUST exibir um controle liga/desliga que reflete e altera o estado do bloqueio e um seletor que permite ao usuário escolher entre o modo de bloqueio direto ("Rejeitar chamada") e o modo silencioso ("Silenciar na tela"), com efeito imediato na próxima ligação recebida.

#### Scenario: Desativar pelo app
- **WHEN** o usuário desliga o controle na tela principal
- **THEN** a próxima ligação de desconhecido toca normalmente

#### Scenario: Alternar para modo Silenciar na tela
- **WHEN** o usuário seleciona o modo "Silenciar na tela"
- **THEN** a próxima ligação de desconhecido entra silenciada, acendendo a tela sem emitir som ou vibração

### Requirement: Controle pelos Ajustes rápidos
O app MUST oferecer um botão nos Ajustes rápidos do sistema que alterna o estado do bloqueio com um toque, sem abrir o app, e que exibe visualmente se o bloqueio está ativo ("Protegido") ou desativado ("Pausado"), utilizando o rótulo principal "Bloquear Chamadas" para evitar qualquer ambiguidade de leitura.

#### Scenario: Alternar pelos Ajustes rápidos
- **WHEN** o usuário toca no botão do app nos Ajustes rápidos com o bloqueio ativo
- **THEN** o bloqueio é desativado e o botão passa a exibir o estado inativo com subtítulo "Pausado"

#### Scenario: Sincronia entre os controles
- **WHEN** o estado é alterado pelos Ajustes rápidos
- **THEN** a tela principal do app exibe o mesmo estado ao ser aberta

### Requirement: Indicar ausência do papel de filtro
Se o app não tiver o papel de filtro de chamadas, a tela principal MUST indicar que a proteção não está funcionando e oferecer ação para concedê-lo, independentemente do estado do controle.

#### Scenario: Papel de filtro removido
- **WHEN** o usuário troca o app de identificação de chamadas e spam nas configurações do sistema
- **THEN** ao abrir o app é exibido um aviso de que a proteção está inativa, com botão para reativá-la
