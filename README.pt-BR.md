# 🛡️ Bloqueador de Chamadas (Android)

<p align="center">
  <img src="app/src/main/res/drawable/ic_qs_shield.xml" width="96" height="96" alt="Ícone do Bloqueador" />
</p>

<p align="center">
  <strong>Aplicativo Android nativo que bloqueia automaticamente chamadas de golpistas, robôs e telemarketing fora da sua agenda de contatos.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Plataforma-Android%2010%2B%20(API%2029%2B)-brightgreen?style=flat-square" alt="Android 10+" />
  <img src="https://img.shields.io/badge/Linguagem-Kotlin-blue?style=flat-square" alt="Kotlin" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose%20%2F%20Material%203-blueviolet?style=flat-square" alt="Jetpack Compose" />
  <img src="https://img.shields.io/badge/Privacidade-100%25%20Offline-orange?style=flat-square" alt="100% Offline" />
  <img src="https://img.shields.io/badge/Licen%C3%A7a-MIT-green?style=flat-square" alt="Licença MIT" />
</p>

<p align="center">
  <a href="https://github.com/fabiovanzella/callBlocking/releases/latest"><strong>📥 Baixar APK Atualizado (Releases)</strong></a>
</p>

<p align="center">
  <a href="README.md">English</a> • <strong>Português</strong>
</p>

---

## 💡 Por que este app existe?

Os filtros nativos de spam das operadoras e do Google dependem de **bancos de dados de reputação comunitária**. Isso significa que se um golpista ou empresa de telemarketing ativar um chip novo hoje, a primeira ligação dele **passará direto pelo filtro** e tocará no seu bolso.

O **Bloqueador de Chamadas** funciona por **Lista de Permissão (Whitelist)**:
- Se quem está ligando **já está salvo nos seus contatos**: a chamada toca normalmente.
- Se quem está ligando **não está na sua agenda** (ou for número privado/oculto): o app intercepta na hora!

---

## ✨ Recursos Principais

- 🛡️ **Dois Modos de Ação Flexíveis:**
  - **Rejeitar (Padrão):** Derruba a chamada no primeiro milissegundo. O telefone não toca, não vibra e não acende a tela. Paz absoluta.
  - **Silenciar na Tela:** A chamada chama normalmente e acende a tela do discador permitindo atender manualmente (ex: se estiver aguardando entregador), mas o som da campainha é cortado em silêncio absoluto.
- ⚡ **Acesso Rápido nos Ajustes do Android (Tile):**
  - Botão com ícone de escudo na barra de notificações (onde fica Wi-Fi e Lanterna) para pausar ou reativar o bloqueio com **apenas 1 toque**, sem precisar abrir o app.
- 🌐 **Multilíngue (i18n):**
  - Suporte completo a **Inglês**, **Português**, **Espanhol**, **Japonês**, **Francês** e **Alemão**.
  - Detecção automática de idioma do sistema e suporte à seleção de idioma por app (Android 13+).
- 📋 **Histórico Inteligente:**
  - Registra as chamadas bloqueadas agrupadas por dia ("Hoje", "Ontem", datas).
  - Contador de repetições: se o mesmo número insistir 5 vezes, aparece agrupado como um único item com badge `5x` e o horário da última tentativa.
  - Ações rápidas em 1 toque: **Ligar de volta**, **Salvar como Contato**, **Copiar Número** e **Pesquisar no Google**.
- 🔕 **Notificação Silenciosa e Agrupada:**
  - Notificação discreta na barra (sem som e sem pop-up) com resumo atualizado: *"3 chamadas bloqueadas hoje - Última: (11) 4003-xxxx"*.
- 🔒 **Privacidade Radical:**
  - **Zero Acesso à Internet:** O app não possui nem declara permissão de internet (`INTERNET`). Nada sai do seu aparelho.
  - **Sem Permissão de Contatos (`READ_CONTACTS`):** O app utiliza a arquitetura nativa `CallScreeningService` do Android Telecom, na qual o próprio sistema operacional filtra e entrega para triagem apenas números fora da agenda. O app nunca tem acesso à sua lista de contatos.

---

## 📲 Como Instalar e Usar no Celular (Passo a Passo)

Você não precisa de computador para instalar o aplicativo. Basta seguir estes passos diretamente no seu celular Android:

### 1. Baixar o APK
1. No seu celular, acesse a aba [**Releases**](https://github.com/fabiovanzella/callBlocking/releases/latest) deste repositório no GitHub.
2. Baixe o arquivo **`CallBlocker-v1.1.0.apk`**.

### 2. Permitir a Instalação
1. Toque no arquivo baixado na barra de notificações ou no gerenciador de arquivos.
2. Se o Android exibir a mensagem *"Para sua segurança, seu smartphone não tem permissão para instalar apps desconhecidos desta fonte"*:
   - Toque em **Configurações**.
   - Ative a chave **"Permitir desta fonte"** e confirme.
3. Toque em **Instalar**.

### 3. Configuração Inicial (Onboarding)
Ao abrir o app pela primeira vez, siga o assistente de etapas simples:
1. **Definir como Filtro de Chamadas:** Toque no botão e confirme o diálogo do Android escolhendo **Bloqueador de Chamadas** como o app padrão de identificação de chamadas e spam.
2. **Notificações:** Permita o envio de notificações para receber os avisos silenciosos na barra.
3. **Adicionar Acesso Rápido:** Adicione o botão de escudo aos botões da sua barra superior.

---

## ⚙️ Configurações Importantes para Aparelhos Xiaomi / POCO / Redmi (HyperOS e MIUI)

A Xiaomi possui um gerenciamento agressivo de bateria que pode congelar aplicativos em segundo plano. Para garantir que o filtro funcione 24 horas por dia mesmo se você não abrir o app por semanas:

1. **Ativar o Início Automático:**
   - Acesse: *Configurações > Apps > Gerenciar apps > Bloqueador de Chamadas*.
   - Ative a opção **Início automático**.
2. **Desativar a Economia de Bateria:**
   - Na mesma tela do app, toque em **Economia de bateria**.
   - Selecione a opção **"Sem restrições"**.

*(O próprio app possui um atalho para essa tela no menu superior de 3 pontinhos > "Revisar permissões e otimizações")*.

---

## 🛠️ Para Desenvolvedores (Como Compilar o Projeto)

### Pré-requisitos
- **JDK 17+**
- **Android SDK** com Platform 35 e Build-Tools 35.0.0
- Variável `ANDROID_HOME` configurada no ambiente.

### Comandos Úteis
```bash
# Clonar o repositório
git clone https://github.com/fabiovanzella/callBlocking.git
cd callBlocking

# Rodar os testes unitários
./gradlew testDebugUnitTest

# Gerar o APK de Release
./gradlew assembleRelease

# Instalar diretamente no celular conectado via USB
./gradlew installDebug
```

---

## 📄 Licença

Este projeto é software livre e de código aberto sob a licença [MIT](LICENSE).
