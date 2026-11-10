# Bloqueador de Chamadas (Android)

Aplicativo Android nativo (Kotlin + Jetpack Compose) para bloquear automaticamente todas as chamadas recebidas de números que não estão na agenda de contatos do telefone (incluindo números privados/ocultos).

Desenvolvido especialmente para eliminar chamadas invasivas de robocalls, golpes e telemarketing em dispositivos Android (testado e otimizado para HyperOS / POCO X8 Pro).

---

## 🚀 Como Compilar e Instalar

### Pré-requisitos
- **Java 17+** (verifique com `java -version`)
- **Android SDK** com Platform 35 e Build-Tools 35.0.0
- Variável `ANDROID_HOME` apontando para o SDK (ex: `export ANDROID_HOME=$HOME/Android/Sdk`)
- Dispositivo Android com depuração USB ativada ou emulador.

### 1. Compilar o APK
```bash
./gradlew assembleDebug
```
O APK será gerado em: `app/build/outputs/apk/debug/app-debug.apk`

### 2. Rodar Testes Unitários
```bash
./gradlew test
```

### 3. Instalar no Dispositivo via USB
Conecte o aparelho com a depuração USB ativa:
```bash
./gradlew installDebug
```

---

## 📱 Configuração no POCO X8 Pro (HyperOS)

Para garantir que o app funcione em segundo plano e bloqueie chamadas mesmo quando estiver fechado há horas:

1. **Definir como Filtro de Spam:**
   - Na primeira abertura, aceite o diálogo do sistema para definir o app como "App de identificação de chamadas e spam".
   - Ou acerte manualmente em: *Configurações > Apps > Apps padrão > App de identificação de chamadas e spam > Bloqueador*.
2. **Início Automático (Autostart):**
   - Acesse: *Configurações > Apps > Gerenciar apps > Bloqueador > Início automático* (Ativar).
3. **Economia de Bateria:**
   - Acesse: *Configurações > Apps > Gerenciar apps > Bloqueador > Economia de bateria* (Definir como "Sem restrições").
4. **Ajustes Rápidos:**
   - Desça a barra de notificações e edite os botões para adicionar o botão **Bloquear Desconhecidos** (Escudo). Isso permite pausar o bloqueio com um toque quando estiver aguardando uma entrega.
