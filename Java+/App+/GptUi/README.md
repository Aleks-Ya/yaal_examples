# GptUi

## TODO

1. Add `Delete` button for the Themes
2. Add `Rename` button for the Themes
3. Add auto-completion for the Theme combo box
4. Add a filter (search) field for History
5. Display current InteractionId in the main window
6. Add "Copy" button for Question
7. Expand Question and Answer view to full screen
8. Display number of Interactions in each Theme in the Theme list
9. Replace Gemini Pro with Gemini Ultra.

## Install on Ubuntu

1. Build distribution and deploy to `/home/aleks/installed/GptUI`:
    1. With tests: `gradle :App+:GptUi:installLocally`
    2. Without tests: `gradle -x :App+:GptUi:test :App+:GptUi:installLocally`
2. Add to `PATH` in `~/.bashrc`: `export PATH=$PATH:/home/aleks/installed/GptUI/bin`
3. Add a menu icon by `MenuLibre` application:
    1. Command: `/home/aleks/installed/GptUI/bin/GptUi`
    2. Icon: `/home/aleks/installed/GptUI/bin/icon.png`
4. Tokens: `~/.gpt/config.properties`. Properties:
    1. `openai.token`
    2. `gcp.api.key`
5. Log file: `~/.gpt/console.log`
