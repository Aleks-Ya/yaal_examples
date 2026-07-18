---
name: add-english-word-to-anki
description: Quickly add a new English word to Anki as a flashcard.
---

# Claude skill: Add English word to Anki
This skill creates a new English word note in Anki, including filling all fields for the new note.

Target note type for new notes is `En-word-or-sentence`. Target deck is `En::English`.

## Abbreviations and synonyms
POS = Part Of Speech
flashcard = note

## Input provided per card
Input data is provided as a path to a plain-text/Markdown file:
1. The file name (without extension) is the source where every word in the file was found, e.g. `The Guard.md` -> source "The Guard".
2. Each non-empty line is one real-life sentence. The new word (or phrase) within it is marked by wrapping it in single underscores, e.g. `Just _pin_ a medal to me body.` -> word "pin".

Validation and parsing of the marked lines is done by @scripts/parse_input.py, not by hand — see
step 1. Duplicate matching and example-sentence HTML are likewise handled by
@scripts/find_duplicate.py and @scripts/build_example_html.py — see step 2.2.

Example of an input file: @"assets/The Guard.md"

## Mode
If the arguments include `--dry-run` (in addition to the file path, in either order), run in **dry-run mode**. Otherwise run in **live mode** (default): e.g. `/add-english-word-to-anki --dry-run '/path/to/file.md'` vs. `/add-english-word-to-anki '/path/to/file.md'`.

In dry-run mode, still perform all read-only lookups (`findNotes`, `notesInfo`, `listDecks`, `modelFieldNames`) needed to do the duplicate check and report accurate results, but skip every note-mutating call (`addNote`/`addNotes`, `updateNoteFields`, `addTags`). Instead of mutating, report what would happen for each word: for a new note, the full planned field values (per @references/field-plan.md), tags, and target deck; for a duplicate, the existing note id and the planned Example-real-life update (or "sentence already present, no change" if it's already there) plus any fields that would be backfilled. End with a short summary line, e.g. "N new notes, M duplicates updated, K skipped".

## Steps
1. Read the input file: derive the source from the file name, then run `python3 ".claude/skills/add-english-word-to-anki/scripts/parse_input.py" <input file>` to validate and parse the lines. If it exits non-zero, stop and show the user the reported line errors rather than guessing at a fix. On success it prints a JSON array of `{line, word, sentence}` objects (underscores already stripped from `sentence`) — use that as the list of word/sentence pairs.
2. For each word/sentence pair:
    1. Prepare the necessary information for the new flashcard.
        1. Determine the POS of the word as used in the given sentence.
        2. Pick the appropriate Anki tag by POS from @assets/en-pos-anki-tags.md
        3. Pick the appropriate Anki tag by source of the word from @assets/word-sources-anki-tags.md (keep empty if not found). Match loosely (e.g. ignoring a trailing year in the file-derived source, such as "The Guard 2011" matching `source::movie::the-guard`) — this tag lookup is independent of the literal source text shown in fields, which always keeps the full file-derived source (year included).
        4. Determine the base form of the word (e.g. singular/infinitive/dictionary form). This becomes the value used for the English field, prefixed per the collection's convention of avoiding Anki's exact-duplicate warning: `a`/`an` for singular countable nouns (e.g. "a bucket", "an idea"), `to` for verbs (e.g. "to conquer"). Other POS get no prefix.
        5. If the word/entry is multi-word, additionally pick one unit tag:
            - `en::unit::idiom` — figurative meaning, can't be inferred from the individual words (e.g. "kick the bucket" = die)
            - `en::unit::phrase` — literal multi-word unit, meaning follows from the words (e.g. "casting off" = literally departing by boat)
            - `en::unit::collocation` — words that simply commonly co-occur (e.g. "heavy rain", "make a decision")
    2. Check for duplicates: search Anki (`findNotes`) with a broad wildcard on the word (e.g. `English:*beggar*`) and, for each match, `notesInfo` to get its English field and tags. Feed the base-form word (from step 2.1.4, with or without its a/an/to prefix), the POS tag from step 2.1.2, and these candidates into @scripts/find_duplicate.py, which returns the matching note id(s) — it already handles the a/an/to normalization and `en::parts::*` tag-family matching, so don't re-derive that by eye. Zero ids means no duplicate (continue to step 2.3); more than one id is unusual — stop and flag it to the user instead of picking one. If exactly one id is found:
        1. Feed the note's current Example-real-life field value (or `null` if it has none), the *surface-form* word exactly as it appears in the sentence (from @scripts/parse_input.py's output — not the base form), the plain sentence, and the source into @scripts/build_example_html.py. It returns `{html, changed, already_present}`, handling the legacy-plain-text wrap, dedupe-by-sentence, and `<b>` bolding — don't hand-edit the field. If `changed` is true, write its `html` back with `updateNoteFields` (skip the call and just report the planned update if running in dry-run mode); if `already_present`, don't touch the field.
        2. Backfill missing fields: for every field marked "Claude" in @references/field-plan.md's Created-by column (Transcription, Definition, Russian, Synonym1, Synonyms, Antonym1, Antonyms, Examples1-generated) that is empty on the existing note, generate its value the same way as step 2.3 and write it with `updateNoteFields` (skip the call and just report the planned fill if running in dry-run mode) — except don't touch Synonym1/Synonyms/Antonym1/Antonyms if they're empty because the note already carries the matching absence tag from step 2.4 (that's a confirmed "none exist", not missing data). If a backfilled Synonym1/Synonyms/Antonym1/Antonyms turns out to have no value after all, add its absence tag (step 2.4) instead of leaving it silently empty.
        3. Tell the user the word already existed, and summarize what changed (new sentence appended and/or which fields were backfilled, or "nothing to update" if the sentence already existed and no fields were empty), then move to the next input object without creating a new note.
    3. Generate each field value according to @references/field-plan.md. For Example-real-life specifically, call @scripts/build_example_html.py the same way as step 2.2.1 but with `existing: null`, and use its `html` output as the field's initial value.
    4. For each of Synonym1, Synonyms, Antonym1, Antonyms left empty (no such synonym/antonym exists), add the corresponding absence tag: `~api::absent::synonym1`, `~api::absent::synonyms`, `~api::absent::antonym1`, `~api::absent::antonyms` respectively.
    5. Add the new flashcard to deck `En::English` (skip this call, and report the planned note instead, if running in dry-run mode).
