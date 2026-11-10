# Design

## Context
O aplicativo possui todas as suas strings definidas no idioma Português (Brasil) dentro de `res/values/strings.xml`. No Android, a convenção padrão para distribuição internacional é utilizar a pasta `res/values/` como fallback em **Inglês** e criar qualificadores de idioma (`values-pt`, `values-es`, `values-ja`, `values-fr`, `values-de`) para cada língua suportada. O Android carrega automaticamente as strings corretas de acordo com a configuração de idioma do celular.

## Goals / Non-Goals
**Goals:**
- Fornecer suporte completo aos principais idiomas globais:
  - **Inglês (en)** (padrão universal de fallback em `res/values/`)
  - **Português (pt)** (`res/values-pt/`)
  - **Espanhol (es)** (`res/values-es/`)
  - **Japonês (ja)** (`res/values-ja/`)
  - **Francês (fr)** (`res/values-fr/`)
  - **Alemão (de)** (`res/values-de/`)
- Adaptar o `CallGrouper.kt` para aceitar strings dinâmicas de "Hoje" e "Ontem", permitindo que os cabeçalhos de agrupamento sejam localizados.
- Habilitar `android:localeConfig` no `AndroidManifest.xml` para que usuários do Android 13+ possam alterar o idioma do aplicativo individualmente nas Configurações do Android.

**Non-Goals:**
- Criar seletor manual próprio de idioma dentro da tela do app (utilizar o mecanismo nativo do Android).

## Decisions

### 1. Estrutura de Arquivos de Recursos
- `res/values/strings.xml`: Inglês (English)
- `res/values-pt/strings.xml`: Português (Brasil e Portugal)
- `res/values-es/strings.xml`: Espanhol (Español)
- `res/values-ja/strings.xml`: Japonês (日本語)
- `res/values-fr/strings.xml`: Francês (Français)
- `res/values-de/strings.xml`: Alemão (Deutsch)

### 2. Localização Dinâmica de Datas em `CallGrouper`
Passar os rótulos de "Hoje" e "Ontem" ou uma função de resolução de strings para `CallGrouper.groupCalls(...)`, de forma que o ViewModel obtenha do `Context` da aplicação:
```kotlin
fun groupCalls(calls: List<BlockedCall>, todayLabel: String = "Today", yesterdayLabel: String = "Yesterday"): List<DayCallGroup>
```

### 3. Suporte ao Android 13+ (Per-App Language)
Criar `res/xml/locales_config.xml` declarando as línguas suportadas e referenciar no `AndroidManifest.xml` com `android:localeConfig="@xml/locales_config"`.
