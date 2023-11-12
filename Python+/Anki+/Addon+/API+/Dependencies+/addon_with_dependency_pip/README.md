Add-on having dependencies:

1. Third-party module `pyyaml` in `bundled_dependencies`
2. Own module `client` which depends on `pyyaml`
3. `__init__.py` depends on both `pyyaml` and `client`

Run:

1. Build `bundled_dependencies`: `pip install --target=./bundled_dependencies pyyaml`
2. Start Anki
3. Execute "Main menu" -> "Tools" -> "addon_with_dependency_pip" -> "Run from \_\_init__.py"
4. Execute "Main menu" -> "Tools" -> "addon_with_dependency_pip" -> "Run from client"