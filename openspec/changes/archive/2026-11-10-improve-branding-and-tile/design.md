# Design

## Context
O aplicativo foi lançado inicialmente com strings provisórias ("Bloqueador", "Bloquear Desconhecidos" e "Desativado") e utilizando ícones padrão do sistema (`@android:drawable/ic_menu_call` e `@android:drawable/ic_lock_lock`). Nos aparelhos com HyperOS / Android 14+, a junção de rótulo e subtítulo do Tile causou frases ambíguas ("Desbloquear conhecidos está desativado").

## Goals / Non-Goals
**Goals:**
- Prover uma identidade visual profissional e moderna através de um Adaptive Icon nativo (`ic_launcher` e `ic_launcher_round`).
- Criar ícone vetorial dedicado para o Quick Settings Tile (`ic_qs_shield`).
- Tornar os textos do Quick Settings Tile concisos e sem duplicidade de sentido: "Bloquear Chamadas", "Protegido", "Pausado".
- Atualizar o nome público da aplicação para "Bloqueador de Chamadas".

**Non-Goals:**
- Mudar regras de negócio do Room, DataStore ou `CallScreeningService`.

## Decisions

### 1. Adaptive Icon Vetorial
O Android 8.0+ (API 26+) utiliza `AdaptiveIconDrawable` dividido em camada de fundo (`ic_launcher_background`) e primeiro plano (`ic_launcher_foreground`).
- Background: Gradiente suave em azul moderno (`#0D47A1` para `#1976D2`).
- Foreground: Escudo de segurança vazado com uma barra e símbolo estilizado de chamada bloqueada em branco puro (`#FFFFFF`).
- Arquivos gerados em `app/src/main/res/drawable/` e configurados em `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`.

### 2. Ícone e Textos do Quick Settings Tile
- `tile_label`: "Bloquear Chamadas" (17 caracteres, cabe perfeitamente nos botões do HyperOS sem quebrar linha).
- `tile_state_active`: "Protegido".
- `tile_state_inactive`: "Pausado".
- `android:icon`: `@drawable/ic_qs_shield` (vetor de escudo de segurança).

## Risks / Trade-offs
- O HyperOS pode manter em cache o ícone antigo do app no launcher até uma reinicialização do launcher ou reinstalação do APK. Mitigado usando `adb install -r` e reiniciando a Activity.
