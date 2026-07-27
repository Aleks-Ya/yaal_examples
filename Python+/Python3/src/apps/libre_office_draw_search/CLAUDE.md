# CLAUDE.md

Guidance for Claude Code when working in `src/apps/libre_office_draw_search/`.
Read the parent `Python+/Python3/CLAUDE.md` for repo-wide conventions (imports as `apps.<name>...`,
Python 3.12+, `pythonpath = src`, running `pytest` from `Python+/Python3`).

## What this app is

A CLI utility that searches LibreOffice Draw (`.odg`) files under `~/DocsVault/LibreOfficeDraw` for
keywords, ranks the matches, prints them, and lets you interactively open one in the default app.
Aliased on Linux as `draw` → `python .../draw_find.py <keyword...>`.

## Pipeline (entry point: `draw_find.py`)

`draw_find.py` wires the stages together, each in its own module (parser/logic separation per repo
convention):

1. **`file_discoverer.py`** (`FileDiscoverer.find_draw_files`) — globs `**/*.odg` under the root dir.
   `FileDiscoverer.is_root_available` guards this: `draw_find.py` calls it first and prints
   `Printer.format_missing_root` + exits if the vault is unavailable (see the mount convention below).
2. **`odg_parser.py`** (`OdgParser.parse`) — opens each `.odg` via the `odfdo` library and extracts
   page names + text (paragraphs and spans) into an `OdgFileData`.
3. **`searcher.py`** (`Searcher.search`) — matches keywords (lowercased, whitespace-split, flattened)
   against four fields: folder names, filenames, page names, and texts. Produces `SearchResults`.
   Parsing goes through **`parse_cache.py`** (`ParseCache`, injected into `Searcher`), not
   `OdgParser.parse` directly.
4. **`ranker.py`** (`Ranker.rank_results`) — sorts by weighted score and assigns 1-based `rank`.
   Weights: **filename 100, folder 50, page 5, text 1** (in `Ranker.__rank`).
5. **`printer.py`** (`Printer`) — formats keywords, file count, and per-result output.
6. **`opener.py`** (`Opener.open_result`) — interactive `input()` loop; opens the chosen rank with
   `xdg-open`/`open`/`os.startfile` per platform.

## Data model (`data_types.py`)

All strings are wrapped in `NewType` aliases (`OdgPath`, `FolderName`, `FileName`, `PageName`, `Text`)
for clarity — construct them explicitly (e.g. `Text(content)`). `SearchResult` holds the four match
lists plus `rank` and `draw_file`; its `are_*_found()` / `is_found()` helpers drive both ranking and
printing. `SearchResults` aggregates the list with `pages_count`, `texts_count`, `matches_count`.

## Conventions specific to this app

- **Keep the parser/logic split.** New parsing goes in `odg_parser.py`; new matching logic in
  `searcher.py`; new output formatting in `printer.py`. Don't merge stages into `draw_find.py`.
- **`draw_find.py` is a script**, not an importable module — it does a `sys.path.append` hack so the
  alias works when run directly. Don't import from it; put shared logic in the stage modules.
- **Root dir is hardcoded** to `~/DocsVault/LibreOfficeDraw` in `draw_find.py`. Tests inject their own
  root via fixtures, so keep `Searcher`/`Printer` taking `root_dir` as a constructor arg.
- **The vault is a mount that may be absent.** `~/DocsVault/LibreOfficeDraw` is normally a
  Cryptomator mount. When it's unmounted, a `.exists()` check is **not** enough: an open LibreOffice
  instance leaves behind a skeleton of nested empty dirs and lock files (`.~lock.*.odg#`) but no real
  `.odg` files, so the path still exists and looks non-empty. `FileDiscoverer.is_root_available`
  therefore keys off the **presence of at least one `**/*.odg` file** (short-circuit
  `next(root_dir.glob('**/*.odg'), None)`), and also returns `False` on a missing/non-dir path or an
  `OSError` from a stale/broken mount — so the app prints a clear "not available (is it mounted?)"
  message instead of silently reporting 0 files or crashing. Trade-off: a genuinely `.odg`-free but
  mounted vault also reads as unavailable, which is acceptable since the app only searches `.odg`.
- **Parsing is cached** by `ParseCache` (`parse_cache.py`): a JSON index keyed by file path + mtime at
  `$XDG_CACHE_HOME/libre_office_draw_search/index.json` (default `~/.cache/...`). Only new/changed
  `.odg` files are re-parsed, so repeat searches over a static vault are near-instant. Files not
  queried in a run are pruned from the index on `save()`. `Searcher` takes an optional `ParseCache`;
  tests inject one with a `tmp_path` index so they never touch the real `~/.cache`.
- `Searcher.__get_namespaces` is a currently-unused XML-namespace helper — leave it unless the task is
  about namespaces.

## Tests (`tests/apps/libre_office_draw_search/`)

- Run: `cd Python+/Python3 && pytest -k libre_office_draw_search`.
- Tests use **real `.odg` fixtures** under `tests/apps/libre_office_draw_search/files/`
  (`odg_parser_test.odg`, `nested/buildings.odg`) — `conftest.py` exposes them via `real_root_dir`,
  `real_buildings_file`, `real_odg_parser_file`. `odg_parser_test` and `searcher_test` actually parse
  these files (they require `odfdo`); the rest use in-memory `SearchResult`/`SearchResults` fixtures.
- `file_discoverer_test` asserts an exact discovery order — if you add/rename fixture `.odg` files,
  update that expectation.
