# GptUi

## TODO

1. Fix layout
2. Add `Cancel` button
3. Add `Delete` button for the interaction history
4. Do not create new HttpClient instance for each request
5. Add an answer from Bard
6. Add a button to check grammar only
7. Add a button to check is a sentence factually correct
8. Add "Grammar" button
9. Show a 5-words definition
10. Add button for checking a statement for truth
11. Add search throughout the Interaction History
12. Display current InteractionId in the main window
13. Add "Regenerate" buttons for short and long answers
14. Add auto-completion for the Theme combo box
15. Search for a definition in Wikipedia also

## Install on Ubuntu

1. Build distribution and deploy to `~/installed/GptUI`: `gradle :App+:GptUi:installLocally`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by MenuLibre
    1. Path to exec: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Path to icon: `/home/aleks/installed/GptUI/bin/icon.png`
4. Token file: `${user.home}/.gpt/token.txt`
5. Log file: `${user.home}/.gpt/console.log`
