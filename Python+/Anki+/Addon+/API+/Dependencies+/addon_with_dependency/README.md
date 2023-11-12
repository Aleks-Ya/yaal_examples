Add-on having dependencies:

1. Third-party module `mem_top` (copied from `sys.path`)
2. Own module `mem_top_client` which depends on `mem_top`
3. `__init__.py` depends on both `mem_top` and `mem_top_client`

Run:

1. Start Anki
2. Execute "Main menu" -> "Tools" -> "addon_with_dependency" -> "Mem Top (directly)"
3. Execute "Main menu" -> "Tools" -> "addon_with_dependency" -> "Mem Top (through client)"