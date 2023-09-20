# GptUi

## TODO

1. Fix layout
2. Add TestFX unit-tests
3. Add `Cancel` button
4. Add `Delete` button to each item of the interaction history list
5. Do not create new HttpClient instance for each request
6. Add an answer from Bard
7. Add a button to check grammar only
8. Add a button to check is a sentence factually correct
9. Add "Answer", "Definition", "Grammar" buttons instead of "Send"
10. Show a 5-words definition
11. Add button for checking a statement for truth
12. Add search throughout the Interaction History
13. Display current InteractionId in the main window
14. Add "Regenerate" buttons for short and long answers
15. Add auto-completion for the Theme combo box
16. Search for a definition in Wikipedia also

## Install on Ubuntu

1. Build distribution and deploy to `~/installed/GptUI`: `gradle :App+:GptUi:installLocally`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by MenuLibre
    1. Path to exec: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Path to icon: `/home/aleks/installed/GptUI/bin/icon.png`
4. Token file: `${user.home}/.gpt/token.txt`
5. Log file: `${user.home}/.gpt/console.log`
