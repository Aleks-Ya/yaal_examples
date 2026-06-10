# AGENTS: How to be productive in this codebase

This file is a compact, actionable guide for AI coding agents to get productive quickly.

Key points
- Project layout: top-level `src/` contains importable code. Tests use `pythonpath = src` (see `pytest.ini`).
- Python runtime: targets Python 3.12 (project README shows pyenv -> 3.12.12). Use the same to support modern typing syntax.
- Large native / ML deps: `requirements.txt` is large and includes packages requiring OS deps (e.g. `libkrb5-dev` for phoenixdb, `cmake` for onnx). See project README lines 5-11.

Quick setup (commands)
- Create and activate env with pyenv (as in `README.md`), then:

```
pip install -U pip -r requirements.txt
pytest                 # runs tests excluding integration tests (pytest.ini addopts)
pytest -m "integration"  # run integration tests
python -m apps.bytes_to_human_str.bytes_to_human_str  # example runnable module
```

Important conventions and patterns
- Imports: modules under `src/` are imported using package roots like `apps.<name>...`. Tests rely on this (see `pytest.ini`).
- File-relative helpers: use `src/current_path.py::get_current_dir()` and `get_file_in_current_dir()` when code needs the caller's file directory rather than `__file__`.
  - Example: many small utilities in `src/apps/*` use Path-based operations and rely on these helpers for tests.
- Temp helpers: `src/temp_helper.py::TempPath` provides `temp_path_absent()` and `dir_exists()` used by tests (see `tests/conftest.py` fixture `temp_path_absent`). Prefer using these fixtures over creating temp files manually.
- Typing / style: code uses modern Python typing (PEP 585 builtin generics like `list[str]`, `dict[...]`) and private-name patterns (`__` double-underscore methods). Target Python 3.12+ when editing.

Testing / debugging notes
- Tests are configured in `pytest.ini`: pythonpath=src and default addopts excludes `integration` tests.
- To debug a failing unit test, run the single test with `pytest -k <expr> -q` from repository root so `src` is on path via pytest.ini.
- Many modules are small, pure-Python utilities; you can run simple modules directly with `python -m apps.<module>.<script>` if they include a `__main__` or simple test harness.

Integration & environment notes
- Some apps interact with system services (e.g., `src/apps/inactivity_time/` has systemd `.service`/`.desktop` examples) — these expect a Linux desktop environment.
- `requirements.txt` contains an extra index URL (line near end) and private packages — set up credentials if CI installs dependencies.

Where to look first (high-value files)
- `pytest.ini` — test import/runtime config and markers
- `requirements.txt` — full dependency list and extra index URL
- `src/current_path.py`, `src/temp_helper.py` — project-local helpers used by tests and scripts
- `src/apps/` — the collection of small applications. Pick one (e.g. `libre_office_draw_search/`) to learn patterns: typed data classes, parser + searcher separation (`odg_parser.py` + `searcher.py`).
- `tests/` — examples of how modules are exercised; mirrors project conventions.

What not to assume
- There is no centralized packaging or published distribution; many scripts are standalone utilities under `src/apps`.
- Some packages in requirements are heavy or platform-specific (ML, C-extensions). Prefer running unit tests first without optional heavy deps.

Example actionable tasks for an agent
- Add a unit test: put under `tests/` and import modules as `from apps.<name> import ...`.
- Run tests locally with `pytest` from project root; CI likely mirrors this setup.

References
- README.md (project setup)
- pytest.ini (tests)
- requirements.txt (dependencies)
- src/current_path.py, src/temp_helper.py (helpers)
- src/apps/libre_office_draw_search/ (representative app)

