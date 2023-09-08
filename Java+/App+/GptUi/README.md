# GptUi

## TODO

1. Fix layout
2. Add TestFX unit-tests
3. Use `org.beryx.jlink` Gradle plugin to build a DEB distribution
4. Add `Cancel` button
5. Add `Delete` button to each item of the interaction history list
6. Do not create new HttpClient instance for each request
7. Add an answer from Bard
8. Add a button to check grammar only
9. Add a button to check is a sentence factually correct
10. "Copy" buttons don't work for Interactions loaded from the History
11. Add "Answer", "Definition", "Grammar" buttons instead of "Send"
12. Rename `question` to `prompt` in sources
13. Show a 5-words definition
14. Add button for checking a statement for truth
15. Add search throughout the Interaction History
16. Display current InteractionId in the main window
17. Add an indicator of request status
18. Add "Regenerate" buttons for short and long answers 
19. Add auto-completion for the Theme combo box

## Install on Ubuntu

1. Build distribution and deploy to `~/installed/GptUI`: `gradle App+:GptUi:installDist`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by MenuLibre
    1. Path to exec: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Path to icon: `/home/aleks/installed/GptUI/bin/icon.png`
4. Token file: `${user.home}/.gpt/token.txt`
5. Log file: `${user.home}/.gpt/console.log`
