# Proposal

## Why

Para que o projeto possa ser publicado com excelência no GitHub e utilizado por outros usuários, é necessária uma documentação clara, moderna e convidativa, além de instruções precisas de instalação e um executável (APK) pronto para download direto sem necessidade de compilar código.

## What Changes

- Redigir um `README.md` completo, elegante e com padrão open-source de alta qualidade:
  - Título, badges (Kotlin, Compose, Android 10+, Material 3, Licença MIT).
  - Botão chamativo de download do APK.
  - Demonstração do funcionamento e diferenciais (Whitelist baseada em contatos sem pedir permissão `READ_CONTACTS`, Modo Rejeitar vs Modo Silencioso na tela, Acesso rápido por Tile, Notificação discreta, etc.).
  - Guia passo a passo de instalação no celular (permitir fontes desconhecidas e configurações essenciais para o HyperOS/Xiaomi).
  - Instruções de compilação para desenvolvedores.
- Gerar uma build de distribuição pronta (`release` / `debug-aligned`) em um diretório acessível ou instrução de publicação em GitHub Releases.
- Fornecer os comandos Git necessários para conectar o repositório local ao GitHub remoto.

## Capabilities

### New Capabilities
<!-- Nenhuma nova funcionalidade no app; alteração pura de documentação e empacotamento (skip_specs: true) -->

### Modified Capabilities
<!-- Nenhuma alteração de requisitos de especificação -->

## Impact

- `README.md`: reformulação completa.
- `app/build/outputs/apk/debug/app-debug.apk`: geração e cópia para release pronto.
