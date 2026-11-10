# Tasks

## 1. Internacionalização de Recursos (Strings XML)

- [x] 1.1 Migrar `res/values/strings.xml` para Inglês (English) como fallback padrão global, verificando integridade de todas as chaves
- [x] 1.2 Criar `res/values-pt/strings.xml` com as traduções completas em Português
- [x] 1.3 Criar `res/values-es/strings.xml` com as traduções completas em Espanhol (Español)
- [x] 1.4 Criar `res/values-ja/strings.xml` com as traduções completas em Japonês (日本語)
- [x] 1.5 Criar `res/values-fr/strings.xml` com as traduções completas em Francês (Français)
- [x] 1.6 Criar `res/values-de/strings.xml` com as traduções completas em Alemão (Deutsch)
- [x] 1.7 Criar `res/xml/locales_config.xml` e referenciar `android:localeConfig` no `AndroidManifest.xml`

## 2. Refatoração de Localização em Código

- [x] 2.1 Atualizar `CallGrouper.kt` para receber os textos de "Hoje" e "Ontem" dinamicamente via parâmetros com fallback e atualizar `LogViewModel.kt` para injetar os textos localizados do `Context`
- [x] 2.2 Atualizar os testes unitários em `CallGrouperTest.kt` para verificar agrupamento com strings em diferentes idiomas

## 3. Validação e Deploy

- [x] 3.1 Compilar o projeto com `./gradlew testDebugUnitTest assembleDebug` e verificar que o build passa sem nenhum erro de recurso
- [x] 3.2 Instalar a nova versão no celular via `adb install -r` e validar que o app exibe os textos em português no aparelho e responde corretamente à configuração de idioma do sistema
