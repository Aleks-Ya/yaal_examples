# GptUi

## TODO

1. Fix layout
2. Add TestFX unit-tests
3. Use `org.beryx.jlink` Gradle plugin to build a DEB distribution
4. Add `Cancel` button
5. Do not overwrite question when answers from GPT are received
6. Add `Delete` button to each item of the interaction history list
7. Do not create new HttpClient instance for each request
8. Add an answer from Bard
9. Add a button to check grammar only
10. Add a button to check is a sentence factually correct
11. "Copy" buttons don't work for Interactions loaded from the History
12. Add "Answer", "Definition", "Grammar" buttons instead of "Send"
13. Rename `question` to `prompt` in sources
14. Show a 5-words definition
15. Add button for checking a statement for truth
16. Add search throughout the Interaction History

## Install on Ubuntu

1. Build distribution and deploy to `~/installed/GptUI`: `gradle App+:GptUi:installDist`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by MenuLibre
    1. Path to exec: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Path to icon: `/home/aleks/installed/GptUI/bin/icon.png`
4. Token file: `${user.home}/.gpt/token.txt`
5. Log file: `${user.home}/.gpt/console.log`
