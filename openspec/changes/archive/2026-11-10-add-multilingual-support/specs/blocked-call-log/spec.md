# Spec Delta

## MODIFIED Requirements

### Requirement: Listagem agrupada por dia
A tela de log MUST listar as ligações rejeitadas da mais recente para a mais antiga, agrupadas por dia com cabeçalhos legíveis e localizados no idioma ativo do usuário ("Hoje"/"Today", "Ontem"/"Yesterday", ou a data formatada pelo padrão local).

#### Scenario: Agrupamento por dia
- **WHEN** existem bloqueios de hoje e de dias anteriores
- **THEN** a lista mostra seções "Hoje", "Ontem" e datas, em ordem decrescente

#### Scenario: Agrupamento em idioma internacional
- **WHEN** o aparelho está configurado em outro idioma (ex: inglês ou espanhol)
- **THEN** os cabeçalhos são exibidos traduzidos (ex: "Today", "Yesterday" ou "Hoy", "Ayer")
