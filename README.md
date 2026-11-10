# 🛡️ Call Blocker (Android)

<p align="center">
  <img src="docs/assets/logo.svg" width="96" height="96" alt="Call Blocker Icon" />
</p>

<p align="center">
  <strong>Native Android application that automatically blocks scam, robocalls, and spam calls from numbers not saved in your contact list.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android%2010%2B%20(API%2029%2B)-brightgreen?style=flat-square" alt="Android 10+" />
  <img src="https://img.shields.io/badge/Language-Kotlin-blue?style=flat-square" alt="Kotlin" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose%20%2F%20Material%203-blueviolet?style=flat-square" alt="Jetpack Compose" />
  <img src="https://img.shields.io/badge/Privacy-100%25%20Offline-orange?style=flat-square" alt="100% Offline" />
  <img src="https://img.shields.io/badge/License-MIT-green?style=flat-square" alt="MIT License" />
</p>

<p align="center">
  <a href="https://github.com/fabiovanzella/callBlocking/releases/latest"><strong>📥 Download Latest APK (Releases)</strong></a>
</p>

<p align="center">
  <strong>English</strong> • <a href="README.pt-BR.md">Português</a>
</p>

---

## 💡 Why does this app exist?

Carrier and Google spam filters rely on **community reputation databases**. This means that if a scammer or telemarketing company activates a new SIM card today, their first calls will **bypass the filter completely** and ring directly on your phone.

**Call Blocker** operates on an **Allowlist (Whitelist) model**:
- If the caller **is in your contacts**: the phone rings normally.
- If the caller **is not in your contacts** (or is a private/hidden number): the app intercepts it immediately!

---

## ✨ Key Features

- 🛡️ **Two Flexible Action Modes:**
  - **Reject (Default):** Drops the incoming call within the first millisecond. The phone doesn't ring, vibrate, or wake the screen. Absolute peace of mind.
  - **Silence on Screen:** The call rings silently, waking the dialer screen so you can answer manually if needed (e.g., when waiting for a courier or delivery), while muting ringtone and vibration entirely.
- ⚡ **Quick Settings Tile:**
  - Shield-icon button in your Android notification shade (next to Wi-Fi and Flashlight) to pause or resume protection with **a single tap**, without ever opening the app.
- 🌐 **Multilingual Support (i18n):**
  - Fully localized in **English**, **Portuguese**, **Spanish**, **Japanese**, **French**, and **German**.
  - Automatically follows system language and supports per-app language settings (Android 13+).
- 📋 **Smart Call History Log:**
  - Keeps records grouped by date ("Today", "Yesterday", and specific dates).
  - Repetition counter: if the same number calls 5 times, it collapses into a single item with a `5x` badge and the timestamp of the latest attempt.
  - 1-tap quick actions: **Call Back**, **Save to Contacts**, **Copy Number**, and **Search on Web**.
- 🔕 **Silent & Summarized Notifications:**
  - Low-priority notification in the status bar (no audible alert, no pop-up heads-up) with an up-to-date summary: *"3 calls blocked today - Last: +1 (555) 012-3456"*.
- 🔒 **Radical Privacy:**
  - **Zero Internet Access:** The app does not request or hold the `INTERNET` permission. Nothing ever leaves your device.
  - **No Contacts Permission (`READ_CONTACTS`):** The app uses Android Telecom's native `CallScreeningService` API. The OS itself checks your address book and delivers only non-contact numbers for screening. The app never has access to your contacts list.

---

## 📲 How to Install and Use on Your Phone (Step-by-Step)

You do not need a computer to install the app. Simply follow these steps directly on your Android phone:

### 1. Download the APK
1. On your phone, visit the [**Releases**](https://github.com/fabiovanzella/callBlocking/releases/latest) tab of this GitHub repository.
2. Download the **`CallBlocker-v1.1.0.apk`** file.

### 2. Allow Installation
1. Tap the downloaded file in your notification drawer or file manager.
2. If Android displays *"For your security, your phone is not allowed to install unknown apps from this source"*:
   - Tap **Settings**.
   - Enable **"Allow from this source"** and confirm.
3. Tap **Install**.

### 3. Initial Setup (Onboarding)
When opening the app for the first time, follow the quick onboarding wizard:
1. **Set as Default Call Screener:** Tap the button and confirm the Android system dialog by selecting **Call Blocker** as your default Caller ID & Spam app.
2. **Notifications:** Allow notifications to receive silent summary updates in your status bar.
3. **Add Quick Settings Tile:** Add the shield toggle button to your Quick Settings panel.

---

## ⚙️ Recommended Settings for Xiaomi / POCO / Redmi Devices (HyperOS & MIUI)

Xiaomi devices employ aggressive battery management that may sleep background services. To ensure 24/7 protection even if you haven't opened the app in weeks:

1. **Enable Autostart:**
   - Go to: *Settings > Apps > Manage apps > Call Blocker*.
   - Turn on **Autostart**.
2. **Disable Battery Saver Restrictions:**
   - In the same app info screen, tap **Battery saver**.
   - Select **"No restrictions"**.

*(The app includes a direct shortcut to this screen in the top-right menu: 3 dots > "Review permissions & optimizations")*.

---

## 🛠️ For Developers (Building from Source)

### Prerequisites
- **JDK 17+**
- **Android SDK** with Platform 35 and Build-Tools 35.0.0
- `ANDROID_HOME` environment variable configured.

### Common Commands
```bash
# Clone the repository
git clone https://github.com/fabiovanzella/callBlocking.git
cd callBlocking

# Run unit tests
./gradlew testDebugUnitTest

# Build Release APK
./gradlew assembleRelease

# Install directly on a connected USB device
./gradlew installDebug
```

---

## 📄 License

This project is free and open-source software under the [MIT License](LICENSE).
