# GptUi

## TODO

1. Fix layout
2. Add `Delete` button for the Themes
3. Do not create new HttpClient instance for each request
4. Add auto-completion for the Theme combo box
5. Add a filter (search) field for History
6. Add a "Filter" check-box for filtering the History by current Theme
7. Display current InteractionId in the main window
8. Add a temperature slider to the Answer pane
9. Add a beep for Bard
10. Add "Copy" button for Question

## Install on Ubuntu

1. Build distribution and deploy to `~/installed/GptUI`: `gradle :App+:GptUi:installLocally`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by MenuLibre
    1. Path to exec: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Path to icon: `/home/aleks/installed/GptUI/bin/icon.png`
4. Token file: `${user.home}/.gpt/token.txt`
5. Log file: `${user.home}/.gpt/console.log`
