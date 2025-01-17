# GptUi

## TODO

1. Add `Delete` button for the Themes
2. Add `Rename` button for the Themes
3. Add a filter (search) field for History
4. Add "Copy" button for Question
5. Expand Question and Answer view to full screen
6. Replace Gemini Pro with Gemini Ultra.
7. Add new Theme dialog: disable OK button if theme title is empty
8. Fix floating unit-tests: `TemperatureTest`
9. Limit length of text in History ComboBox
10. Bug: Ctrl-Alt-V within the Theme ComboBox
11. Bug: Ctrl-Alt-Up/Down switch Interaction is random order
12. Utilize "Structured output" from `gpt-4o-2024-08-06` model (https://openai.com/index/introducing-structured-outputs-in-the-api/)

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
