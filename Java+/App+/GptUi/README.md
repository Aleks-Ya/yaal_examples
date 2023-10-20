# GptUi

## TODO

1. Fix layout
2. Add `Delete` button for the Themes
3. Do not create new HttpClient instance for each request
4. Show a 5-words definition
5. Add search throughout the Interaction History
6. Display current InteractionId in the main window
7. Add auto-completion for the Theme combo box
8. Search for a definition in Wikipedia
9. Add a filter (search) field for History
10. Replace Ctrl-XXX hotkeys with Alt-XXX
11. Add a temperature slider

## Install on Ubuntu

1. Build distribution and deploy to `~/installed/GptUI`: `gradle :App+:GptUi:installLocally`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by MenuLibre
    1. Path to exec: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Path to icon: `/home/aleks/installed/GptUI/bin/icon.png`
4. Token file: `${user.home}/.gpt/token.txt`
5. Log file: `${user.home}/.gpt/console.log`
