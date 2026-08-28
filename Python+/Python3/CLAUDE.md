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

**Per-app dependency isolation:** some apps have their own uv project (`pyproject.toml` + `uv.lock` in
the app directory, `package = false`) instead of relying on the shared `requirements.txt`:

| App | Entry point | Deps |
|---|---|---|
| `src/apps/yaal_examples_search` | `examples_search.py` | `rich`, `pyperclip` (+ `pytest`, `seedir` dev) |
| `src/apps/libre_office_draw_search` | `draw_find.py` | `odfdo` (+ `pytest` dev) |

Run and test those through their own env — don't add their packages to `requirements.txt`:
```
uv run --directory src/apps/<app> <entry_point>.py <keyword>      # cwd moves; use for running
uv run --project   src/apps/<app> pytest tests/apps/<app>         # cwd stays; pytest.ini still applies
```
`package = false` means the code is never installed — it stays importable as `apps.<name>...` via the
`sys.path` append in the entry point and `pythonpath = src` here. See each app's `README.md`.

`odfdo` is deliberately still in `requirements.txt` too: `libre_office_draw_style_aligner` and
`tests/module/thirdparty/odfdo/` use it. Everything else in `src/apps/` uses the shared
`requirements.txt`.

**Dependency-isolated example directories:** the same uv-project pattern is applied to the heavy and
mutually conflicting example directories under `tests/module/thirdparty/`. Each owns a
`pyproject.toml` and its own `.venv`, and is **not** listed in `requirements.txt`:

| Directory | Holds |
|---|---|
| `AI+` | torch, tensorflow, transformers, gradio, ultralytics, LLM clients (multi-GB) |
| `AI+/NLP+` | NLP + the Japanese tokenizer stack (spacy, MeCab, SudachiPy, GiNZA, ...) |
| `AI+/OnnxRuntime+/onnx-runtime-cpu`, `.../onnxruntime-gpu`, `.../onnx-runtime-open-vino` | `onnxruntime`, `onnxruntime-gpu` and `onnxruntime-openvino` all provide the `onnxruntime` module |
| `Database+` | chromadb |
| `Math+` | numpy, scipy, matplotlib |
| `pandas` | pandas + parquet/Excel engines |
| `AWS+` | boto, boto3, aws-cdk-lib |
| `PyQt5`, `PyQt6` | the two Qt bindings, kept apart so pytest-qt binds to one |
| `PyTest+` | pytest and its plugins |
| `Kafka+/kafka`, `Kafka+/kafka-python`, `Kafka+/confluent_kafka` | `kafka` and `kafka-python` both own the top-level `kafka` module and cannot coexist |
| `DateTime+` | pendulum, dateparser, isodate, pytimeparse(2), pytz, ... |
| `MarkDown+` | mistune, markdown2, mdutils, mdformat |
| `Subtitles+` | pysrt, srt |
| `phoenixdb` | Apache Phoenix client; needs `libkrb5-dev` |
| `hdfs` | `hdfs[avro,dataframe,kerberos]`; the kerberos extra needs `libkrb5-dev` |
| `t-tech-investments` | the private T-Bank index, scoped to that one package |

```bash
uv sync --project tests/module/thirdparty/<Topic>
uv run  --project tests/module/thirdparty/<Topic> pytest tests/module/thirdparty/<Topic>
```

Two of these carry packages that need something beyond pip. `phoenixdb` and `hdfs[kerberos]` both
pull `gssapi`, which builds only with the Kerberos headers present (`sudo apt install -y libkrb5-dev`,
already in `README.md`); without them `uv sync` fails on `krb5-config`. `AI+/NLP+` declares
`ja-ginza-electra` in a **non-default `electra` group** (`uv sync --group electra`) rather than in its
main dependencies: it forces `transformers` back to 4.25.1 and `tokenizers` to 0.13.3, and 0.13.3 has
no cp312 wheel so it builds from source and needs Rust. That is why it was commented out in
`requirements.txt`; the group keeps it declared and reproducible without breaking the default install.

`Kafka+/*` and `PyQt5` hold only plain reference scripts, so they declare no `pytest` and
`uv run ... pytest` fails there — run those with `uv run --project <dir> python <script>.py`.
`Kafka+/kafka` additionally cannot import on 3.12 at all (the distribution is from 2017; the script
already says "works only on Python <=3.6"), and is isolated purely to stop it colliding with
`kafka-python`, which provides the same `kafka` module.

**A directory is isolated exactly when its `pyproject.toml` declares `[tool.uv]`.**
`tests/module/thirdparty/conftest.py` turns that into a `collect_ignore`: such a directory is
collected only while pytest runs under that directory's own `.venv`, so a bare `pytest` skips them
all instead of erroring out on missing imports. Adding a new isolated directory therefore needs no
config change — just create the project. (Testing for `[tool.uv]` rather than for the mere presence
of a `pyproject.toml` is deliberate: `setuptools/`, `build_frontend/` and `cython/` ship
`pyproject.toml`/`setup.py` files as *subject matter*, and none of those is a uv project.)

**These projects do not commit `uv.lock`** — `.gitignore` excludes `tests/module/thirdparty/**/uv.lock`.
They are cheat sheets whose job is to show how a library is used *today*, so every `uv sync` should
resolve fresh rather than pin the examples to whatever was current when they were written; this also
matches `requirements.txt`, which is essentially unpinned. uv still writes a lock locally on each
`uv run`/`uv sync` — it is just untracked. (The two projects under `src/apps/` are tools rather than
examples, and do commit theirs.) The trade-off is that a library release can break an example; when
that happens, pin the offending package in that project's `pyproject.toml` with a comment saying why,
the way `t-tech-investments==1.49.0` does.

The `requirements.txt` exception rule that keeps `odfdo` also keeps ~12 carved-out packages listed
there, because non-isolated code still imports them — e.g. `src/apps/detect_document_images` uses
`transformers`, `src/apps/kafka_current_application_sensor` uses `confluent-kafka`, and
`src/apps/tinkoff_investments` uses `t-tech-investments`.

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
- **Import mode**: `pytest.ini` sets `--import-mode=importlib` and `pythonpath = src tests`. The
  examples deliberately reuse one filename across sibling package directories (7x
  `detect_language_test.py`, 2x `current_timezone_test.py`, ...); the default `prepend` mode names
  modules after the basename alone and aborts collection with "import file mismatch" on those.
  importlib mode does not touch `sys.path`, so `tests/conftest.py` re-adds each test module's own
  directory via `pytest_collectstart` — a handful of examples import a plain sibling file
  (`from hello_app import ...`), which cannot be written as an absolute import because directory
  names like `PyTest+` contain `+` and are not valid Python identifiers.
