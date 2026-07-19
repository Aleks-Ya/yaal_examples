# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

Two related Claude Skills that manage English vocabulary flashcards (`En-word-or-sentence` notes
in the `En::English` deck) in Anki. See `README.md` for usage and each skill's `SKILL.md` for its
full step-by-step process:

- `.claude/skills/add-english-word-to-anki/` — turns real-life sentences (from an input file) into
  new, fully-filled-in flashcards; on a duplicate it appends the sentence and backfills empty
  Claude-owned fields instead of creating a new note.
- `.claude/skills/populate-existing-english-anki-notes/` — takes **no input file**; it finds
  existing notes flagged for completion (tag `en::to-refine`) and backfills their empty
  Claude-owned fields (essentially the add skill's backfill routine, promoted to a standalone,
  tag-driven flow). Removes `en::to-refine` from a note once it is fully complete.

Both skills share one copy of the deterministic helper scripts, the field-derivation reference,
and the tag vocabularies under the project-level `shared/` directory (outside `.claude/skills/` so
the skill loader never treats it as a skill).

## Layout

Each skill directory holds only its own `SKILL.md`. Everything shared lives under `shared/`:

- `.claude/skills/<skill>/SKILL.md` — the skill definition: mode/`--dry-run`, and the step-by-step
  process. Both reference the `shared/…` files by project-root-relative path.
- `shared/references/field-plan.md` — how each Anki note field's value is derived; its "Created by"
  column marks the Claude-owned (backfillable) fields both skills rely on.
- `shared/references/activity-diagram.puml` — PlantUML activity diagram of the add-skill flow.
- `shared/assets/en-pos-anki-tags.md` / `shared/assets/word-sources-anki-tags.md` — the fixed tag
  vocabularies the skills pick from for part-of-speech and source tags.
- `shared/assets/all-anki-tags.md` — a full dump of every tag currently in the collection, kept only
  as reference; the skills do not apply tags from it beyond the two lists above.
- `shared/assets/The Guard.md` — example input file for the add skill (filename-as-source,
  `_word_`-marked sentences).
- `shared/assets/Example of field Example-real-life.html` — reference for that field's expected HTML
  format.
- `shared/scripts/parse_input.py` — validates/parses the add skill's `_word_`-marked lines; accepts a
  single file **or a folder** of `.md`/`.txt` files (non-recursive; empty files skipped, each file's
  name is the per-entry `source`). Run with `python3 shared/scripts/parse_input.py <file-or-folder>`
  (prints a JSON `{entries, skipped}` object on success, per-file line errors + exit 1 on failure).
  Used only by the add skill.
- `shared/scripts/find_duplicate.py` — decides whether a candidate note is a genuine duplicate (a/an/to
  normalization + `en::parts::*` tag-family matching); JSON in on stdin, JSON out on stdout. Used only
  by the add skill.
- `shared/scripts/build_example_html.py` — builds/appends a note's `<ul><li>...</li></ul>`
  example-sentence field (handles legacy plain-text wrapping, sentence dedupe, `<b>` bolding); JSON in
  on stdin, JSON out on stdout. Used by both skills (Example-real-life / Examples1-generated).
- `shared/scripts/fetch_and_resize_image.py` — downloads the Picture field's image and shrinks it to
  fit within 600px on its longest side (never upscales), used before `storeMediaFile`; CLI args in
  (`<url> <output_path> [max_dimension]`), JSON out on stdout. Used by both skills. The only script
  with an external dependency (Pillow — see `requirements.txt`).
- `shared/scripts/generate_tts.py` — synthesizes the mp3 for the audio fields (English / Definition /
  Synonym1 / Antonym1) via Google Cloud Text-to-Speech, used before `storeMediaFile`; CLI args in
  (`<text> <output_path.mp3>` + optional voice/rate/pitch flags), JSON out on stdout. Used by both
  skills. Reads the Google API key from `/home/aleks/.gcp/tts_api_key.txt` (override via
  `GOOGLE_TTS_API_KEY_FILE`); stdlib-only, no `requirements.txt` entry.
- `shared/tests/` — pytest suite for the five scripts above (unit tests against their pure functions
  plus one CLI/subprocess end-to-end test per script).
- `pytest.ini` (project root) — `pythonpath = shared/scripts` (so test modules can `import` the
  scripts directly) and `testpaths = shared/tests`, same convention as `Python+/Python3/pytest.ini`.
- `requirements.txt` (project root) — Python dependencies for the scripts above (currently just Pillow).

These are otherwise pure instruction-based skills, with these five shared scripts (and their tests) as
their only deterministic helper code.

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
