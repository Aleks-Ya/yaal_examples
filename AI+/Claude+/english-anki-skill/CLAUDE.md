# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A Claude Skill (`.claude/skills/add-english-word-to-anki/`) that turns real-life sentences into
fully-filled-in English vocabulary flashcards in Anki. See `README.md` for usage and
`.claude/skills/add-english-word-to-anki/SKILL.md` for the full step-by-step process.

## Layout

- `SKILL.md` — the skill definition: input format, `--dry-run` mode, and the step-by-step process.
- `references/field-plan.md` — how each Anki note field's value is derived.
- `assets/en-pos-anki-tags.md` / `assets/word-sources-anki-tags.md` — the fixed tag vocabularies
  the skill picks from for part-of-speech and source tags.
- `assets/all-anki-tags.md` — a full dump of every tag currently in the collection, kept only as
  reference; the skill does not apply tags from it beyond the two lists above.
- `assets/The Guard.md` — example input file (filename-as-source, `_word_`-marked sentences).
- `assets/Example of field Example-real-life.html` — reference for that field's expected HTML
  format.
- `scripts/parse_input.py` — validates/parses the input file's `_word_`-marked lines; run with
  `python3 scripts/parse_input.py <file>` (prints JSON on success, line errors + exit 1 on failure).
- `scripts/find_duplicate.py` — decides whether a candidate note is a genuine duplicate (a/an/to
  normalization + `en::parts::*` tag-family matching); JSON in on stdin, JSON out on stdout.
- `scripts/build_example_html.py` — builds/appends a note's `<ul><li>...</li></ul>` example-sentence
  field (handles legacy plain-text wrapping, sentence dedupe, `<b>` bolding); JSON in on stdin,
  JSON out on stdout.
- `tests/` — pytest suite for the three scripts above (unit tests against their pure functions
  plus one CLI/subprocess end-to-end test per script).
- `pytest.ini` — `pythonpath = scripts` (so test modules can `import` the scripts directly) and
  `testpaths = tests`, same convention as `Python+/Python3/pytest.ini`.

This is otherwise a pure instruction-based skill, with these three scripts (and their tests) as
its only deterministic helper code.

## Running tests
```
cd .claude/skills/add-english-word-to-anki
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
