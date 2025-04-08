🐦 Flappier Bird
A goofy remake of Flappy Bird made with Java and Android Studio.

 

📦 Requirements
Android Studio (Chipmunk or later recommended)
Android SDK 30+
Gradle (comes with Android Studio)

▶️ How to Run
Clone the repo
git clone https://github.com/your-username/flappierbird.git
Open it in Android Studio
Build the project
Let Gradle sync finish.
If prompted, install missing SDKs or plugins.
Run the app
Connect an Android device or start an emulator.
Press the Run ▶️ button in Android Studio.

🧠 Features

Pipe generation with randomness
Collision detection
Score + best score tracking
Optimized bitmap usage to reduce memory leaks

🛉 Clean build (if you get errors)

If you face build errors or crashes on first run:
./gradlew clean
./gradlew build
Then re-open Android Studio.

⚠️ Troubleshooting

Make sure you're using a Java-compatible Android Studio version.
If images are missing, check res/drawable/ and assets/.
