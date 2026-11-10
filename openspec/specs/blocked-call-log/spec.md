# blocked-call-log Specification

## Purpose
Registrar localmente cada ligação rejeitada e apresentá-la numa tela de log elegante, que permita ao usuário entender o que foi bloqueado e agir sobre números legítimos.

## Requirements

### Requirement: Registrar ligações rejeitadas
Para cada ligação rejeitada, o app MUST gravar localmente o número (ou a indicação de número oculto) e a data/hora da ligação. Os dados MUST permanecer apenas no aparelho.

#### Scenario: Registro após bloqueio
- **WHEN** uma ligação de desconhecido é rejeitada
- **THEN** uma entrada com número e data/hora aparece no log do app

#### Scenario: Dados não saem do aparelho
- **WHEN** o app está em uso
- **THEN** nenhum dado do log é enviado pela rede

### Requirement: Listagem agrupada por dia
A tela de log MUST listar as ligações rejeitadas da mais recente para a mais antiga, agrupadas por dia com cabeçalhos legíveis e localizados no idioma ativo do usuário ("Hoje"/"Today", "Ontem"/"Yesterday", ou a data formatada pelo padrão local).

#### Scenario: Agrupamento por dia
- **WHEN** existem bloqueios de hoje e de dias anteriores
- **THEN** a lista mostra seções "Hoje", "Ontem" e datas, em ordem decrescente

#### Scenario: Agrupamento em idioma internacional
- **WHEN** o aparelho está configurado em outro idioma (ex: inglês ou espanhol)
- **THEN** os cabeçalhos são exibidos traduzidos (ex: "Today", "Yesterday" ou "Hoy", "Ayer")

### Requirement: Contagem de repetições
Dentro de um mesmo dia, ligações rejeitadas do mesmo número MUST ser exibidas como um único item com a quantidade de tentativas e o horário da mais recente.

#### Scenario: Mesmo número liga várias vezes
- **WHEN** o mesmo número desconhecido é bloqueado 3 vezes no mesmo dia
- **THEN** a lista exibe um único item para esse número com o indicador "3x" e o horário da última tentativa

### Requirement: Formatação de números
Os números MUST ser exibidos formatados no padrão do país do aparelho (por exemplo, "(11) 98765-4321" no Brasil); números sem identificação MUST aparecer como "Número oculto".

#### Scenario: Número brasileiro
- **WHEN** um número "+5511987654321" é bloqueado
- **THEN** ele aparece formatado de forma legível no padrão brasileiro

### Requirement: Resumo de bloqueios
A tela de log MUST exibir um resumo com a quantidade de ligações bloqueadas hoje e no mês corrente.

#### Scenario: Resumo atualizado
- **WHEN** uma nova ligação é bloqueada com a tela aberta
- **THEN** a lista e os contadores do resumo são atualizados sem ação do usuário

### Requirement: Ações sobre um número bloqueado
Ao selecionar um item do log, o app MUST oferecer as ações: ligar de volta (abrindo o discador), salvar como contato (abrindo a tela de novo contato já preenchida), copiar número e pesquisar o número na web. Para números ocultos, essas ações MUST NOT ser oferecidas.

#### Scenario: Salvar contato
- **WHEN** o usuário escolhe "Salvar contato" em um item
- **THEN** abre a tela de novo contato do sistema com o número preenchido

#### Scenario: Ligar de volta
- **WHEN** o usuário escolhe "Ligar" em um item
- **THEN** abre o discador com o número preenchido, sem iniciar a chamada automaticamente

#### Scenario: Item de número oculto
- **WHEN** o usuário seleciona um item "Número oculto"
- **THEN** nenhuma ação de ligar, salvar, copiar ou pesquisar é oferecida

### Requirement: Remover registros
O usuário MUST poder remover um item individual do log e limpar todo o log, com confirmação antes de limpar tudo. Remover registros do app MUST NOT alterar o histórico de chamadas do sistema.

#### Scenario: Limpar log
- **WHEN** o usuário escolhe "Limpar log" e confirma
- **THEN** todos os registros do app são apagados e o histórico do sistema permanece inalterado

### Requirement: Estado vazio
Quando não houver registros, a tela de log MUST exibir um estado vazio amigável explicando que as ligações bloqueadas aparecerão ali.

#### Scenario: Primeiro uso
- **WHEN** o usuário abre o log sem nenhum bloqueio registrado
- **THEN** é exibida uma mensagem de estado vazio no lugar da lista

### Requirement: Visual consistente com o sistema
A interface MUST seguir o Material 3 com cores dinâmicas do aparelho e suportar modo claro e escuro.

#### Scenario: Modo escuro
- **WHEN** o aparelho está em modo escuro
- **THEN** o app é exibido com o tema escuro
