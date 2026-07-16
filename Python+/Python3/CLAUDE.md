# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this project is

A personal Python 3 knowledge base with two distinct kinds of content living side by side:

1. **`src/apps/`** — ~26 small, independent, runnable applications/utilities (e.g. `bytes_to_human_str`,
   `libre_office_draw_search`, `zen_money`, `tinkoff_investments`). Each is self-contained under its own
   directory; some have their own `README.md`. Corresponding tests live under `tests/apps/<name>/`.
2. **`tests/core/`** and **`tests/module/`** — reference scripts/tests demonstrating Python language
   features (`core/flow`, `core/function`, `core/scope`, `core/with`, ...) and standard/third-party
   library usage (`module/builtins`, `module/standard`, `module/thirdparty/<package>`), organized by
   topic. This is effectively an executable cheat sheet.

**Important:** not every file under `tests/` is a collected pytest test. Pytest only picks up files
matching `test_*.py` / `*_test.py`. Many reference scripts (e.g. `tests/core/flow/if.py`,
`tests/core/scope/scope.py`, `tests/core/flow/for.py`) intentionally don't follow that naming — they are
meant to be read or run directly (`python path/to/script.py`), not executed by pytest. Don't assume a
`.py` file under `tests/` is dead code just because pytest doesn't collect it, and don't rename it to fit
the `*_test.py` pattern unless converting it into a real test is the actual intent.

## Setup

```
sudo apt install -y libkrb5-dev   # for phoenixdb
sudo apt install -y cmake         # for onnx
pyenv install 3.12.12
pyenv virtualenv 3.12.12 python3-examples-3.12.12
pyenv activate python3-examples-3.12.12
pip install -U pip -r requirements.txt
```

`requirements.txt` is large (ML/NLP/cloud/DB packages) and pulls from an extra private index
(`--extra-index-url` near the bottom); installing everything requires those credentials. For most code
edits you don't need the full set installed — only the packages the file(s) you're touching import.

## Commands

```
pytest                              # runs tests, excludes "integration" (see pytest.ini addopts)
pytest -k <expr> -q                 # run a single test by name/expression
pytest -m integration               # run integration tests (hit real 3rd-party resources)
python -m apps.bytes_to_human_str.bytes_to_human_str   # run an app module directly
```

`pytest.ini` sets `pythonpath = src` and `testpaths = tests`, so tests import modules as
`apps.<name>...` and pytest must be run from this directory (`Python+/Python3`), not the repo root.

## Conventions

- **Imports**: everything under `src/` is imported via package root `apps.<name>...` (not relative
  paths), matching the `pythonpath = src` pytest config.
- **Typing/target version**: Python 3.12+; use modern PEP 585 builtin generics (`list[str]`,
  `dict[str, int]`) rather than `typing.List`/`typing.Dict`.
- **File-relative paths**: use `src/current_path.py`'s `get_current_dir()` /
  `get_file_in_current_dir(filename)` (inspects the caller's stack frame) instead of `__file__` when code
  needs a path relative to the calling file — this is what lets the same app code work correctly whether
  invoked as a script or imported in tests.
- **Temp paths in tests**: use `src/temp_helper.py`'s `TempPath.dir_exists()` /
  `TempPath.temp_path_absent()` rather than hand-rolling `tempfile` calls; the `temp_path_absent` pytest
  fixture in `tests/conftest.py` wraps the latter.
- **App structure pattern**: nontrivial apps split parsing from logic, e.g.
  `src/apps/libre_office_draw_search/` separates `odg_parser.py` (parsing), `searcher.py`/`ranker.py`
  (logic), `data_types.py` (typed dataclasses), and `draw_find.py` (entry point) — follow this
  parser/logic separation for new apps of similar complexity rather than one monolithic script.
- **Test markers**: `slow`, `fast`, `integration` are registered in `pytest.ini`; `integration` is
  excluded by default via `addopts`, so tests that talk to real external services must be marked
  `@pytest.mark.integration`.
