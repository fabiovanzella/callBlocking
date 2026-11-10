# Tasks

## 1. Identidade Visual e Ícones

- [x] 1.1 Criar drawables vetoriais para o Adaptive Icon (`ic_launcher_background.xml`, `ic_launcher_foreground.xml`) e configurar `mipmap-anydpi-v26/ic_launcher.xml` e `ic_launcher_round.xml`, verificando a existência dos arquivos e a compilação de recursos
- [x] 1.2 Criar drawable vetorial do escudo para os Ajustes Rápidos (`res/drawable/ic_qs_shield.xml`), verificando sua integridade XML

## 2. Strings e Configurações do Manifest

- [x] 2.1 Atualizar `app_name` para "Bloqueador de Chamadas" e os textos do Quick Settings Tile (`tile_label` = "Bloquear Chamadas", `tile_state_active` = "Protegido", `tile_state_inactive` = "Pausado") em `res/values/strings.xml`, verificando ausência de erros de compilação
- [x] 2.2 Atualizar o `AndroidManifest.xml` para referenciar o novo ícone do app (`@mipmap/ic_launcher`) e o ícone do Tile (`@drawable/ic_qs_shield`), verificando que o manifest compila

## 3. Validação e Deploy no Aparelho

- [x] 3.1 Compilar o projeto com `./gradlew testDebugUnitTest assembleDebug` e verificar que o build é concluído com sucesso
- [x] 3.2 Instalar a nova versão no celular via `adb install -r` e validar que o ícone moderno aparece no launcher e o botão dos Ajustes Rápidos exibe "Bloquear Chamadas" com status "Protegido" / "Pausado"
