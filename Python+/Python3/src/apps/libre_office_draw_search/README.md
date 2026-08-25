# LibreOfficeDrawSearch

## Dependencies
This app has its own [uv](https://docs.astral.sh/uv/) project (`pyproject.toml` + `uv.lock` in this
directory), independent of the big shared `Python+/Python3/requirements.txt`. The only runtime dep is
`odfdo`; `pytest` lives in the `dev` group.

The project is declared `package = false`, so the code is not installed — it stays importable as
`apps.libre_office_draw_search.*` through the `sys.path` append in `draw_find.py` and
`pythonpath = src` in `Python+/Python3/pytest.ini`.

Add or change a dependency by editing `pyproject.toml`, then run `uv sync` from this directory.

Note that `odfdo` also stays in the shared `requirements.txt` — `libre_office_draw_style_aligner` and
`tests/module/thirdparty/odfdo/` need it too.

## Usage
Linux alias:
```bash
alias draw='env -u VIRTUAL_ENV uv run --directory ~/pr/home/yaal_examples/Python+/Python3/src/apps/libre_office_draw_search draw_find.py'
```
`uv run` creates and updates `.venv` from `uv.lock` on demand, so no environment needs to be activated.
`env -u VIRTUAL_ENV` just silences uv's warning when another virtualenv happens to be active.

Usage: `draw FileSystem`

Searches `~/DocsVault/LibreOfficeDraw`, which is normally a Cryptomator mount; when it is unmounted the
app prints a "not available" message and exits instead of reporting 0 results.

## Unit-tests
Run from `~/pr/home/yaal_examples/Python+/Python3`:
```bash
uv run --project src/apps/libre_office_draw_search pytest tests/apps/libre_office_draw_search
```
`--project` (rather than `--directory`) keeps the working directory at `Python+/Python3` so pytest still
picks up `pytest.ini` as its rootdir and applies `pythonpath = src`.
