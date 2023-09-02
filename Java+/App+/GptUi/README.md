# GptUi

## TODO

1. Fix layout
2. Add TestFX unit-tests
3. Configure `jul-to-slf4j` to process HttpClient logs
4. Use `org.beryx.jlink` Gradle plugin to build a DEB distribution
5. Add versioning
6. Add `Cancel` button
7. Do not overwrite question when answers from GPT are received
8. Add `Delete` button to each item of the interaction history list
9. Sort the Theme list in reverse order (latest Themes on the top)
10. Do not create new HttpClient instance for each request
11. Expand MarkDown code blocs (```markdown) before conversion to HTML
12. Add an answer from Bard
13. Add a button to check grammar only
14. Add a button to check is a sentence factually correct

## Install on Ubuntu

1. Build distribution and deploy to `~/installed/GptUI`: `gradle App+:GptUi:installDist`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by MenuLibre
    1. Path to exec: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Path to icon: `/home/aleks/installed/GptUI/bin/icon.png`
