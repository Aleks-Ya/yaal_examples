# Claude Skill for quick adding new English words to Anki

A Claude Code skill (`.claude/skills/add-english-word-to-anki/`) that turns a list of real-life
sentences into fully-filled-in English vocabulary flashcards in Anki, including definition, IPA
transcription, Russian translation, synonyms/antonyms, tags, and example sentences.

## Requirements

- Anki running locally with the [AnkiConnect](https://ankiweb.net/shared/info/2055492159) add-on
  installed (listening on `http://localhost:8765` by default).
- The `anki-mcp-server` MCP server, configured in `.mcp.json` to run in stdio mode so Claude Code
  starts/stops it automatically per session (no need to run it manually in a terminal).

## Input format

Input is a plain-text/Markdown file:
- The file name (without extension) is the **source**, e.g. `The Guard 2011.md` -> source
  "The Guard 2011".
- Each non-empty line is one real-life sentence, with the new word or phrase marked by wrapping
  it in single underscores, e.g.:

```
Just _pin_ a medal to me body, like those lads coming home from Iraq.
Look, I know that you've had a lot of fun _batting_ around the American.
They're eating you alive, the _beggars_.
```

See `.claude/skills/add-english-word-to-anki/assets/The Guard.md` for a full example.

## Usage

From this project's directory, run the skill non-interactively with the Claude Code CLI:

```
claude -p --model sonnet "/add-english-word-to-anki '/path/to/The Guard 2011.md'"
```

Add `--dry-run` to preview what would be created/updated without writing anything to Anki:

```
claude -p --model sonnet "/add-english-word-to-anki --dry-run '/path/to/The Guard 2011.md'"
```

## How it works

For each word, the skill determines its part of speech and base form, picks the relevant Anki
tags, and checks for an existing duplicate note. If one exists, it adds the new sentence to it
(instead of creating a new note) and backfills any of its Claude-owned fields that are still
empty (e.g. an older note missing a definition or synonyms); otherwise it fills in every field of
the `En-word-or-sentence` note type and adds it to the `En::English` deck. See
`.claude/skills/add-english-word-to-anki/SKILL.md` for the full step-by-step process and
`references/field-plan.md` for how each field's value is derived.
