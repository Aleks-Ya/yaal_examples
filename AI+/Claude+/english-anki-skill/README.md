# Claude Skills for English vocabulary flashcards in Anki

Two Claude Code skills that manage `En-word-or-sentence` flashcards in the `En::English` Anki deck:

- **`add-english-word-to-anki`** — turns a list of real-life sentences into fully-filled-in new
  flashcards.
- **`populate-existing-english-anki-notes`** — takes no input file; finds existing notes tagged
  `en::to-refine` and backfills their empty fields.

## Requirements

- Anki running locally with the [AnkiConnect](https://ankiweb.net/shared/info/2055492159) add-on
  (listening on `http://localhost:8765`).
- Python deps for the helper scripts: `pip install -r requirements.txt` (from this directory).
- A Google Cloud **Text-to-Speech** API key in `/home/aleks/.gcp/tts_api_key.txt` (for the audio
  fields).

The `anki-mcp-server` MCP server is already configured in `.mcp.json`; Claude Code starts it per
session.

## Input format (add-english-word-to-anki)

Input is **either** a single plain-text/Markdown file **or** a folder of them (only `.md`/`.txt`,
non-recursive; empty files skipped):
- Each file's name (without extension) is the **source** for its words, e.g. `The Guard 2011.md`
  -> source "The Guard 2011".
- Each non-empty line is one real-life sentence, with the new word or phrase marked by wrapping it
  in single underscores:

```
Just _pin_ a medal to me body, like those lads coming home from Iraq.
Look, I know that you've had a lot of fun _batting_ around the American.
They're eating you alive, the _beggars_.
```

See `shared/assets/The Guard.md` for a full example.

## Usage

Run from this directory. `--permission-mode auto` lets the skill run without pausing for a
permission prompt each time — needed for these unattended `-p` runs. Add `--dry-run` to preview
without writing anything to Anki. Add `--no-pictures` (either skill) to skip the image
search/verification for the Picture field — by far the most expensive step; the Picture field
just stays empty (and untagged) and does not block a note's completion or `en::to-refine`
removal.

Plain `-p` prints only the final report, and only once the whole run finishes. To watch progress
live, add `--output-format stream-json --verbose` — it emits one JSON line per step (assistant
messages, tool calls, final result):

```bash
claude --permission-mode auto --model sonnet --output-format stream-json --verbose -p "/populate-existing-english-anki-notes"
```

**Add new words from a file or a folder of files:**

```bash
claude --permission-mode auto --model sonnet -p "/add-english-word-to-anki '/path/to/The Guard 2011.md'"
claude --permission-mode auto --model sonnet -p "/add-english-word-to-anki '/home/aleks/tmp/!new_anki_words'"
claude --permission-mode auto --model sonnet -p "/add-english-word-to-anki --dry-run '/home/aleks/tmp/!new_anki_words'"
claude --permission-mode auto --model sonnet -p "/add-english-word-to-anki --no-pictures '/home/aleks/tmp/!new_anki_words'"
```

**Fill in existing notes flagged for completion** (tag `en::to-refine`, no input file). `--limit N`
processes at most `N` notes in one run:

```bash
claude --permission-mode auto --model sonnet -p "/populate-existing-english-anki-notes"
claude --permission-mode auto --model sonnet -p "/populate-existing-english-anki-notes --dry-run --limit 3"
claude --permission-mode auto --model sonnet -p "/populate-existing-english-anki-notes --no-pictures --limit 10"
```

See each skill's `SKILL.md` for the full step-by-step process.
