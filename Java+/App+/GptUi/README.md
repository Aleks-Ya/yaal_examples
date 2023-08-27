# GptUi

## TODO

1. Switch from legacy `completions` OpenAI endpoint to `chat/completions`
2. Fix layout
3. Add JavaFX unit-tests
4. Configure `jul-to-slf4j` to process HttpClient logs
5. Use `org.beryx.jlink` Gradle plugin to build a DEB distribution
6. Add versioning

## Install on Ubuntu

1. Build distribution and deploy to `~/installed/GptUI`: `gradle App+:GptUi:installDist`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by MenuLibre
    1. Path to exec: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Path to icon: `/home/aleks/installed/GptUI/bin/icon.png`
