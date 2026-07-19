# Claude Skills for English vocabulary flashcards in Anki

Two Claude Code skills that manage `En-word-or-sentence` flashcards in the `En::English` Anki deck,
including definition, IPA transcription, Russian translation, synonyms/antonyms, tags, a
representative image, example sentences, and pronunciation audio (Google Text-to-Speech):

- **`add-english-word-to-anki`** (`.claude/skills/add-english-word-to-anki/`) — turns a list of
  real-life sentences into fully-filled-in new flashcards.
- **`populate-existing-english-anki-notes`** (`.claude/skills/populate-existing-english-anki-notes/`)
  — takes no input file; finds existing notes tagged `en::to-refine` and backfills their empty
  Claude-owned fields, dropping the tag once a note is complete.

Both skills share their helper scripts, field-derivation reference, and tag vocabularies under the
project-level `shared/` directory.

## Requirements

- Anki running locally with the [AnkiConnect](https://ankiweb.net/shared/info/2055492159) add-on
  installed (listening on `http://localhost:8765` by default).
- The `anki-mcp-server` MCP server, configured in `.mcp.json` to run in stdio mode so Claude Code
  starts/stops it automatically per session (no need to run it manually in a terminal).
- Python dependencies for the skills' helper scripts (currently just Pillow, used to shrink
  downloaded Picture images): `pip install -r requirements.txt` (from this project's directory).
- A Google Cloud **Text-to-Speech** API key in `/home/aleks/.gcp/tts_api_key.txt`, used to synthesize
  the audio fields (the same key/voice HyperTTS used: `en-US-Wavenet-B`, MP3). The
  `generate_tts.py` script is stdlib-only, so no extra `pip` install is needed for it.

## Input format (add-english-word-to-anki)

Input is **either** a single plain-text/Markdown file **or** a folder of them (in a folder, only
`.md`/`.txt` files are read, non-recursively; other files and empty files are skipped):
- Each file's name (without extension) is the **source** for its words, e.g. `The Guard 2011.md`
  -> source "The Guard 2011". A folder can hold several files, each its own source.
- Each non-empty line is one real-life sentence, with the new word or phrase marked by wrapping
  it in single underscores, e.g.:

```
Just _pin_ a medal to me body, like those lads coming home from Iraq.
Look, I know that you've had a lot of fun _batting_ around the American.
They're eating you alive, the _beggars_.
```

See `shared/assets/The Guard.md` for a full example.

## Usage

From this project's directory, run either skill non-interactively with the Claude Code CLI.

**Add new words from a file or a folder of files:**

```
claude -p --model sonnet "/add-english-word-to-anki '/path/to/The Guard 2011.md'"
claude -p --model sonnet "/add-english-word-to-anki '/home/aleks/tmp/!new_anki_words'"
```

Add `--dry-run` to preview what would be created/updated without writing anything to Anki:

```
claude -p --model sonnet "/add-english-word-to-anki --dry-run '/home/aleks/tmp/!new_anki_words'"
```

**Fill in existing notes flagged for completion** (tag `en::to-refine`, no input file):

```
claude -p --model sonnet "/populate-existing-english-anki-notes"
```

Add `--dry-run` to preview, and `--limit N` to process at most `N` notes in one run (image
search is per-note and slow):

```
claude -p --model sonnet "/populate-existing-english-anki-notes --dry-run --limit 3"
```

Notes stay in the `en::to-refine` queue until every Claude-owned field is filled (or legitimately
tagged as absent); the skill drops the tag automatically once a note is fully complete.

## How it works

**add-english-word-to-anki:** for each word it determines the part of speech and base form, picks
the relevant Anki tags, and checks for an existing duplicate note. If one exists, it adds the new
sentence to it (instead of creating a new note) and backfills any of its Claude-owned fields that
are still empty; otherwise it fills in every field of the `En-word-or-sentence` note type and adds
it to the `En::English` deck.

**populate-existing-english-anki-notes:** it finds `En-word-or-sentence` notes tagged
`en::to-refine`, and for each one backfills every empty Claude-owned field (definition, IPA,
translation, synonyms/antonyms, image, generated examples, pronunciation audio) — respecting the `~api::absent::*` tags
that mark confirmed-absent values — then removes `en::to-refine` once the note is complete.

See each skill's `SKILL.md` for the full step-by-step process and `shared/references/field-plan.md`
for how each field's value is derived.
