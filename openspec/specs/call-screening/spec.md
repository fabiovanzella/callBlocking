# call-screening Specification

## Purpose
Fazer a triagem das ligações recebidas pela operadora e rejeitar automaticamente as que vêm de números fora da agenda do usuário, incluindo números ocultos, sem interferir nas ligações de contatos.

## Requirements

### Requirement: Rejeitar ligações de desconhecidos
Quando o bloqueio estiver ativo e o app tiver o papel de filtro de chamadas, o app MUST rejeitar toda ligação recebida de número que não esteja na agenda de contatos do aparelho, antes de o telefone tocar.

#### Scenario: Ligação de número desconhecido com bloqueio ativo
- **WHEN** chega uma ligação de um número que não está nos contatos e o bloqueio está ativo
- **THEN** a ligação é rejeitada sem tocar e sem exibir a tela de chamada recebida

#### Scenario: Ligação de contato salvo
- **WHEN** chega uma ligação de um número salvo nos contatos
- **THEN** a ligação toca normalmente e não é registrada no log de bloqueadas

### Requirement: Rejeitar números ocultos
Quando o bloqueio estiver ativo, o app MUST rejeitar ligações sem número identificado (oculto, privado ou desconhecido).

#### Scenario: Ligação de número oculto
- **WHEN** chega uma ligação com número oculto e o bloqueio está ativo
- **THEN** a ligação é rejeitada e registrada no log como "Número oculto"

### Requirement: Permitir ligações quando o bloqueio está desativado
Quando o bloqueio estiver desativado, o app MUST permitir todas as ligações e MUST NOT registrá-las no log de bloqueadas.

#### Scenario: Ligação de desconhecido com bloqueio desativado
- **WHEN** chega uma ligação de número desconhecido e o bloqueio está desativado
- **THEN** a ligação toca normalmente e nada é registrado no log

### Requirement: Manter registro no histórico de chamadas do sistema
Toda ligação rejeitada pelo app MUST continuar aparecendo no histórico de chamadas do aparelho.

#### Scenario: Ligação rejeitada aparece no histórico do sistema
- **WHEN** uma ligação de desconhecido é rejeitada
- **THEN** ela aparece no histórico do app Telefone do sistema como chamada rejeitada/bloqueada

### Requirement: Suprimir notificação de chamada perdida do sistema
Para ligações rejeitadas, o app MUST impedir a notificação de chamada perdida do sistema, de modo que o usuário receba apenas a notificação discreta do próprio app.

#### Scenario: Sem notificação duplicada
- **WHEN** uma ligação de desconhecido é rejeitada
- **THEN** não aparece notificação de "chamada perdida" do sistema para essa ligação

### Requirement: Não acessar a agenda de contatos
O app MUST NOT solicitar nem usar permissão de leitura de contatos; a distinção entre contato e desconhecido MUST ser delegada ao sistema operacional.

#### Scenario: Permissões declaradas
- **WHEN** o usuário inspeciona as permissões do app nas configurações do Android
- **THEN** não há permissão de contatos solicitada nem concedida

### Requirement: Não bloquear chamadas de emergência
O app MUST NOT interferir nas exceções do sistema para emergências (por exemplo, retorno de ligação após chamada de emergência).

#### Scenario: Retorno após ligação de emergência
- **WHEN** o sistema isenta uma ligação de triagem por contexto de emergência
- **THEN** a ligação toca normalmente, independentemente do estado do bloqueio
