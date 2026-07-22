# Picture procedure (shared)

The single home for how the **Picture** field of an `En-word-or-sentence` note is filled, and how
the `--no-pictures` flag changes that flow. Both skills reach it through the backfill routine (step
B2) and `field-plan.md`'s Picture row. The field value, when filled, is `<img src="filename">`.

The helper scripts below are **run, never read** — their CLI contracts are documented here and in
`shared/references/field-plan.md`; don't open their source.

## Finding and storing the picture

The image must clearly depict the word *in this sentence's sense/POS*. Procedure:

1. **Build a concrete, depictable query** for the word in its intended sense — use the `Definition`
   and the example sentence to disambiguate, and for abstract verbs/adjectives/nouns describe a
   concrete scene that represents the sense rather than the bare headword (e.g. for "reluctant" →
   "person hesitating with worried face", not "reluctant").
2. **Search candidates:** run `python3 "shared/scripts/search_images.py" "<query>" --limit 8` to get
   ranked candidates (each with `url`, `thumbnail`, `title`, `tags`); **pre-rank by `title`/`tags`**
   and take the **top ~3** matching the sense.
3. **Verify on thumbnails, in one batch:** download all ~3 candidates' **`thumbnail`** URLs (not
   `url`) in a **single** `shared/scripts/fetch_and_resize_image.py --batch` call — pipe a JSON
   array of `{"url": "<thumbnail>", "path": "<temp_output_path>", "max_dimension": 320}` on stdin
   (each shrunk to fit within **320px** — plenty to judge relevance, and far cheaper to view than a
   larger image; a thumbnail already within 320px is fine to judge on), then **view all the temp
   files with the Read tool in a single message** (parallel Read calls) and judge them together:
   pick whichever clearly and unambiguously depicts the word in the intended sense — reject
   off-sense, text/diagram-heavy, watermarked/logo, or confusing images. If none of the ~3 fit,
   refine the query **once** and retry the batch.
4. **Store the winner:** for the **chosen winner only**, download+shrink its full-resolution `url`
   to 600px with the single-URL form `shared/scripts/fetch_and_resize_image.py <url>
   <temp_output_path> 600` (the thumbnail was only for judging; the full-res fetch is for stored
   quality), then store that resized file into Anki's media collection with `storeMediaFile`
   (`path` = the resized full-res temp file, `filename` = the `picture` entry from the one
   `python3 "shared/scripts/slugify.py" "<English value>" <pos> --all-media` call — see
   backfill-routine.md step B0 — e.g. `beggar-noun.jpg`; deterministic word+POS slug, so
   re-processing overwrites rather than accumulates). Delete the temp files afterwards. Set the
   field value to `<img src="filename">`.

**Absence fallback:** if no viewed candidate clearly fits (after the one query refinement), leave
the field empty and tag the note `~api::absent::picture` (backfill-routine.md step B4 / SKILL.md
step 2.4) — don't store a poor match just to fill the field.

## No-pictures mode

Either skill may be invoked with `--no-pictures` (any position, combinable with `--dry-run`). The
Picture flow above — image search, thumbnail batch download, visual check, full-res fetch — is by
far the most expensive part of a run, and this mode skips it entirely:

- Pass `--no-pictures` to every `note_status.py` call (the backfill routine's B1 worklist and B5
  verdict): an empty, un-absence-tagged Picture then lands in `skipped_fields` instead of
  `empty_claude_fields`, so it is neither backfill work nor an obstacle to
  `complete`/`remove_refine_tag` — the note finishes (and drops `en::to-refine`) on the strength of
  its other fields, with the Picture deliberately left unfilled. (The add skill's duplicate path
  reuses `find_duplicate.py`'s embedded `status`, which is computed *without* the flag — ignore a
  `Picture` entry in its `empty_claude_fields`, but do run `note_status.py --no-pictures` for the B5
  verdict.)
- Run none of the Picture procedure in B2 (no `search_images.py`, no `fetch_and_resize_image.py`, no
  `storeMediaFile` for the picture). This holds in dry-run mode too, where the picture lookups would
  otherwise still run.
- Do **not** add `~api::absent::picture` in B4 for the skipped field — absence was never verified;
  the field simply stays empty and untagged.
- Everything else — text fields, audio, the other absence tags — proceeds unchanged.
