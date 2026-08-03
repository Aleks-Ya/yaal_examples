# English field — article/`to` prefix (shared)

The single home for the `a`/`an`/`to` prefix carried by the `English` field of an
`En-word-or-sentence` note. Both skills reach it through `field-plan.md`'s English row and the
backfill routine (step E). The `English` value itself is the base form of the word (add SKILL.md
step 2.1.4); this file only governs its prefix.

## The rule

Prefix the base form with:

- `a`/`an` — singular countable nouns, e.g. "a bucket";
- `to` — verbs, including multi-word verbs like "to bat around", e.g. "to conquer".

The prefix exists to dodge Anki's exact-duplicate warning (and it doubles as a POS signal for TTS —
see `shared/references/audio-procedure.md`).

**When applicable only:**

- no prefix for other parts of speech, for uncountable/plural/proper nouns, or when the value is a
  full sentence;
- skip it if the value already starts with `a `/`an `/`to ` — **never double-prefix**.

## When it is applied

Both when **creating** a note and when **normalizing an existing** note on review. The canonical
retrofit procedure — including regenerating a now-stale `English-audio-generated` — is
`shared/references/backfill-routine.md` step E.

The prefix is **ignored when matching duplicates** (add SKILL.md step 2.2).
