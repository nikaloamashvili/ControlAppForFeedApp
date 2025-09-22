# ControlAppForFeedApp

ControlAppForFeedApp is an Android application that acts as a control interface for managing the visual aspects and behavior of the DisplayApp (FeedDisplayApp) via broadcast intents. It enables dynamic adjustments to color schemes, visibility, and orientation, providing a seamless remote control experience for feed display applications.

---

## Key Features

- **MVVM Architecture**: Utilizes ViewModel and LiveData for reactive UI and state management.
- **Dependency Injection with Hilt**: Ensures modular, testable, and maintainable code.
- **Remote Control**: Sends broadcast intents to manage the connected FeedDisplayApp.
- **User-Friendly Interface**: Simple controls for adjusting color, visibility, and orientation.

---

## Architecture & Technologies

- **Language:** Kotlin
- **Framework:** Android Jetpack (ViewModel, LiveData, Navigation)
- **Architecture Pattern:** MVVM (Model-View-ViewModel)
- **Dependency Injection:** Hilt
- **Communication:** Broadcast Intents

---

## How It Works

1. **User Interaction:** The user interacts with ControlAppForFeedApp's interface to change display settings.
2. **ViewModel Logic:** UI actions are managed by ViewModel classes, which use LiveData to update UI state reactively.
3. **Broadcast Intents:** Upon user command, the app sends broadcast intents with the appropriate parameters (color, visibility, orientation) to the FeedDisplayApp.
4. **FeedDisplayApp Response:** FeedDisplayApp listens for these intents and applies the changes accordingly.

---

## Dependencies

- [Hilt](https://dagger.dev/hilt/) - For dependency injection
- Android Jetpack libraries (ViewModel, LiveData, etc.)

---

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/nikaloamashvili/ControlAppForFeedApp.git
   ```
2. **Open in Android Studio.**
3. **Build & Run:** Make sure you have an Android device or emulator set up.

---

## License

This project is licensed under the MIT License.

---

## See Also

- [FeedDisplayApp](https://github.com/nikaloamashvili/FeedDisplayApp) - The companion app that receives and processes broadcast intents.
