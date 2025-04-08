## 🐦 Flappier Bird

A goofy remake of Flappy Bird made with **Java** and **Android Studio**.

![demo](demo.gif) <!-- optional preview gif/screenshot -->

---

### 📦 Requirements

- **Android Studio** (Chipmunk or later recommended)
- **Android SDK 30+**
- **Gradle** (comes with Android Studio)

---

### ▶️ How to Run

#### 1. Clone the repository
```bash
git clone https://github.com/your-username/flappierbird.git
```

#### 2. Open the project in Android Studio
- Launch Android Studio
- Click on **"Open"**, then select the cloned project folder

#### 3. Build the project
- Let **Gradle** finish syncing
- Accept any SDK/download prompts that appear

#### 4. Run the app
- Connect an Android device or start an emulator
- Click the **Run** ▶️ button in Android Studio

---

### 🧠 Features

- Pipe generation with randomness
- Collision detection
- Score + best score tracking
- Optimized bitmap usage to reduce memory leaks

---

### 🗉 Clean Build (if you get errors)

If you face build errors or crashes on first run:
```bash
./gradlew clean
./gradlew build
```
Then reopen the project in Android Studio.

---

### ⚠️ Troubleshooting

- Make sure you're using a **Java-compatible** Android Studio version
- If images are missing, check the `res/drawable/` and `assets/` folders

---

### 📜 License

MIT License


