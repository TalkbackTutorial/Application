# Application
An Android App as Talkback Tutorial.

## Debug settings
find here: app/src/main/java/com/github/talkbacktutorial/DebugSettings.kt

### bypassProgressionLocks
setting this to true allows you to access all lessons and modules

### talkbackNotRequired
if this is true you can use the app without talkback, otherwise, the app will ask you to turn talkback on

### skipIntroductoryLesson
When false the app will open the first lesson if it has not already been completed. Set this to true to bybass the first lesson

### skipTTS
When true any TTS is replaced with 'skip', use this if you need to test and don't care what is being read out

### wipeDatabase
setting this to true will clear the database an close the app. Use this if you want to start fresh or there is an issue with the database
