# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

Two related Claude Skills that manage English vocabulary flashcards (`En-word-or-sentence` notes
in the `En::English` deck) in Anki. See `README.md` for usage and each skill's `SKILL.md` for its
full step-by-step process:

- `.claude/skills/add-english-word-to-anki/` — turns real-life sentences (from a single input file,
  with `# Source` H1 headers delimiting sources) into new, fully-filled-in flashcards; on a
  duplicate it appends the sentence and backfills empty Claude-owned fields instead of creating a new
  note. Treating the file as an inbox, after a live run it removes the sentence lines that were
  cleanly imported while keeping every header (even ones left empty); dry-run leaves the input file
  untouched.
- `.claude/skills/populate-existing-english-anki-notes/` — takes **no input file**; it finds
  existing notes flagged for completion (tag `en::to-refine`) and backfills their empty
  Claude-owned fields (essentially the add skill's backfill routine, promoted to a standalone,
  tag-driven flow). Removes `en::to-refine` from a note once it is fully complete.

Both skills share one copy of the deterministic helper scripts, the field-derivation reference,
and the tag vocabularies under the project-level `shared/` directory (outside `.claude/skills/` so
the skill loader never treats it as a skill).

## Layout

Each skill directory holds only its own `SKILL.md`. Everything shared lives under `shared/`:

- `.claude/skills/<skill>/SKILL.md` — the skill definition: flags (`--dry-run`, `--no-pictures`,
  the populate skill's `--limit`) and the step-by-step process. Both reference the `shared/…` files
  by project-root-relative path.
- `shared/references/field-plan.md` — how each Anki note field's value is derived; its "Created by"
  column marks the Claude-owned (backfillable) fields both skills rely on.
- `shared/references/backfill-routine.md` — the shared step-by-step for filling every empty
  Claude-owned field of a note in hand (drives `note_status.py` + `slugify.py`); referenced by both
  skills so the backfill/completeness logic lives in one place instead of being repeated in prose.
- `shared/references/skill-conventions.md` — the conventions shared by both skills (abbreviations,
  target note type/deck, `--dry-run`/`--no-pictures` semantics, single-write rule, output-report
  style); each `SKILL.md` links to it and keeps only its skill-specific deltas inline.
- `shared/references/picture-procedure.md` — the single home for the Picture field: the
  find/verify/store-the-image procedure and the full `--no-pictures` behavior. `field-plan.md`,
  `backfill-routine.md`, and `skill-conventions.md` link here instead of restating it.
- `shared/references/activity-diagram.puml` — PlantUML activity diagram of the add-skill flow.
- `shared/assets/en-pos-anki-tags.md` / `shared/assets/word-sources-anki-tags.md` — the fixed tag
  vocabularies the skills pick from for part-of-speech and source tags.
- `shared/assets/all-anki-tags.md` — a full dump of every tag currently in the collection, kept only
  as reference; the skills do not apply tags from it beyond the two lists above.
- `shared/assets/new_words.md` — example input file for the add skill (`# Source` H1 headers
  delimiting sources, incl. `# NO_SOURCE` and an empty section, over `_word_`-marked sentences).
- `shared/assets/Example of field Example-real-life.html` — reference for that field's expected HTML
  format.
- `shared/scripts/parse_input.py` — validates/parses the add skill's single input file, whose `# Source`
  H1 headers delimit sources (`# NO_SOURCE` -> `null` source; empty sections allowed; a sentence before
  the first header is an error). Run with `python3 shared/scripts/parse_input.py <file>` (prints a JSON
  `{entries}` object of `{source, line, word, sentence}` on success, per-line errors + exit 1 on
  failure). A second `--clear` mode (`parse_input.py --clear <file>`, reading `{"remove_lines": [...]}`
  on stdin) deletes exactly those 1-indexed lines from the file — keeping every other line, headers
  included — to clear the imported sentences after a live run. Used only by the add skill.
- `shared/scripts/find_duplicate.py` — decides whether an existing note is a genuine duplicate (a/an/to
  normalization + `en::parts::*` tag-family matching). Preferred **direct mode** (`"<word>" "<pos_tag>"`
  as CLI args) queries AnkiConnect itself (default `http://localhost:8765`, override via
  `ANKICONNECT_URL`) and, for a unique match, also returns a trimmed note payload (small text fields +
  a ready `note_status.py` result) so the skill needs no follow-up `notesInfo`; legacy stdin mode takes
  `{word, pos_tag, candidates}` JSON. Used only by the add skill.
- `shared/scripts/build_example_html.py` — builds/appends a note's `<ul><li>...</li></ul>`
  example-sentence field (handles legacy plain-text wrapping, sentence dedupe, `<b>` bolding); JSON in
  on stdin, JSON out on stdout. Used by both skills (Example-real-life / Examples1-generated).
- `shared/scripts/search_images.py` — searches the keyless Openverse image API for Picture-field
  candidates, returning a ranked JSON array of `{url, thumbnail, title, tags, source, license}` so the
  skill can rank by title/tags and then visually verify each before storing; CLI args in
  (`<query> [--limit N]`), JSON out on stdout. Endpoint overridable via `OPENVERSE_API_BASE` (for tests).
  Used by both skills; stdlib-only, no `requirements.txt` entry.
- `shared/scripts/fetch_and_resize_image.py` — downloads a Picture candidate's image and shrinks
  it to fit within 600px on its longest side (never upscales). Single-URL mode
  (`<url> <output_path> [max_dimension]`, JSON out) fetches the chosen winner's full-res image for
  `storeMediaFile`; `--batch` mode reads a JSON array of `{url, path, max_dimension?}` on stdin and
  downloads them **concurrently** (used to pull the top candidates' thumbnails in one call for the
  Read-tool visual check), returning a JSON array with a per-item `error` on failure. Used by both
  skills. The only script with an external dependency (Pillow — see `requirements.txt`).
- `shared/scripts/generate_tts.py` — synthesizes the mp3 for the audio fields (English / Definition /
  Synonym1 / Antonym1) via Google Cloud Text-to-Speech, used before `storeMediaFile`. Single-text mode
  takes CLI args (`<text> <output_path.mp3>` + optional voice/rate/pitch flags); `--batch` mode reads a
  JSON array of `{text, path, voice?...}` on stdin and synthesizes them **concurrently** (one call per
  note for all its audio), returning a JSON array with a per-item `error` on failure. Used by both
  skills. Reads the Google API key from `/home/aleks/.gcp/tts_api_key.txt` (override via
  `GOOGLE_TTS_API_KEY_FILE`); stdlib-only, no `requirements.txt` entry.
- `shared/scripts/note_status.py` — the single source of truth for the judgment-free bookkeeping:
  given a note's fields+tags (JSON in on stdin) it reports which Claude-owned fields are empty vs.
  absence-tagged, which audio needs generating, and whether the note is complete / should drop
  `en::to-refine` (JSON out on stdout); its `--no-pictures` flag excludes an empty Picture from
  the worklist and the completeness verdict (for the skills' `--no-pictures` mode). Used by both skills (drives `backfill-routine.md`); stdlib-only.
- `shared/scripts/slugify.py` — builds the deterministic word+POS(+field) media-filename slug so
  `storeMediaFile` overwrites rather than accumulates; CLI args in
  (`<word> <pos> [--field …] [--ext jpg|mp3]`, or `--all-media` for every filename a note can need —
  Picture jpg + the four audio mp3s — in one call), JSON out. Used by both skills; stdlib-only.
- `shared/tests/` — pytest suite for the eight scripts above (unit tests against their pure functions
  plus one CLI/subprocess end-to-end test per script).
- `pytest.ini` (project root) — `pythonpath = shared/scripts` (so test modules can `import` the
  scripts directly) and `testpaths = shared/tests`, same convention as `Python+/Python3/pytest.ini`.
- `requirements.txt` (project root) — Python dependencies for the scripts above (currently just Pillow).

These are otherwise pure instruction-based skills, with these eight shared scripts (and their tests)
plus the `shared/references/` docs as their only deterministic helper code.

## Running tests
```
# from this project's root directory
pip install -r requirements.txt   # one-time, for fetch_and_resize_image.py's Pillow dependency
pytest
```

## Abbreviations
- POS = Part of Speech

## Anki access

Anki is reachable via the `anki-mcp-server` MCP server (tools like `listDecks`, `addNote`,
`findNotes`, `collection_stats`, etc.), configured in `.mcp.json` in **stdio mode** — Claude Code
spawns/stops the server process itself per session. Prerequisites that Claude Code can't automate:
Anki must be running locally with the AnkiConnect add-on installed (listening on
`http://localhost:8765` by default).

## Notes for future work

- This project sits under `AI+/Claude+/` in the parent `yaal_examples` repo, which is a grab-bag of
  unrelated example projects (see the parent `CLAUDE.md`) — treat this directory as self-contained.
