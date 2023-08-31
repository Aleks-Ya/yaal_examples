# GptUi

## TODO

1. Switch from legacy `completions` OpenAI endpoint to `chat/completions`
2. Fix layout
3. Add JavaFX unit-tests
4. Configure `jul-to-slf4j` to process HttpClient logs
5. Use `org.beryx.jlink` Gradle plugin to build a DEB distribution
6. Add versioning
7. Add `Cancel` button
8. Do not overwrite question when answers from GPT are received
9. Add `Delete` button to each item of the interaction history list
10. Sort the Theme list in reverse order (latest Themes on the top)
11. Do not create new HttpClient instance for each request
12. Add MarkDown table extension for FlexMark
13. Expand MarkDown code blocs (```markdown) before convertion to HTML
14. Add an answer from Bard

## Install on Ubuntu

1. Build distribution and deploy to `~/installed/GptUI`: `gradle App+:GptUi:installDist`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by MenuLibre
    1. Path to exec: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Path to icon: `/home/aleks/installed/GptUI/bin/icon.png`
