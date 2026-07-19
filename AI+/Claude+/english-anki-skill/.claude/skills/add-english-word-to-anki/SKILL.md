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
NID = Note ID

## Input provided per card
Input data is provided as a path to **either** a single plain-text/Markdown file **or** a folder of them
(only regular `.md`/`.txt` files, non-recursive; other files and empty files are skipped):
1. Each file's name (without extension) is the source where every word in that file was found, e.g. `The Guard.md` -> source "The Guard". `@shared/scripts/parse_input.py` emits this `source` per entry — don't hand-derive it.
2. Each non-empty line is one real-life sentence. The new word (or phrase) within it is marked by wrapping it in single underscores, e.g. `Just _pin_ a medal to me body.` -> word "pin".

Validation and parsing of the marked lines is done by @shared/scripts/parse_input.py, not by hand — see
step 1. Duplicate matching and example-sentence HTML are likewise handled by
@shared/scripts/find_duplicate.py and @shared/scripts/build_example_html.py — see step 2.2. Downloading and
shrinking the image for the Picture field (temp file deleted after `storeMediaFile`) is handled
by @shared/scripts/fetch_and_resize_image.py — see @shared/references/field-plan.md's Picture row.
Synthesizing the mp3 for the audio fields (English/Definition/Synonym1/Antonym1) is handled by
@shared/scripts/generate_tts.py — see @shared/references/field-plan.md's Audio procedure (in the
English-audio-generated row).

Example of an input file: @"shared/assets/The Guard.md"

## Mode
If the arguments include `--dry-run` (in addition to the file/folder path, in either order), run in **dry-run mode**. Otherwise run in **live mode** (default): e.g. `/add-english-word-to-anki --dry-run '/home/aleks/tmp/!new_anki_words'` vs. `/add-english-word-to-anki '/path/to/file.md'`.

In dry-run mode, still perform all read-only lookups (`findNotes`, `notesInfo`, `listDecks`, `modelFieldNames`) and the `WebSearch`/`WebFetch` image lookup for Picture, needed to do the duplicate check and report accurate results, but skip every note-mutating call (`addNote`/`addNotes`, `updateNoteFields`, `addTags`, `storeMediaFile`). Instead of mutating, report what would happen for each word: for a new note, the full planned field values (per @shared/references/field-plan.md), tags, and target deck; for a duplicate, the existing note id and the planned Example-real-life update (or "sentence already present, no change" if it's already there) plus any fields that would be backfilled.

## Output report
Don't narrate results per word while processing. Instead, accumulate one row per word/sentence pair (in input order) and print them all as a single Markdown table once every pair has been processed, immediately followed by an aggregate summary line, e.g. "N new notes, M duplicates updated, K skipped". If any input files were skipped as empty (the `skipped` list from step 1), append them to that summary, e.g. "; P empty files skipped: Novartis".

Columns:
- **NID** — the Anki note id: the existing note's id for a duplicate (whether or not it was backfilled); the newly created note's id (`addNote`'s return value) for a new note in live mode; the literal `(new)` for a new note in dry-run mode (no id exists yet since `addNote` is skipped).
- **Source** — the entry's `source` (the originating file's name without extension, from @shared/scripts/parse_input.py's output), e.g. "The Guard 2011".
- **Word** — the surface-form word/phrase exactly as it appears in the input sentence (from @shared/scripts/parse_input.py's output), e.g. "beggars".
- **Base form** — the dictionary/base form used for the English field (step 2.1.4), including its a/an/to prefix, e.g. "a beggar".
- **POS** — the plain human-readable POS word (noun/verb/adjective/adverb/preposition/etc.), not the `en::parts::*` tag.
- **Outcome** — a free-text description of what happened, e.g.: "Created new note" / "Created new note (no image found)" for a new note; "Duplicate — appended new sentence" (optionally with "; backfilled Definition, Picture") for a duplicate that changed; "Duplicate — nothing to update" for a duplicate with no changes; "Ambiguous match (N candidates) — skipped" when @shared/scripts/find_duplicate.py returns more than one id.

Example:
```
| NID            | Source         | Word    | Base form     | POS  | Outcome                            |
|----------------|----------------|---------|---------------|------|-------------------------------------|
| 1579307261208  | The Guard 2011 | beggars | a beggar      | noun | Duplicate — appended new sentence  |
| (new)          | The Guard 2011 | pin     | to pin        | verb | Created new note (Picture found)   |
| 1482172556889  | The Guard 2011 | batting | to bat around | verb | Duplicate — nothing to update      |
```

## Steps
1. Read the input: run `python3 "shared/scripts/parse_input.py" <input file or folder>` to validate and parse the lines. If it exits non-zero, stop and show the user the reported line errors (each prefixed with its file name) rather than guessing at a fix. On success it prints a JSON object `{entries, skipped}`: `entries` is a JSON array of `{source, file, line, word, sentence}` objects (underscores already stripped from `sentence`, `source` = the originating file's name without extension) — use that as the list of word/sentence pairs; `skipped` lists any empty input files that were skipped — report them in the summary (see `## Output report`).
2. For each word/sentence pair:
    1. Prepare the necessary information for the new flashcard.
        1. Determine the POS of the word as used in the given sentence.
        2. Pick the appropriate Anki tag(s) by POS from @shared/assets/en-pos-anki-tags.md — follow
           its "Choosing the right tag" guidance: use the *most specific* applicable sub-tag(s)
           (e.g. `en::parts::noun::countable`, not a bare `en::parts::noun`), applying every
           sub-tag that fits and omitting the bare parent when a sub-tag applies.
        3. Pick the appropriate Anki tag by source of the word (the entry's `source` from step 1) from @shared/assets/word-sources-anki-tags.md (keep empty if not found). Match loosely (e.g. ignoring a trailing year in the source, such as "The Guard 2011" matching `source::movie::the-guard`) — this tag lookup is independent of the literal source text shown in fields, which always keeps the full file-derived source (year included).
        4. Determine the base form of the word (e.g. singular/infinitive/dictionary form). This becomes the value used for the English field, prefixed per the collection's convention of avoiding Anki's exact-duplicate warning: `a`/`an` for singular countable nouns (e.g. "a bucket", "an idea"), `to` for verbs (e.g. "to conquer"). Other POS get no prefix.
        5. If the word/entry is multi-word, additionally pick one unit tag:
            - `en::unit::idiom` — figurative meaning, can't be inferred from the individual words (e.g. "kick the bucket" = die)
            - `en::unit::phrase` — literal multi-word unit, meaning follows from the words (e.g. "casting off" = literally departing by boat)
            - `en::unit::collocation` — words that simply commonly co-occur (e.g. "heavy rain", "make a decision")
    2. Check for duplicates: search Anki (`findNotes`) with a broad wildcard on the word (e.g. `English:*beggar*`) and, for each match, `notesInfo` to get its English field and tags. Feed the base-form word (from step 2.1.4, with or without its a/an/to prefix), the POS tag from step 2.1.2, and these candidates into @shared/scripts/find_duplicate.py, which returns the matching note id (s) — it already handles the a/an/to normalization and `en::parts::*` tag-family matching, so don't re-derive that by eye. Zero ids means no duplicate (continue to step 2.3); more than one id is unusual — record a report-table row (see `## Output report`) flagging the ambiguous match, then move on to the next word without creating or updating a note, instead of picking one. If exactly one id is found:
        1. Feed the note's current Example-real-life field value (or `null` if it has none), the *surface-form* word exactly as it appears in the sentence (from @shared/scripts/parse_input.py's output — not the base form), the plain sentence, and the source into @shared/scripts/build_example_html.py. It returns `{html, changed, already_present}`, handling the legacy-plain-text wrap, dedupe-by-sentence, and `<b>` bolding — don't hand-edit the field. If `changed` is true, write its `html` back with `updateNoteFields` (skip the call and just report the planned update if running in dry-run mode); if `already_present`, don't touch the field.
        2. Backfill missing fields: for every field marked "Claude" in @shared/references/field-plan.md's Created-by column (Transcription, Definition, Picture, Russian, Synonym1, Synonyms, Antonym1, Antonyms, Examples1-generated) that is empty on the existing note, generate its value the same way as step 2.3 and write it with `updateNoteFields` (skip the call and just report the planned fill if running in dry-run mode) — except don't touch Synonym1/Synonyms/Antonym1/Antonyms/Picture if they're empty because the note already carries the matching absence tag from step 2.4 (that's a confirmed "none exist", not missing data). If a backfilled Synonym1/Synonyms/Antonym1/Antonyms/Picture turns out to have no value after all, add its absence tag (step 2.4) instead of leaving it silently empty. Then backfill the audio fields (English-audio-generated, Definition-audio-generated, and — only when the corresponding text field is non-empty — Synonym1-audio-generated / Antonym1-audio-generated): for each whose audio field is empty and whose source text field is non-empty (including a source field just backfilled above), generate the mp3 per field-plan.md's Audio procedure (`generate_tts.py` → `storeMediaFile` → set field to `[sound:…]`; skip the `storeMediaFile`/`updateNoteFields` calls in dry-run mode).
        3. Record a report-table row for this word (see `## Output report`) — Outcome summarizes what changed (new sentence appended and/or which fields were backfilled, or "nothing to update" if the sentence already existed and no fields were empty) — then move to the next input object without creating a new note.
    3. Generate each field value according to @shared/references/field-plan.md. For Example-real-life specifically, call @shared/scripts/build_example_html.py the same way as step 2.2.1 but with `existing: null`, and use its `html` output as the field's initial value. For the audio fields (English-audio-generated, Definition-audio-generated, and — only when the field is non-empty — Synonym1-audio-generated / Antonym1-audio-generated), generate each mp3 per field-plan.md's Audio procedure (`generate_tts.py` → `storeMediaFile` → set field to `[sound:…]`; skip the `storeMediaFile` call in dry-run mode, as with Picture).
    4. For each of Synonym1, Synonyms, Antonym1, Antonyms left empty (no such synonym/antonym exists), add the corresponding absence tag: `~api::absent::synonym1`, `~api::absent::synonyms`, `~api::absent::antonym1`, `~api::absent::antonyms` respectively. Likewise, if no suitable image was found for Picture (see @shared/references/field-plan.md), leave it empty and add `~api::absent::picture`.
    5. Add the new flashcard to deck `En::English` (skip this call if running in dry-run mode), then record a report-table row for this word (see `## Output report`).
3. Once every word/sentence pair has been processed, print the report table (one row per word, in input order) followed by the aggregate summary line.
