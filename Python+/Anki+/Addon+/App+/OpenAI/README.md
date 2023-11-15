# Get English words synonyms using OpenAI GPT

### Run

1. Build `bundled_dependencies`: `pip install --target=./bundled_dependencies openai`
2. Start Anki
3. Execute "Main menu" -> "Tools" -> "Load synonyms from OpenAI"

### Errors

Message: `ModuleNotFoundError: No module named 'pydantic_core._pydantic_core'`
Cause: `bundled_dependencies` was created with different Python version
Solution: use `pyenv` to install the same Python version as Anki has and re-create `bundled_dependencies`
