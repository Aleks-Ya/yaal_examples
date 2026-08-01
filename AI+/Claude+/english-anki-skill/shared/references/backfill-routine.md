# Backfill routine (shared)

The canonical procedure for **filling every empty Claude-owned field of one `En-word-or-sentence`
note already in hand** (its field values + tags known). Both skills run it:

- `add-english-word-to-anki` — on a **new** note (all Claude fields empty, so this fills everything)
  and on a **duplicate** note (only the still-empty fields).
- `populate-existing-english-anki-notes` — on each `en::to-refine` note.

It covers only the Claude-owned fields. `Example-real-life` and every non-Claude field are **out of
scope** here and handled by each skill separately (the add skill appends the real-life sentence via
`shared/scripts/build_example_html.py`; the populate skill leaves `Example-real-life` untouched).

The helper scripts below are **run, never read** — their CLI contracts are documented here and in
`shared/references/field-plan.md`; don't open their source.

Inputs you need before running it: the note's current fields and tags, the word's base form
(the `English` value) and its plain POS word (noun/verb/…), used for the media filename slugs.

**Batch the writes — one write per note.** Compute every value first (steps B1–B4), then:

- For a note **already in Anki** (the populate skill, and the add skill's duplicate path), write
  *all* changed fields — backfilled text, `<img src=…>`, and `[sound:…]` references together, plus
  any English normalization from step E — in **one** `updateNoteFields` call at the end, never one
  call per field.
- For a **brand-new** note (the add skill's new-note path) there is no note to update: the values
  all land in the single final `addNote` call.
- Either way, run `storeMediaFile` for each Picture/audio file before that single write (media is
  independent of the note) and reference the stored files by filename.
- Skip every mutating call in dry-run mode and report the planned values instead.

## No-pictures mode

Either skill may be invoked with `--no-pictures`, which skips the Picture flow (the most expensive
part of a run) entirely — full behavior in `shared/references/picture-procedure.md`'s "No-pictures
mode". Routine-local reminder: pass `--no-pictures` to **both** the B1 and B5 `note_status.py`
calls so the empty Picture lands in `skipped_fields` (not backfill work, not an obstacle to
`complete`/`remove_refine_tag`); B2 runs none of the Picture procedure (even in dry-run) and B4 adds
no `~api::absent::picture`. Everything else proceeds unchanged.

## Step E — English normalization (run first; shared by both skills)

If the note's `English` lacks the applicable `a`/`an`/`to` prefix for its POS (per field-plan.md's
English row) and a prefix applies, prepend it — the new value goes into the same single write as
everything else. Consequences:

- If `English-audio-generated` is already **non-empty**, the old mp3 is now stale: regenerate it
  from the new `English` value (include it in the step B3 TTS batch; the deterministic slug means
  `storeMediaFile` overwrites the old file, and the field value itself is unchanged).
- If it is **empty**, nothing special — step B3 backfills it from the already-prefixed value.

Mention the article edit (and any audio regeneration) in the report row's Outcome; it does not
count as a backfilled/"Filled" field.

## Step B0 — Media filenames (one slug call per note)

Get every filename the note could need in a single call — never build slugs by eye (they are
deterministic so `storeMediaFile` overwrites old media on reprocessing instead of accumulating):

```
python3 "shared/scripts/slugify.py" "<English value>" <pos> --all-media
-> {"picture": "beggar-noun.jpg", "english": "beggar-noun-english.mp3",
    "definition": "beggar-noun-definition.mp3", "synonym1": "beggar-noun-synonym1.mp3",
    "antonym1": "beggar-noun-antonym1.mp3"}
```

## Step B1 — Ask `note_status.py` what is missing (one call)

Run the note's current fields + tags through `shared/scripts/note_status.py` (JSON in on stdin,
JSON out):

```
echo '{"fields": {…notesInfo fields…}, "tags": [ … ]}' | python3 "shared/scripts/note_status.py"
```

Use its output as the worklist — don't decide emptiness or completeness by eye:

- `empty_claude_fields` — the text fields to backfill in step B2 (already excludes fields covered by
  an `~api::absent::*` tag; those come back in `absent_ok_fields` and are skipped).
- `audio_to_generate` — audio fields whose source text is *currently* non-empty but audio empty.

Do **not** re-run it after B2 just to refresh the audio list — derive that deterministically in B3.
(If the add skill's duplicate path already ran `find_duplicate.py` in direct mode, its `status`
output *is* this call — reuse it instead of calling again.)

## Step B2 — Generate the empty text fields

For each field in `empty_claude_fields`, generate its value per `shared/references/field-plan.md`
(don't write yet — values go into the single final write). Field-specific notes:

- **Picture** — follow `shared/references/picture-procedure.md`: on success `storeMediaFile` under
  the `picture` slug (step B0) and set the field value to `<img src="filename">`; on no fitting
  image, leave empty and tag `~api::absent::picture` (step B4).
- **Examples1-generated** — generate up to 10 sentences and build the `<ul><li>…</li></ul>` list
  with `shared/scripts/build_example_html.py`, threading each call's `html` output back in as the
  next `existing` (start from `existing: null`, `source: null`).
- **Transcription / Definition / Russian / Synonym1 / Synonyms / Antonym1 / Antonyms** — generate
  directly per field-plan.md.

## Step B3 — Synthesize the audio in one batch

The definitive audio worklist is, deterministically (no extra `note_status.py` call):
`audio_to_generate` from step B1, **plus** the paired audio field of every text field just filled in
B2 or normalized in E (English / Definition / Synonym1 / Antonym1 → their `-audio-generated`
fields). The paired audio of a B2/E field is **always (re)generated, even when that audio field
already holds a `[sound:…]`**: its source text was just (re)written, so any existing audio is stale
(e.g. a note whose `Definition` was empty but still carried an old `Definition-audio-generated`). The
deterministic slug means `storeMediaFile` overwrites the old mp3 in place and the `[sound:<filename>]`
value is unchanged.

Synthesize all of them in **one** call following field-plan.md's Audio procedure —
`shared/scripts/generate_tts.py --batch` with a JSON array of `{"text": "<HTML-stripped source
text>", "path": "<temp.mp3>"}` on stdin — then `storeMediaFile` each result under its slug from
step B0 and set each field value to `[sound:<filename>]` (in the single final write; delete the
temp files afterwards). In dry-run mode skip the `storeMediaFile` and the write.

## Step B4 — Tag genuine absences

If a backfilled **Synonym1 / Synonyms / Antonym1 / Antonyms / Picture** turns out to have no value
after all (no synonym exists, no clearly-fitting image, etc.), add its absence tag with one
`addTags` call instead of leaving it silently empty (skip the call in dry-run mode):

| Field    | Absence tag                |
|----------|----------------------------|
| Synonym1 | `~api::absent::synonym1`   |
| Synonyms | `~api::absent::synonyms`   |
| Antonym1 | `~api::absent::antonym1`   |
| Antonyms | `~api::absent::antonyms`   |
| Picture  | `~api::absent::picture`    |

## Step B5 — Final write + verdict

Perform the single `updateNoteFields` (or `addNote`) with every changed field from E/B2/B3.

When the completeness verdict is needed (the populate skill's `en::to-refine` decision, or a
report row), run `note_status.py` **once** on the final state — the fields as just written plus
the final tags (including any absence tags from B4) — and use its `complete` /
`remove_refine_tag` / `incomplete_reasons` output. Since the script is a pure function of the
passed-in state, in dry-run mode pass the *intended* final values instead.
