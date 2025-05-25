# Get English words synonyms using OpenAI GPT

### Install to local Anki
1. `ln -s ~/pr/home/yaal_examples/Python+/Anki+/Addon+/App+/OpenAI ~/.local/share/Anki2/addons21/OpenAI`

### Run

1. Build `bundled_dependencies`: `pip install --target=./bundled_dependencies openai`
2. Start Anki
3. Execute "Main menu" -> "Tools" -> "Load synonyms from OpenAI"

### Errors

Message: `ModuleNotFoundError: No module named 'pydantic_core._pydantic_core'`
Cause: `bundled_dependencies` was created with different Python version
Solution: use `pyenv` to install the same Python version as Anki has and re-create `bundled_dependencies`:
1. Install `pyenv`: `brew install pyenv`
2. Install Python 3.19.5: `pyenv install 3.9.18`
3. Configure shell: `pyenv init`
4. Re-open Bash
5. Change to the Addon dir: `cd ~/pr/home/yaal_examples/Python+/Anki+/Addon+/App+/OpenAI`
6. Choose Python 3.19.5 for this shell: `pyenv shell 3.9.18`
7. Verify Python version: `python -V`
8. Generate `bundled_dependencies` folder: `python -m pip install --target=./bundled_dependencies openai`
