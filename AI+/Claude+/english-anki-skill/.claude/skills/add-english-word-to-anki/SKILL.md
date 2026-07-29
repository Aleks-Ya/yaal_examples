---
name: add-english-word-to-anki
description: Quickly add a new English word to Anki as a flashcard.
---

# Claude skill: Add English word to Anki
This skill creates a new English word note in Anki, including filling all fields for the new note.

## Conventions
Read `shared/references/skill-conventions.md` first — it holds the conventions shared with the
`populate-existing-english-anki-notes` skill: abbreviations (POS/flashcard/NID), the target note
type (`En-word-or-sentence`) and deck (`En::English`), the `--dry-run` and `--no-pictures`
semantics, the single-write rule, and the output-report style. Only the parts specific to *this*
skill are spelled out below.

## Helper scripts and reference docs
Per skill-conventions.md, all deterministic work is delegated to the scripts under
`shared/scripts/` — **run them; never read their source**. Load each reference doc lazily, only when
its step is reached:

- `shared/references/field-plan.md` — how each field's value is derived; read it once when the
  first note's fields are being prepared (step 2.1).
- `shared/references/backfill-routine.md` — the shared routine for filling a note's empty
  Claude-owned fields (single-write rule, media slugs, batch TTS, absence tags); read together
  with field-plan.md.
- `shared/assets/en-pos-anki-tags.md` — POS tag vocabulary; read at step 2.1.2.
- `shared/scripts/list_source_tags.py` — fetches the current `source::*` tag vocabulary live from
  Anki (so newly-added source tags are picked up without editing any file); run at step 2.1.3.
- `shared/assets/new_words.md` — example input file, for humans; don't load it.

## Input provided per card
Input data is provided as a path to a **single** plain-text/Markdown file. H1 headers (`# Source`)
delimit sources — every sentence under a header belongs to that source until the next header:
1. The header text is the source where the sentences under it were found, e.g. `# The Guard 2011` -> source "The Guard 2011". The special header `# NO_SOURCE` marks a source-less section: its sentences are still processed, but the source is `null` (not mentioned in any field or tag). Empty sections (a header with no sentences, e.g. `# Python`) are allowed. `shared/scripts/parse_input.py` emits this `source` per entry (`null` for a `# NO_SOURCE` section) — don't hand-derive it.
2. Each non-blank sentence line is one real-life sentence. The new word (or phrase) within it is marked by wrapping it in single underscores, e.g. `Just _pin_ a medal to me body.` -> word "pin". A non-blank line under a header with no `_..._` marker at all is not a sentence to import — `parse_input.py` silently skips it (no entry, no error) and it stays in the file untouched by step 4's clearing. A line with more than one marker, or a sentence before the first header, is a validation error (parse_input.py exits non-zero).

## Mode
If the arguments include `--dry-run` (in addition to the file path, in either order), run in **dry-run mode**. Otherwise run in **live mode** (default): e.g. `/add-english-word-to-anki --dry-run '/home/aleks/tmp/new_anki_words.md'` vs. `/add-english-word-to-anki '/path/to/file.md'`.

`--dry-run` and `--no-pictures` follow the shared semantics in skill-conventions.md. Skill-specific
details:
- The input file acts as an inbox of new words to import. In **live mode**, as a final step
  (step 4), the skill **removes the cleanly-imported sentence lines** from the file while keeping
  **every header (even ones left empty)**; sentences that weren't cleanly processed stay under their
  header (see step 4 for the exact rule). Editing the file is a mutation, so in **dry-run mode** the
  input file is left completely untouched (the report instead counts how many sentences *would* be
  cleared). `--no-pictures` has no effect on this: a Picture skip is a normal successful outcome, so
  a `--no-pictures` note still counts as cleanly imported. Note a Picture skip in the row's Outcome,
  e.g. "Created new note (Picture skipped)".
- In dry-run mode the read-only work still performed is the `find_duplicate.py` duplicate check and
  the `search_images.py` lookup plus candidate download/visual check for Picture (unless
  `--no-pictures` is also given); the note-mutating calls skipped are `addNote`/`addNotes`,
  `updateNoteFields`, `addTags`, `storeMediaFile`. What to report per word: for a new note, the full
  planned field values (per field-plan.md), tags, and target deck; for a duplicate, the existing
  note id and the planned Example-real-life update (or "sentence already present, no change" if it's
  already there) plus any fields that would be backfilled.

## Output report
Follow the output-report style in skill-conventions.md (one accumulated Markdown table, no per-word
narration, one row per word/sentence pair in input order, then an aggregate summary line). This
skill's aggregate line reads e.g. "N new notes, M duplicates updated, K skipped". Also append an
inbox-cleared note (step 4): in live mode "; cleared 5 imported sentence(s)", or in dry-run mode
"; would clear 5 imported sentence(s)" (omit if zero).

Columns:
- **NID** — the Anki note id: the existing note's id for a duplicate (whether or not it was backfilled); the newly created note's id (`addNote`'s return value) for a new note in live mode; the literal `(new)` for a new note in dry-run mode (no id exists yet since `addNote` is skipped).
- **Source** — the entry's `source` (its section's `# header` text, from `parse_input.py`'s output), e.g. "The Guard 2011"; blank for a `# NO_SOURCE` section (source `null`).
- **Word** — the surface-form word/phrase exactly as it appears in the input sentence (from `parse_input.py`'s output), e.g. "beggars".
- **Base form** — the dictionary/base form used for the English field (step 2.1.4), including its a/an/to prefix, e.g. "a beggar".
- **POS** — the plain human-readable POS word (noun/verb/adjective/adverb/preposition/etc.), not the `en::parts::*` tag.
- **Outcome** — a free-text description of what happened, e.g.: "Created new note" / "Created new note (no image found)" for a new note; "Duplicate — appended new sentence" (optionally with "; backfilled Definition, Picture") for a duplicate that changed; "Duplicate — nothing to update" for a duplicate with no changes; "Ambiguous match (N candidates) — skipped" when `find_duplicate.py` returns more than one id.

Example:
```
| NID            | Source         | Word    | Base form     | POS  | Outcome                            |
|----------------|----------------|---------|---------------|------|-------------------------------------|
| 1579307261208  | The Guard 2011 | beggars | a beggar      | noun | Duplicate — appended new sentence  |
| (new)          | The Guard 2011 | pin     | to pin        | verb | Created new note (Picture found)   |
| 1482172556889  | The Guard 2011 | batting | to bat around | verb | Duplicate — nothing to update      |
```

## Steps
1. Read the input: run `python3 "shared/scripts/parse_input.py" <input file>` to validate and parse the lines. Lines with no `_..._` marker at all are not errors — the script silently skips them (no entry) and leaves them in the file. If it exits non-zero, stop and show the user the reported line errors (e.g. `line 3: multiple words marked with _..._`, or a sentence before the first `# source` header) rather than guessing at a fix. On success it prints a JSON object `{entries}`: `entries` is a JSON array of `{source, line, word, sentence}` objects (underscores already stripped from `sentence`, `source` = the section's `# header` text, or `null` for a `# NO_SOURCE` section) — use that as the list of word/sentence pairs. Keep each entry's `line` — step 4 needs it to clear the imported sentences from the file.
2. For each word/sentence pair:
    1. Prepare the necessary information for the new flashcard (read `shared/references/field-plan.md` and `shared/references/backfill-routine.md` now, if not already loaded).
        1. Determine the POS of the word as used in the given sentence.
        2. Pick the appropriate Anki tag(s) by POS from `shared/assets/en-pos-anki-tags.md` (read it
           now, if not already loaded) — follow its "Choosing the right tag" guidance: use the *most
           specific* applicable sub-tag(s) (e.g. `en::parts::noun::countable`, not a bare
           `en::parts::noun`), applying every sub-tag that fits and omitting the bare parent when a
           sub-tag applies.
        3. Pick the appropriate Anki tag by source of the word (the entry's `source` from step 1). Get the current source-tag vocabulary by running `python3 "shared/scripts/list_source_tags.py"` **once per run** — it queries Anki live (`getTags`, filtered to the `source::` prefix) and prints `{"source_tags": [...]}`; cache that list and reuse it for the remaining words rather than re-running it per word. Match the entry's `source` against that live list, keeping empty if not found. If the entry's `source` is `null` (a `# NO_SOURCE` section), apply no source tag at all. Otherwise match loosely (e.g. ignoring a trailing year in the source, such as "The Guard 2011" matching `source::movie::the-guard`) — this tag lookup is independent of the literal source text shown in fields, which always keeps the full header-derived source (year included).
        4. Determine the base form of the word (e.g. singular/infinitive/dictionary form). This becomes the value used for the English field, prefixed per the collection's convention of avoiding Anki's exact-duplicate warning: `a`/`an` for singular countable nouns (e.g. "a bucket", "an idea"), `to` for verbs (e.g. "to conquer"). Other POS get no prefix.
        5. If the word/entry is multi-word, additionally pick one unit tag:
            - `en::unit::idiom` — figurative meaning, can't be inferred from the individual words (e.g. "kick the bucket" = die)
            - `en::unit::phrase` — literal multi-word unit, meaning follows from the words (e.g. "casting off" = literally departing by boat)
            - `en::unit::collocation` — words that simply commonly co-occur (e.g. "heavy rain", "make a decision")
    2. Check for duplicates in **one call**: run `python3 "shared/scripts/find_duplicate.py" "<base form from step 2.1.4>" "<POS tag from step 2.1.2>"`. It queries AnkiConnect directly (a wildcard `findNotes` scoped to the note type + `notesInfo`) and already handles the a/an/to normalization and `en::parts::*` tag-family matching — don't re-derive that by eye, and don't run `findNotes`/`notesInfo` through the MCP tools for this. It prints `{duplicates, candidates_checked, note}`. Zero ids in `duplicates` means no duplicate (continue to step 2.3); more than one id is unusual — record a report-table row (see `## Output report`) flagging the ambiguous match, then move on to the next word without creating or updating a note, instead of picking one. If exactly one id is found, `note` holds everything needed — `{id, tags, fields}` (the small text fields, incl. `English` and `Example-real-life`) and `status` (a ready-made `note_status.py` result) — so no follow-up `notesInfo` is needed. Then, collecting **all** field changes from the sub-steps below into **one** `updateNoteFields` call (the routine's single-write rule; skip it in dry-run mode):
        1. Normalize the English article per backfill-routine.md step E, using `note.fields.English`. (Whether `English-audio-generated` is stale, i.e. currently non-empty, follows from `note.status`: it is non-empty iff it is *not* listed in `status.audio_to_generate`.) Note the edit in the row's Outcome (e.g. "added article to English, regenerated audio").
        2. Feed the note's current `Example-real-life` value (from `note.fields`, or `null` if empty), the *surface-form* word exactly as it appears in the sentence (from `parse_input.py`'s output — not the base form), the plain sentence, and the entry's `source` (pass it through as-is; a `null` source is fine — the script then omits the `(Source)` suffix) into `shared/scripts/build_example_html.py` (JSON in on stdin, JSON out). It returns `{html, changed, already_present}`, handling the legacy-plain-text wrap, dedupe-by-sentence, and `<b>` bolding — don't hand-edit the field. If `changed` is true, include its `html` in the single write; if `already_present`, don't touch the field.
        3. Backfill the existing note's empty Claude-owned fields by running the shared backfill routine (backfill-routine.md), using the base-form word (step 2.1.4) and the POS tag from step 2.1.2, and **reusing `note.status` as the routine's step B1 output** (don't call `note_status.py` again for the worklist). It fills only the empty fields (respecting the absence tags from step 2.4), synthesizes the needed audio in one batch, and tags genuine absences. Skip every mutating call in dry-run mode and report the planned fills instead.
        4. Record a report-table row for this word (see `## Output report`) — Outcome summarizes what changed (article added to English, new sentence appended and/or which fields were backfilled, or "nothing to update" if the sentence already existed, English already had its article, and no fields were empty) — then move to the next input object without creating a new note.
    3. Build the new note's fields. Set `English` to the base form from step 2.1.4 (with its a/an/to prefix). Set `Example-real-life` by calling `shared/scripts/build_example_html.py` the same way as step 2.2.2 but with `existing: null`, using its `html` output. Fill every remaining Claude-owned field — and its audio — by running the shared backfill routine (backfill-routine.md): on a brand-new note all those fields are empty, so the routine fills them all per field-plan.md, synthesizes the audio in one batch, and applies any absence tags (step 2.4); the values land in the single final `addNote` call (skip the `storeMediaFile`/mutating calls in dry-run mode).
    4. Absence tags (the routine in step 3 applies these; the duplicate backfill in step 2.2.3 reuses the same list): for each of Synonym1, Synonyms, Antonym1, Antonyms left empty because no such synonym/antonym exists, add `~api::absent::synonym1`, `~api::absent::synonyms`, `~api::absent::antonym1`, `~api::absent::antonyms` respectively; if no suitable image was found for Picture (see field-plan.md), leave it empty and add `~api::absent::picture`.
    5. Add the new flashcard to deck `En::English` (skip this call if running in dry-run mode), then record a report-table row for this word (see `## Output report`).
3. Once every word/sentence pair has been processed, print the report table (one row per word, in input order) followed by the aggregate summary line.
4. Clear the imported sentences from the input file (the inbox-clearing step — see `## Mode`). Collect the `line` (from step 1's entries) of every **cleanly-imported** entry — every entry whose Outcome was a new note created, or a duplicate created/updated/"nothing to update" — i.e. **all entries except** any with an ambiguous-match skip ("Ambiguous match (N candidates) — skipped"; the only per-word skip, whose sentence is left in place so nothing is lost). Then:
    - In **live mode**, remove those lines by running `python3 "shared/scripts/parse_input.py" --clear <input file>` with `{"remove_lines": [<the collected line numbers>]}` on stdin (e.g. `echo '{"remove_lines": [2, 5, 6]}' | python3 "shared/scripts/parse_input.py" --clear "<path>"`). It deletes exactly those lines and keeps every other line — **all headers included, even ones whose section is now empty** — verbatim, and prints `{"removed": N}`. The `<input file>` is the original path argument (a single file). Don't clear anything if the list is empty.
    - In **dry-run mode**, do **not** touch the file; only count how many lines *would* be cleared (the size of the collected list).
   Report the count in the summary line per `## Output report`.
