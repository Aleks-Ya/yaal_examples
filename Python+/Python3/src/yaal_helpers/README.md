# yaal-helpers

The home of helpers shared by *everything* in `Python+/Python3` — `src/apps/`, the reference scripts
and the tests alike.

| Module                      | What it gives you                                                                                                                                                                                                   |
|-----------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `yaal_helpers.current_path` | `get_current_dir()`, `get_file_in_current_dir(filename)` — paths relative to the **calling** file (they inspect the caller's stack frame), so the same code works whether it is run as a script or imported by a test. |
| `yaal_helpers.temp_helper`  | `TempPath.dir_exists()`, `TempPath.temp_path_absent()` — temp paths for tests, wrapped by the `temp_path_absent` fixture in `tests/conftest.py`.                                                                       |

```python
from yaal_helpers.current_path import get_file_in_current_dir
from yaal_helpers.temp_helper import TempPath
```

## How it reaches every environment

The package lives under `src/` — the directory `pytest.ini` puts on `pythonpath` and the IDE already
indexes as a source root — so `pytest` and the IDE resolve it with no extra setup, exactly like
`apps.<name>...`.

For everything *outside* pytest it is also a real (dependency-free) distribution, declared by
`Python+/Python3/pyproject.toml` and installed editable everywhere:

- the shared environment installs it through `-e .` in `../../requirements.txt`;
- every uv project in this tree (`src/apps/*`, `tests/module/thirdparty/*`) declares `yaal-helpers` in
  `dependencies` with a `[tool.uv.sources]` editable path entry pointing at `Python+/Python3`.

That is what makes `import yaal_helpers...` work in any process — `python some_script.py` from any
cwd, or `uv run` inside one of the isolated example venvs.

## Adding a helper

Drop a new module into this directory. No `pyproject.toml` change and no re-install are needed: the
editable installs pick it up immediately. Keep it dependency-free — every environment in the project
pays for what is declared here.
