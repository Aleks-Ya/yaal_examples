# Shared conventions for the English Anki skills

These conventions are shared by both `add-english-word-to-anki` and
`populate-existing-english-anki-notes`. Read this doc first, before processing the first note. Each
skill's `SKILL.md` keeps only the parts that genuinely differ from what is written here.

## Abbreviations and synonyms
POS = Part Of Speech
TTS = Text-To-Speech
IPA = International Phonetic Alphabet
flashcard = note
NID = Note ID

## Target note & deck
All notes are `En-word-or-sentence` notes in deck `En::English`.

## Helper scripts
All deterministic work is delegated to the scripts under `shared/scripts/` — **run them; never read
their source** (their CLI contracts are documented at the step that uses each, and in the reference
docs). Each skill lists which reference docs to load lazily and at which step.

## Dry-run mode (`--dry-run`)
In dry-run mode, still perform all read-only work needed to report accurate results, but skip every
note-mutating call. Instead of mutating, report for each item what *would* happen: the planned field
values (per `field-plan.md`), tags, absence tags, target deck, and — where relevant — whether
`en::to-refine` would be removed. Each skill names its own read-only lookups and the exact mutating
calls it skips.

## No-pictures mode (`--no-pictures`)
When `--no-pictures` is given (any position, combinable with the other flags including `--dry-run`),
skip the Picture work entirely — the most expensive part of a run: no image search, downloads,
visual checks, or full-res fetch (even in dry-run mode). Picture is left empty **without**
`~api::absent::picture` and does not block completeness, so a note (and any `en::to-refine` it
carries) is finished on the strength of its other fields. Full behavior — including how the flag
threads into `note_status.py` — is in `shared/references/picture-procedure.md`'s "No-pictures mode".
Note the skip in the row's Outcome (each skill gives its own example wording).

## Single-write rule
Collect **all** field changes for a note into **one** `updateNoteFields` call (or the single
`addNote` for a new note) — the backfill routine's single-write rule; run `storeMediaFile` per media
file beforehand. Skip the write (and the `storeMediaFile`/other mutating calls) in dry-run mode.

## Output report
Don't narrate results per item while processing. Instead, accumulate one row per processed item (in
input order) and print them all as a single Markdown table once every item has been processed,
immediately followed by an aggregate summary line. The **NID** column is always the Anki note id.
Each skill defines its remaining columns.
