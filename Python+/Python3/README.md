# Python 3

Create virtual environment:

1. Install Ubuntu packages
    1. `krb5-config` for `phoenixdb`: `sudo apt install -y libkrb5-dev`
    2. `cmake` for `onnx`: `sudo apt install -y cmake`
2. Find the latest version: `pyenv install -l` -> `3.12.12`
3. Install the latest version: `pyenv install 3.12.12`
4. Create a virtual environment: `pyenv virtualenv 3.12.12 python3-examples-3.12.12`
5. Activate the virtual environment: `pyenv activate python3-examples-3.12.12`
6. Install packages: `pip install -U pip -r requirements.txt`
7. Configure the Idea project to use the virtual environment

Step 6 also installs the `yaal-helpers` package (see below) in editable mode via the `-e .` line in
`requirements.txt`.

## Shared helpers

`src/yaal_helpers/` holds the modules shared by everything here — `yaal_helpers.current_path` (paths relative
to the calling file) and `yaal_helpers.temp_helper` (temp paths for tests):

```python
from yaal_helpers.current_path import get_file_in_current_dir
from yaal_helpers.temp_helper import TempPath
```

`pytest.ini`'s `pythonpath = src` covers it, and so does the IDE's `src` source root. On top of that it
is packaged by the root `pyproject.toml` and installed editable into every environment in this
directory — the pyenv one above, and each uv project below, which declares it as a
`[tool.uv.sources]` path dependency. So the import works under `pytest`, under
`python some_script.py` from any directory, and inside the isolated venvs alike. See
`src/yaal_helpers/README.md`.

## Dependency-isolated example directories

`requirements.txt` no longer covers everything. The heavy and mutually conflicting example
directories under `tests/module/thirdparty/` each carry their own [uv](https://docs.astral.sh/uv/)
project (`pyproject.toml` + `uv.lock`) and are installed separately, on demand:

```bash
uv sync --project tests/module/thirdparty/<Topic>                                  # install
uv run  --project tests/module/thirdparty/<Topic> pytest tests/module/thirdparty/<Topic>   # test
```

Nothing above needs the pyenv environment activated — `uv run` creates and updates each `.venv`
from its `uv.lock` on demand. A bare `pytest` skips these directories; see `CLAUDE.md` for the list
and for how the skip works.
