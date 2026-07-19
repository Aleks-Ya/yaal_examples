---
name: populate-existing-english-anki-notes
description: Backfill missing fields on existing English Anki notes flagged with en::to-refine.
---

# Claude skill: Populate existing English Anki notes
This skill finds existing English notes the user has flagged for completion (tag `en::to-refine`)
and backfills the empty Claude-owned fields on each one. It does **not** create notes and does
**not** take an input file — it operates on notes already in the collection.

Target note type is `En-word-or-sentence`. Target deck is `En::English`.

## Abbreviations and synonyms
POS = Part Of Speech
flashcard = note
NID = Note ID

## Shared logic
This skill reuses the same field-derivation rules, helper scripts, and tag vocabularies as the
sibling `add-english-word-to-anki` skill, all kept once under `shared/`:
- Per-field value rules: @shared/references/field-plan.md (the "Created by" column marks which
  fields are Claude-owned and therefore backfillable).
- Picture download/resize: @shared/scripts/fetch_and_resize_image.py.
- Example-sentence HTML list builder (used for Examples1-generated): @shared/scripts/build_example_html.py.
- Audio (mp3) synthesis for the English/Definition/Synonym1/Antonym1 audio fields:
  @shared/scripts/generate_tts.py (Google Cloud TTS) — see field-plan.md's Audio procedure.
- Tag vocabularies: @shared/assets/en-pos-anki-tags.md (POS tags).

This skill does **not** parse input files (no @shared/scripts/parse_input.py) and does **not** run
a duplicate search (no @shared/scripts/find_duplicate.py) — the note is already in hand.

## Mode
If the arguments include `--dry-run`, run in **dry-run mode**; otherwise run in **live mode**
(default), e.g. `/populate-existing-english-anki-notes --dry-run` vs.
`/populate-existing-english-anki-notes`.

In dry-run mode, still perform all read-only lookups (`findNotes`, `notesInfo`, `modelFieldNames`,
`listDecks`) and the `WebSearch`/`WebFetch` image lookup for Picture, needed to report accurate
results, but skip every note-mutating call (`updateNoteFields`, `addTags`, `removeTags`,
`storeMediaFile`). Instead of mutating, report for each note which fields *would* be backfilled
(with their planned values), which absence tags *would* be added, and whether `en::to-refine`
*would* be removed.

Optionally the arguments may include `--limit N` (any positive integer) to process at most `N`
notes in this run — image search is per-note and expensive, so this is useful for a first pass.
If omitted, process every matching note.

## Output report
Don't narrate results per note while processing. Instead, accumulate one row per processed note
(in the order returned by `findNotes`) and print them all as a single Markdown table once every
note has been processed, immediately followed by an aggregate summary line, e.g. "N notes
completed, M partially filled, K unchanged".

Columns:
- **NID** — the Anki note id.
- **Word** — the note's `English` field value (base form, including its a/an/to prefix), e.g. "a beggar".
- **POS** — the plain human-readable POS word (noun/verb/adjective/etc.), derived from the note's
  `en::parts::*` tag — not the tag itself.
- **Filled** — comma-separated list of the fields backfilled this run (or "—" if none).
- **Refine tag** — "removed (complete)" / "kept (still incomplete)" / "kept (dry-run)".
- **Outcome** — free-text description of what happened, e.g. "Backfilled Definition, Picture;
  note now complete" / "Backfilled Russian; still missing Picture" / "Refined POS tag to
  noun::countable; backfilled Definition" / "Nothing to fill" / "Skipped: empty English field".

Example:
```
| NID           | Word     | POS  | Filled              | Refine tag              | Outcome                              |
|---------------|----------|------|---------------------|-------------------------|--------------------------------------|
| 1579307261208 | a beggar | noun | Picture             | removed (complete)      | Backfilled Picture; note now complete |
| 1482172556889 | to bat   | verb | Russian, Synonyms   | kept (still incomplete) | Backfilled Russian, Synonyms; no image found |
```

## Steps
1. Find the notes to process: `findNotes` with the query `tag:en::to-refine note:En-word-or-sentence`.
   For each returned id, call `notesInfo` to get its field values and tags. (Use `modelFieldNames`
   / `listDecks` as needed — all read-only.) If `--limit N` was given, keep only the first `N` ids.
   If no notes match, report that and stop.
2. For each note:
    1. Identify the word and its POS from the note itself (there is no input sentence):
        - The word/base form is the note's `English` field value. If `English` is empty, the note
          is broken — record a report row with Outcome "Skipped: empty English field", do not
          backfill, and move to the next note.
        - Determine the word's POS from its current `en::parts::*` tag(s) (the note may also carry
          an `en::unit::*` tag) and, where the tag is missing or too coarse, from the
          `Definition`/`English`. Derive the plain POS word from it for the report.
        - Reconcile the note's POS tag(s) with the *most specific* applicable tag(s) per
          @shared/assets/en-pos-anki-tags.md's "Choosing the right tag" guidance, then fix the note
          to match (all tag mutations skipped in dry-run mode, but reported):
            - No `en::parts::*` tag at all → `addTags` the most specific applicable tag(s).
            - A bare parent tag (e.g. `en::parts::noun`) while a specific sub-tag applies (e.g. the
              word is a countable, irregular-plural noun) → `addTags` every applicable sub-tag
              (e.g. `en::parts::noun::countable`, `en::parts::noun::irregular`) and `removeTags` the
              now-redundant bare parent — the sub-tag replaces it.
            - A bare parent tag while **no** sub-tag applies (e.g. a plain regular verb keeps
              `en::parts::verb`, a plain adjective keeps `en::parts::adjective`, an adverb, etc.) →
              leave it as-is; the bare tag is already the correct final classification.
            - Already carrying the correct specific tag(s) → leave as-is.
          Mention any POS-tag change in the row's Outcome (e.g. "refined POS tag to noun::countable").
    2. Backfill missing fields: for every field marked "Claude" in @shared/references/field-plan.md's
       Created-by column (Transcription, Definition, Picture, Russian, Synonym1, Synonyms, Antonym1,
       Antonyms, Examples1-generated) that is **empty** on the note, generate its value per
       @shared/references/field-plan.md and write it with `updateNoteFields` (skip the call and just
       report the planned fill in dry-run mode). Specifics:
        - Skip Synonym1/Synonyms/Antonym1/Antonyms/Picture if the note already carries the matching
          absence tag (`~api::absent::synonym1` / `~api::absent::synonyms` / `~api::absent::antonym1`
          / `~api::absent::antonyms` / `~api::absent::picture`) — that's a confirmed "none exist",
          not missing data.
        - **Picture:** search for an image of the word in the sense/POS given by the note's
          `Definition` (and `Example-real-life`, if present), per field-plan.md's Picture row —
          `WebSearch` for a direct image URL (`WebFetch` a promising page if needed), then
          `python3 "shared/scripts/fetch_and_resize_image.py" <url> <temp_output_path> 600`, then
          `storeMediaFile` with `filename` a slug of word+POS (e.g. `beggar-noun.jpg`), delete the
          temp file, and set the field to `<img src="filename">`.
        - **Examples1-generated:** generate up to 10 example sentences using the word and build the
          `<ul><li>…</li></ul>` list with @shared/scripts/build_example_html.py (one call per
          sentence, threading its `html` output back in as the next `existing`, starting from
          `existing: null`, with `source: null`) — exactly as the add skill builds this field. Do
          not touch `Example-real-life` (that is user-owned real-life-sentence data and there is no
          new sentence to add).
        - Transcription / Definition / Russian / Synonym1 / Synonyms / Antonym1 / Antonyms:
          generate directly per field-plan.md.
        - **Audio (English-audio-generated, Definition-audio-generated, Synonym1-audio-generated,
          Antonym1-audio-generated):** for each whose audio field is empty and whose source text
          field (English / Definition / Synonym1 / Antonym1 respectively) is non-empty — including a
          source field just backfilled this run — synthesize the mp3 per field-plan.md's Audio
          procedure: `python3 "shared/scripts/generate_tts.py" "<text>" <temp.mp3>`, `storeMediaFile`
          under a word+POS+field slug (e.g. `beggar-noun-english.mp3`), delete the temp file, and set
          the field to `[sound:<filename>]`. Synonym1/Antonym1 audio is skipped when that text field
          is empty (or absence-tagged).
        - If a backfilled Synonym1/Synonyms/Antonym1/Antonyms/Picture turns out to have no value
          after all, add its corresponding absence tag (see the list above) with `addTags` instead
          of leaving it silently empty (skip the call in dry-run mode).
        - Leave `Example-real-life` and every non-Claude field (Tense, Comment, the obsolete
          `*-generated` fields, `Synonyms-audio-generated`/`Antonyms-audio-generated`, etc.) untouched.
    3. Decide the `en::to-refine` tag: the note is **fully complete** when every Claude field is
       either non-empty **or** (only for Synonym1/Synonyms/Antonym1/Antonyms/Picture) empty with its
       matching absence tag present. Transcription, Definition, Russian, and Examples1-generated have
       no absence tag, so they must be non-empty to count as complete. The four audio fields **also**
       count: English-audio-generated and Definition-audio-generated must be non-empty; Synonym1- and
       Antonym1-audio-generated must be non-empty **unless** their source text field is empty/absent
       (in which case no audio is expected). `Example-real-life` and the empty-by-design
       `Synonyms-audio-generated`/`Antonyms-audio-generated` do **not** affect completeness. If the
       note is fully complete, remove the `en::to-refine` tag with `removeTags` (skip the call in
       dry-run mode and report it as "kept (dry-run)"). Otherwise keep the tag so the note stays in
       the queue.
    4. Record a report-table row for this note (see `## Output report`).
3. Once every note has been processed, print the report table (one row per note) followed by the
   aggregate summary line.
