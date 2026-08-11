# Picture procedure (shared)

The single home for how the **Picture** field of an `En-word-or-sentence` note is filled, and how
the `--no-pictures` flag changes that flow. Both skills reach it through the backfill routine (step
B2) and `field-plan.md`'s Picture row.

The field holds up to **two images**, in this order:

1. a **photo** found on Openverse — the primary image, always attempted first;
2. an **icon** found on Iconify — a symbol of the word, which exists for many simple and abstract
   words that no photo archive can illustrate.

Either may be missing, so the field value is the photo's `<img>`, the icon's `<img>`, or both
concatenated:

```
<img src="ladder-noun.jpg"><img src="ladder-noun-icon.svg">
```

The helper scripts below are **run, never read** — their CLI contracts are documented here and in
`shared/references/field-plan.md`; don't open their source.

## Finding and storing the photo

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
   re-processing overwrites rather than accumulates). Delete the temp files afterwards. The field
   value starts with this `<img src="filename">`.

If no viewed candidate clearly fits (after the one query refinement), store no photo — don't store a
poor match just to fill the field — and go on to the icon, which may still succeed.

## Finding and storing the icon

The icon is looked up **for every note**, whether or not a photo was found. It comes from the keyless
Iconify API, and the pick is made **by name, without viewing the image** — `search_icons.py` only
returns candidates whose icon name *is* the query (or a style variant of it like `ladder-fill`), and
those reliably depict the word, so no visual verification step is needed here:

1. **Search the bare headword** — run `python3 "shared/scripts/search_icons.py" "<word>"`, where
   `<word>` is the `English` value **without** its `a`/`an`/`to` prefix. Unlike the photo query, do
   *not* describe a scene: icon names are single concepts, so a phrase matches nothing.
2. **Retry once with a synonym** if the result is `[]` — a single-word synonym of the intended
   sense (`Synonym1` is usually the right one). Stop after this second attempt.
3. **Pick the first candidate.** The list is already ranked (exact names before style variants,
   tidy monotone collections first), so take `[0]`'s `id` unless its sense is plainly wrong for this
   note (e.g. the `bat` animal when the note means the baseball bat) — in that case scan the rest of
   the list for a better `id`, and if none fits, store no icon.
4. **Fetch and store it** — `python3 "shared/scripts/search_icons.py" --fetch <id> <temp_path.svg>`
   writes the icon recoloured and wrapped in a white rounded card (so it stays legible in Anki's
   night mode and on AnkiDroid) at a 256px display size. Store it with `storeMediaFile` (`path` =
   that temp file, `filename` = the `icon` entry from the one `slugify.py … --all-media` call — see
   backfill-routine.md step B0 — e.g. `ladder-noun-icon.svg`). Delete the temp file afterwards, and
   **append** `<img src="filename">` to the field value after the photo's `<img>`.

**Absence fallback:** only when **neither** a photo nor an icon was found, leave the field empty and
tag the note `~api::absent::picture` (backfill-routine.md step B4 / SKILL.md step 2.4). One image
alone is a filled field — no absence tag.

**Already-filled fields are left alone.** The whole procedure runs only when `note_status.py` reports
`Picture` as empty. A note that already holds a photo from an earlier run counts as filled and gets
no icon added — the two images are deliberately *not* tracked separately. To add an icon to such a
note, clear its Picture field in Anki and re-run the populate skill.

## No-pictures mode

Either skill may be invoked with `--no-pictures` (any position, combinable with `--dry-run`). The
Picture flow above — image search, thumbnail batch download, visual check, full-res fetch, and the
icon lookup — is by far the most expensive part of a run, and this mode skips it entirely (icon
included: it fills the same field):

- Pass `--no-pictures` to every `note_status.py` call (the backfill routine's B1 worklist and B5
  verdict): an empty, un-absence-tagged Picture then lands in `skipped_fields` instead of
  `empty_claude_fields`, so it is neither backfill work nor an obstacle to
  `complete`/`remove_refine_tag` — the note finishes (and drops `en::to-refine`) on the strength of
  its other fields, with the Picture deliberately left unfilled. (The add skill's duplicate path
  reuses `find_duplicate.py`'s embedded `status`, which is computed *without* the flag — ignore a
  `Picture` entry in its `empty_claude_fields`, but do run `note_status.py --no-pictures` for the B5
  verdict.)
- Run none of the Picture procedure in B2 (no `search_images.py`, no `fetch_and_resize_image.py`, no
  `search_icons.py`, no `storeMediaFile` for the picture or icon). This holds in dry-run mode too,
  where the picture lookups would otherwise still run.
- Do **not** add `~api::absent::picture` in B4 for the skipped field — absence was never verified;
  the field simply stays empty and untagged.
- Everything else — text fields, audio, the other absence tags — proceeds unchanged.
