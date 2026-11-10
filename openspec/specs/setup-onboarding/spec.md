# setup-onboarding Specification

## Purpose
Guiar o usuário na primeira execução para conceder ao app tudo o que ele precisa para bloquear ligações de forma confiável no aparelho, incluindo os ajustes específicos do HyperOS.

## Requirements

### Requirement: Solicitar papel de filtro de chamadas
Na primeira execução, o app MUST explicar o que faz e solicitar ao sistema o papel de app de identificação de chamadas e spam.

#### Scenario: Usuário concede o papel
- **WHEN** o usuário aceita o pedido do sistema para tornar o app o filtro de chamadas
- **THEN** o fluxo avança para a próxima etapa e a proteção passa a funcionar

#### Scenario: Usuário recusa o papel
- **WHEN** o usuário recusa o pedido do sistema
- **THEN** o app explica que não consegue bloquear sem esse papel e permite tentar novamente

### Requirement: Solicitar permissão de notificações
Em Android 13 ou superior, o fluxo MUST solicitar a permissão de notificações, explicando que ela serve para os avisos discretos de bloqueio. Recusar MUST NOT impedir a conclusão do fluxo.

#### Scenario: Usuário recusa notificações
- **WHEN** o usuário nega a permissão de notificações
- **THEN** o fluxo continua e o app funciona sem notificações

### Requirement: Orientar ajustes de bateria e início automático
Em aparelhos Xiaomi/HyperOS, o fluxo MUST orientar o usuário a liberar o início automático e remover restrições de bateria do app, oferecendo atalho para a tela de configurações correspondente quando disponível. Essa etapa MUST poder ser pulada.

#### Scenario: Atalho para ajustes do HyperOS
- **WHEN** o usuário toca em "Abrir ajustes" nessa etapa em um aparelho Xiaomi
- **THEN** abre a tela de configurações do sistema relacionada a início automático ou bateria do app

#### Scenario: Atalho indisponível
- **WHEN** a tela específica não existe no aparelho
- **THEN** o app abre a tela de detalhes do app nas configurações do Android

### Requirement: Sugerir botão nos Ajustes rápidos
Ao final do fluxo, o app MUST oferecer adicionar seu botão aos Ajustes rápidos, usando o pedido do sistema quando suportado.

#### Scenario: Adicionar botão
- **WHEN** o usuário aceita adicionar o botão aos Ajustes rápidos
- **THEN** o botão do app passa a estar disponível nos Ajustes rápidos

### Requirement: Executar o fluxo apenas uma vez
Depois de concluído, o fluxo MUST NOT ser exibido novamente nas aberturas seguintes; o app MUST abrir direto na tela principal.

#### Scenario: Segunda abertura
- **WHEN** o usuário abre o app depois de concluir a configuração
- **THEN** o app abre na tela principal com o log
