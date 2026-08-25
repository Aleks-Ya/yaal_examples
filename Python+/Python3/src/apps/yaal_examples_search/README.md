# Search in `yaal_examples` Git repo
CLI search in `/home/aleks/pr/home/yaal_examples`

## Dependencies
This app has its own [uv](https://docs.astral.sh/uv/) project (`pyproject.toml` + `uv.lock` in this
directory), independent of the big shared `Python+/Python3/requirements.txt`. Runtime deps are just
`rich` and `pyperclip`; `pytest` and `seedir` live in the `dev` group.

The project is declared `package = false`, so the code is not installed — it stays importable as
`apps.yaal_examples_search.*` through the `sys.path` append in `examples_search.py` and
`pythonpath = src` in `Python+/Python3/pytest.ini`.

Add or change a dependency by editing `pyproject.toml`, then run `uv sync` from this directory.

## Usage
Linux alias:
```bash
alias examples='env -u VIRTUAL_ENV uv run --directory ~/pr/home/yaal_examples/Python+/Python3/src/apps/yaal_examples_search examples_search.py'
```
`uv run` creates and updates `.venv` from `uv.lock` on demand, so no environment needs to be activated.
`env -u VIRTUAL_ENV` just silences uv's warning when another virtualenv happens to be active.

Case-insensitive search: `examples Java`
Case-sensitive search: `examples -c Java`

Requires the `plocate` binary (`sudo apt install plocate`) with an up-to-date index.

## Unit-tests
Run from `~/pr/home/yaal_examples/Python+/Python3`:
```bash
uv run --project src/apps/yaal_examples_search pytest tests/apps/yaal_examples_search
```
`--project` (rather than `--directory`) keeps the working directory at `Python+/Python3` so pytest still
picks up `pytest.ini` as its rootdir and applies `pythonpath = src`.

## TODO
- [X] Print results as a tree
- [X] Case-insensitive search
- [X] Add unit-tests
- [ ] Ranking results
- [X] Add Bash alias
- [X] Multiple keywords support
- [X] Copy path to results by number