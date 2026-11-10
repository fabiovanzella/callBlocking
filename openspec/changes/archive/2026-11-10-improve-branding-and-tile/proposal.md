# Proposal

## Why

O nome do aplicativo e o ícone atual utilizam recursos genéricos antigos do Android, transmitindo uma aparência descuidada. Além disso, o botão de Ajustes Rápidos (Quick Settings Tile) exibe textos confusos e ambíguos ("Bloquear Desconhecidos / Desativado"), que no HyperOS resultam na leitura truncada e contraditória "Desbloquear conhecidos está desativado".

## What Changes

- Atualizar o nome oficial da aplicação para **"Bloqueador de Chamadas"**.
- Criar um ícone vetorial moderno no formato Adaptive Icon do Android (`mipmap/ic_launcher` com background e foreground vetoriais estilizados com escudo de segurança e símbolo de bloqueio de chamada).
- Reestruturar os textos e estados do Tile de Ajustes Rápidos:
  - Título curto e direto: **"Bloquear Chamadas"**.
  - Estado ativo: subtítulo **"Protegido"**.
  - Estado inativo: subtítulo **"Pausado"**.
- Atualizar o ícone do Quick Settings Tile para um escudo vetorial limpo.

## Capabilities

### New Capabilities
<!-- Nenhuma nova capacidade; apenas refinamento visual e de interface -->

### Modified Capabilities
- `blocking-toggle`: Ajustar os textos e rótulos de estado do Quick Settings Tile para evitar dubiedade ("Bloquear Chamadas", "Protegido", "Pausado").

## Impact

- **Recursos Android:** novos drawables vetoriais (`res/drawable/ic_launcher_background.xml`, `res/drawable/ic_launcher_foreground.xml`, `res/mipmap-anydpi-v26/ic_launcher.xml`, `res/drawable/ic_qs_shield.xml`).
- **Strings:** alteração de `app_name`, `tile_label`, `tile_state_active` e `tile_state_inactive` em `res/values/strings.xml`.
- **Serviço Tile:** ajuste no `BlockingTileService` para carregar o novo ícone e strings refinadas.
