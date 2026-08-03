---
name: populate-existing-english-anki-notes
description: Backfill missing fields on existing English Anki notes flagged with en::to-refine.
---

# Claude skill: Populate existing English Anki notes
This skill finds existing English notes the user has flagged for completion (tag `en::to-refine`)
and backfills the empty Claude-owned fields on each one. It does **not** create notes and does
**not** take an input file — it operates on notes already in the collection.

## Conventions
Read `shared/references/skill-conventions.md` first — it holds the conventions shared with the
`add-english-word-to-anki` skill: abbreviations (POS/flashcard/NID), the target note type
(`En-word-or-sentence`) and deck (`En::English`), the `--dry-run` and `--no-pictures` semantics, the
single-write rule, and the output-report style. Only the parts specific to *this* skill are spelled
out below.

## Shared logic
The core of this skill is the shared **backfill routine**: `shared/references/backfill-routine.md`
(read it, plus `shared/references/field-plan.md`, before processing the first note). field-plan.md
is an **index**: its rows link to per-field reference files (article prefix, definition rules, audio,
picture) — open one when that field is actually being generated. It names every
helper script involved — per skill-conventions.md, **run the scripts under `shared/scripts/`; never
read their source**. Read `shared/assets/en-pos-anki-tags.md` at step 2.1 (POS tags, incl.
"Reconciling an existing note's tag"). This skill does not parse input files and does not run a
duplicate search — the notes are already in hand.

## Mode
If the arguments include `--dry-run`, run in **dry-run mode**; otherwise run in **live mode**
(default), e.g. `/populate-existing-english-anki-notes --dry-run` vs.
`/populate-existing-english-anki-notes`.

`--dry-run` and `--no-pictures` follow the shared semantics in skill-conventions.md. Skill-specific
details:
- In dry-run mode the read-only lookups still performed are `findNotes`, `notesInfo`,
  `modelFieldNames`, `listDecks` and the `search_images.py` lookup plus candidate download/visual
  check for Picture (unless `--no-pictures` is also given); the note-mutating calls skipped are
  `updateNoteFields`, `addTags`, `removeTags`, `storeMediaFile`. What to report per note: which
  fields *would* be backfilled (with their planned values), which absence tags *would* be added, and
  whether `en::to-refine` *would* be removed.
- Under `--no-pictures`, the step 2.3 completeness verdict's `note_status.py` call also takes the
  flag, so an otherwise-complete note still has `en::to-refine` removed even though its Picture stays
  empty. Note the skip in the row's Outcome, e.g. "Backfilled Definition; Picture skipped".

Optionally the arguments may include `--limit N` (any positive integer) to process at most `N`
notes in this run — image search is per-note and expensive, so this is useful for a first pass.
If omitted, process every matching note. Combinable with `--dry-run` and `--no-pictures` in any order.

## Output report
Follow the output-report style in skill-conventions.md (one accumulated Markdown table, no per-note
narration, one row per processed note in the order returned by `findNotes`, then an aggregate
summary line). This skill's aggregate line reads e.g. "N notes completed, M partially filled, K
unchanged".

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
2. For each note (collect **all** field changes from steps 2.1–2.2 into **one** `updateNoteFields`
   call per note — the backfill routine's single-write rule; skip it in dry-run mode):
    1. Identify the word and its POS from the note itself (there is no input sentence):
        - The word/base form is the note's `English` field value. If `English` is empty, the note
          is broken — record a report row with Outcome "Skipped: empty English field", do not
          backfill, and move to the next note.
        - Determine the word's POS from its current `en::parts::*` tag(s) (the note may also carry
          an `en::unit::*` tag) and, where the tag is missing or too coarse, from the
          `Definition`/`English`. Derive the plain POS word from it for the report.
        - Reconcile the note's POS tag(s) with the *most specific* applicable tag(s) following
          `shared/assets/en-pos-anki-tags.md`'s "Choosing the right tag" and "Reconciling an
          existing note's tag" guidance (read that file now, if not already loaded; the four cases
          there: no tag → add; bare parent + sub-tag applies → remove the bare parent **first**, then
          add the sub-tag(s) — `removeTags` on a parent also strips its `::` sub-tags, so the
          reverse order deletes the just-added sub-tag; bare parent + no sub-tag → keep; already
          correct → keep; tag mutations skipped in dry-run mode but reported). Do these two calls
          (`removeTags` bare parent, then `addTags` sub-tags) **as a back-to-back pair for this note
          before moving to the next note** — never defer/batch them into a separate later pass. In a
          multi-note run, do **not** add all sub-tags across notes first and remove the bare parents
          afterward: a later bulk `removeTags` of a parent (e.g. `en::parts::noun`) strips the
          `en::parts::noun::*` sub-tags you already added on *every* note. (This has actually
          happened: 53 just-added sub-tags silently wiped.) Mention any POS-tag change in the row's
          Outcome (e.g. "refined POS tag to noun::countable").
        - Normalize the English article per backfill-routine.md step E (prefix rules:
          `shared/references/english-article-prefix.md`). Mention the article edit and any resulting audio
          regeneration in the row's Outcome — neither counts as a "Filled" field, since that
          column is reserved for fields backfilled from empty in step 2.2.
    2. Backfill the empty Claude-owned fields by running the shared **backfill routine**
       (`shared/references/backfill-routine.md`), using the note's own fields+tags, its `English`
       value as the word, and the POS from step 2.1. That routine (via `note_status.py`) decides
       which fields are empty vs. absence-tagged, fills the text fields (Picture, Examples1,
       Definition, Russian, synonyms/antonyms, Transcription), synthesizes the needed audio in one
       batch, and tags genuine absences. `Example-real-life` and non-Claude fields are left
       untouched (there is no new sentence to add). The fields it fills this run are the "Filled"
       column of the report row.
    3. Decide the `en::to-refine` tag per the routine's step B5: run `note_status.py` once on the
       note's **final** state (fields as written this run + tags; add `--no-pictures` when that
       flag is active); if `remove_refine_tag` is true
       (i.e. `complete` and the note still carries `en::to-refine`), remove the tag with
       `removeTags` (skip the call in dry-run mode and report it as "kept (dry-run)"); otherwise
       keep the tag so the note stays in the queue. Use `incomplete_reasons` to explain a "kept
       (still incomplete)" row.
    4. Record a report-table row for this note (see `## Output report`).
3. Once every note has been processed, print the report table (one row per note) followed by the
   aggregate summary line.
