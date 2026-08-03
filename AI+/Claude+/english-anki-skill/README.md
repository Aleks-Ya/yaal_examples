# Claude Skills for English vocabulary flashcards in Anki

Claude Code skills for an English-learning workflow. Two manage `En-word-or-sentence` flashcards in
the `En::English` Anki deck; the third is a standalone subtitle-cleanup helper that feeds them:

- **`add-english-word-to-anki`** — turns a list of real-life sentences into fully-filled-in new
  flashcards.
- **`populate-existing-english-anki-notes`** — takes no input file; finds existing notes tagged
  `en::to-refine` and backfills their empty fields.
- **`clean-movie-subtitles`** — standalone (no Anki). Cleans a movie-subtitle file (SRT or plain
  text) into readable prose saved beside it with a ` clean` suffix. A handy source of real-life
  sentences to then feed into `add-english-word-to-anki`.

## Requirements

- Anki running locally with the [AnkiConnect](https://ankiweb.net/shared/info/2055492159) add-on
  (listening on `http://localhost:8765`).
- Python deps for the helper scripts: `pip install -r requirements.txt` (from this directory).
- A Google Cloud **Text-to-Speech** API key in `/home/aleks/.gcp/tts_api_key.txt` (for the audio
  fields).

The `anki-mcp-server` MCP server is already configured in `.mcp.json`; Claude Code starts it per
session.

## Input format (add-english-word-to-anki)

Input is a **single** plain-text/Markdown file, defaulting to `new_anki_words.md` in this directory
if no path is given. H1 headers (`# Source`) delimit sources — every
sentence under a header belongs to that source until the next header:
- The header text is the **source** for its sentences, e.g. `# The Guard 2011` -> source
  "The Guard 2011".
- The special header `# NO_SOURCE` marks a source-less section: its sentences are still imported,
  but no source is mentioned (no source tag, no source shown in the example field).
- Empty sections (a header with no sentences, e.g. `# Python`) are allowed.
- Each non-blank sentence line marks the new word or phrase by wrapping it in single underscores.
  A line with no marker at all is ignored (kept in the file, untouched). A sentence before the
  first header is an error.

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

Each skill has a shortcut script at the project root (`english-add-english-word-to-anki.sh`,
`english-populate-existing-english-anki-notes.sh`, `english-clean-movie-subtitles.sh`) that wraps the
underlying `claude --permission-mode auto --model sonnet -p "/<skill> ..."` call — `cd`s into this
directory and forwards whatever arguments/flags you pass it. They can be run from anywhere (this
directory is on `PATH`). There's also `english-anki.sh`, which instead opens a normal interactive
`claude` session (no `-p`) with the same `--permission-mode auto --model sonnet` and cwd, for
freeform work on this project. Add
`--dry-run` to preview without writing anything to Anki. Add `--no-pictures` (either Anki skill) to
skip the image search/verification for the Picture field — by far the most expensive step; the
Picture field just stays empty (and untagged) and does not block a note's completion or
`en::to-refine` removal.

The scripts print only the final report, and only once the whole run finishes. To watch progress
live instead, call `claude` directly with `--output-format stream-json --verbose` — it emits one
JSON line per step (assistant messages, tool calls, final result):

```bash
claude --permission-mode auto --model sonnet --output-format stream-json --verbose -p "/populate-existing-english-anki-notes"
```

**Add new words from a file** (omit the path to use the default `new_anki_words.md` in this
directory):

```bash
english-add-english-word-to-anki.sh
english-add-english-word-to-anki.sh --dry-run
english-add-english-word-to-anki.sh '/home/aleks/tmp/new_anki_words.md'
english-add-english-word-to-anki.sh --dry-run '/home/aleks/tmp/new_anki_words.md'
english-add-english-word-to-anki.sh --no-pictures '/home/aleks/tmp/new_anki_words.md'
```

**Fill in existing notes flagged for completion** (tag `en::to-refine`, no input file). `--limit N`
processes at most `N` notes in one run:

```bash
english-populate-existing-english-anki-notes.sh
english-populate-existing-english-anki-notes.sh --dry-run --limit 3
english-populate-existing-english-anki-notes.sh --no-pictures --limit 10
```

**Clean a movie-subtitle file** (`clean-movie-subtitles`) — no Anki needed. Pass a path to an `.srt`
or plain-text subtitle file. It removes timestamps/cue numbers/formatting tags, puts one sentence per
line, corrects punctuation, restores censored coarse words (`f*ck` -> `fuck`), strips leading dialogue
dashes (`- You okay?` -> `You okay?`), removes speaker labels (keeping sound cues like `[music]`) and
empty lines, and writes the result to a ` clean` file next to
the input (`/tmp/Backrooms 2026.txt` -> `/tmp/Backrooms 2026 clean.txt`). `--dry-run` reports the
planned output path without writing:

```bash
english-clean-movie-subtitles.sh '/tmp/Backrooms 2026.txt'
english-clean-movie-subtitles.sh --dry-run '/tmp/Backrooms 2026.txt'
```

See each skill's `SKILL.md` for the full step-by-step process.
