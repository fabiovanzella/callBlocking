# blocked-call-notification Specification

## Purpose
Avisar o usuário de forma discreta, sem som nem vibração, que ligações foram bloqueadas, dando acesso rápido ao log.

## Requirements

### Requirement: Notificação discreta ao bloquear
Quando uma ligação for rejeitada, o app MUST exibir uma notificação sem som, sem vibração e sem aparecer como pop-up, exibida apenas na barra/aba de notificações.

#### Scenario: Ligação bloqueada gera notificação silenciosa
- **WHEN** uma ligação de desconhecido é rejeitada
- **THEN** aparece uma notificação do app na aba de notificações, sem som, vibração ou pop-up

### Requirement: Notificação agrupada e atualizada
O app MUST manter uma única notificação de bloqueios, atualizada a cada nova ligação rejeitada, mostrando a quantidade bloqueada desde a última vez que o usuário abriu o log ou dispensou a notificação e o número/horário da última.

#### Scenario: Vários bloqueios seguidos
- **WHEN** três ligações são bloqueadas sem que o usuário abra o log
- **THEN** existe apenas uma notificação do app, indicando "3 ligações bloqueadas" e a última ligação

#### Scenario: Contagem reinicia
- **WHEN** o usuário abre o log ou dispensa a notificação e depois outra ligação é bloqueada
- **THEN** a nova notificação indica 1 ligação bloqueada

### Requirement: Abrir o log pela notificação
Tocar na notificação MUST abrir o app na tela de log e remover a notificação.

#### Scenario: Toque na notificação
- **WHEN** o usuário toca na notificação de bloqueios
- **THEN** o app abre na tela de log e a notificação é removida

### Requirement: Respeitar permissão de notificações
Se a permissão de notificações não estiver concedida, o bloqueio e o registro no log MUST continuar funcionando normalmente, apenas sem notificação.

#### Scenario: Notificações negadas
- **WHEN** o usuário negou a permissão de notificações e uma ligação desconhecida chega
- **THEN** a ligação é rejeitada e registrada no log, sem exibir notificação
