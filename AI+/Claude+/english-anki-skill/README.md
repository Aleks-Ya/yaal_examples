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

Input is a **single** plain-text/Markdown file. H1 headers (`# Source`) delimit sources — every
sentence under a header belongs to that source until the next header:
- The header text is the **source** for its sentences, e.g. `# The Guard 2011` -> source
  "The Guard 2011".
- The special header `# NO_SOURCE` marks a source-less section: its sentences are still imported,
  but no source is mentioned (no source tag, no source shown in the example field).
- Empty sections (a header with no sentences, e.g. `# Python`) are allowed.
- Each non-blank sentence line marks the new word or phrase by wrapping it in single underscores.
  A sentence before the first header is an error.

```markdown
# NO_SOURCE
Her ability to find a _decent_ job is going to be extremely hard.

# Anthropic Academy
_Elicitation_: Allows servers to request additional information from users.

# Python

# The Guard 2011
They're eating you alive, the _beggars_.
```

See `shared/assets/new_words.md` for a full example.

The file acts as an inbox: after a **live** run, the sentences that were cleanly imported are removed
from it, while **every header is kept** (even ones left empty). A sentence that couldn't be cleanly
imported (e.g. an ambiguous duplicate) stays under its header, so nothing is lost. `--dry-run` never
touches the input file.

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

**Add new words from a file:**

```bash
claude --permission-mode auto --model sonnet -p "/add-english-word-to-anki '/home/aleks/tmp/new_anki_words.md'"
claude --permission-mode auto --model sonnet -p "/add-english-word-to-anki --dry-run '/home/aleks/tmp/new_anki_words.md'"
claude --permission-mode auto --model sonnet -p "/add-english-word-to-anki --no-pictures '/home/aleks/tmp/new_anki_words.md'"
```

**Fill in existing notes flagged for completion** (tag `en::to-refine`, no input file). `--limit N`
processes at most `N` notes in one run:

```bash
claude --permission-mode auto --model sonnet -p "/populate-existing-english-anki-notes"
claude --permission-mode auto --model sonnet -p "/populate-existing-english-anki-notes --dry-run --limit 3"
claude --permission-mode auto --model sonnet -p "/populate-existing-english-anki-notes --no-pictures --limit 10"
```

See each skill's `SKILL.md` for the full step-by-step process.
