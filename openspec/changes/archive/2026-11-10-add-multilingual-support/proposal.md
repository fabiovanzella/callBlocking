# Proposal

## Why

Atualmente, todos os textos da aplicação estão codificados exclusivamente em português (pt-BR) dentro de `res/values/strings.xml`. Para que o aplicativo tenha alcance internacional e possa ser utilizado confortavelmente por usuários de diversos países, o aplicativo deve oferecer suporte nativo às principais línguas do mundo, adaptando-se automaticamente ao idioma do sistema operacional do aparelho.

## What Changes

- Definir o **Inglês (`en`)** como a linguagem base padrão internacional em `res/values/strings.xml` (garantindo fallback para qualquer idioma não traduzido).
- Criar pastas e arquivos de recursos de strings traduzidos para as principais línguas:
  - **Português (`pt`)** em `res/values-pt/strings.xml`
  - **Espanhol (`es`)** em `res/values-es/strings.xml`
  - **Japonês (`ja`)** em `res/values-ja/strings.xml`
  - **Francês (`fr`)** em `res/values-fr/strings.xml`
  - **Alemão (`de`)** em `res/values-de/strings.xml`
- Adaptar o `CallGrouper.kt` para utilizar strings localizáveis ao formatar os cabeçalhos de data ("Hoje" / "Ontem" / datas relativas) em vez de palavras fixas em português.
- Habilitar suporte nativo a seleção de idioma por aplicativo do Android 13+ (`android:localeConfig`).

## Capabilities

### New Capabilities
<!-- Nenhuma nova funcionalidade isolada; expansão de internacionalização (i18n) das capacidades existentes -->

### Modified Capabilities
- `blocked-call-log`: As seções de data e textos de interface da tela de log MUST ser exibidas no idioma do sistema do usuário (ou inglês como fallback padrão).
- `setup-onboarding`: As etapas de explicação de permissões e boas-vindas MUST ser exibidas no idioma configurado no dispositivo.

## Impact

- **Recursos XML:** Criação de `res/values-pt/`, `res/values-es/`, `res/values-ja/`, `res/values-fr/`, `res/values-de/` e migração de `res/values/` para inglês padrão.
- **`CallGrouper.kt` / `LogViewModel.kt`:** Parâmetros de contexto adicionados para carregar cabeçalhos de dia dinamicamente via `Context.getString`.
- **`AndroidManifest.xml`:** Configuração opcional de `localeConfig` para o Android 13+.
