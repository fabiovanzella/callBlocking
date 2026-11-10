# Design

## Context
O aplicativo foi desenvolvido, testado e validado no Android / POCO X8 Pro. Para torná-lo um projeto open-source atraente e facilmente instalável no GitHub, precisamos de uma documentação completa que ensine qualquer pessoa a instalar e usar sem precisar de um computador ou do Android Studio.

## Goals / Non-Goals
**Goals:**
- Criar um `README.md` com padrão profissional, linguagem acessível e recursos visuais (tabelas, badges, emojis, seções organizadas).
- Documentar passo a passo como baixar o APK no celular, como permitir a instalação no Android e quais ajustes fazer no HyperOS/MIUI.
- Fornecer instruções simples de build e arquitetura para programadores.
- Preparar o comando e fluxo do GitHub Releases para hospedar o binário APK.

**Non-Goals:**
- Publicar na Google Play Store.

## Decisions

### 1. Estrutura do README.md
- **Header:** Nome com escudo, badges (Android 10+, Kotlin, Compose, Material 3, No-Internet, 100% Offline), botão de download da release.
- **Destaques:**
  - *Privacidade:* App não tem permissão de internet e não pede acesso aos contatos (o Android entrega apenas o que não está na agenda).
  - *Dois modos:* Rejeitar chamada vs Silenciar na tela.
  - *Tile de Acesso Rápido:* Alternar proteção com 1 toque.
  - *Log com ações:* Ligar, salvar contato, copiar, pesquisar no Google.
- **Guia de Instalação no Celular:**
  1. Baixar o arquivo `.apk` da aba Releases do GitHub.
  2. Abrir o arquivo e autorizar "Instalar de fontes desconhecidas".
  3. No primeiro uso: definir como "Filtro de chamadas e spam padrão".
  4. Para Xiaomi/POCO: ativar Início Automático e definir bateria como "Sem restrições".
- **Para Desenvolvedores:** Clonar, compilar e rodar testes com `./gradlew`.

### 2. Estratégia de Binário APK
O APK pronto fica disponível em `app/build/outputs/apk/debug/app-debug.apk` e deve ser publicado na aba **Releases** do GitHub (tag `v1.0.0`). Isso mantém o repositório Git leve e rápido de clonar, oferecendo um link direto de download permanente pelo GitHub CDN.
