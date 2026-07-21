# English Part of Speech (POS) Anki tags

- en::parts::adjective
- en::parts::adjective::compound
- en::parts::adjective::irregular
- en::parts::adverb
- en::parts::conjunction
- en::parts::determiner
- en::parts::exclamation
- en::parts::noun
- en::parts::noun::countable
- en::parts::noun::irregular
- en::parts::noun::proper
- en::parts::noun::uncountable
- en::parts::number
- en::parts::predeterminer
- en::parts::preposition
- en::parts::pronoun
- en::parts::verb
- en::parts::verb::auxiliary
- en::parts::verb::irregular
- en::parts::verb::modal
- en::parts::verb::phrasal

## Choosing the right tag

Pick the **most specific** tag that applies. A sub-tag (`en::parts::X::Y`) is more specific than
its bare parent (`en::parts::X`) and implies the parent through the `::` hierarchy, so a note that
carries an applicable sub-tag should **not** also carry the bare parent tag — the sub-tag replaces
it.

- **Apply every specific sub-tag that fits**, not just one — the sub-tags of a family are not
  mutually exclusive. A noun can be both `en::parts::noun::countable` and
  `en::parts::noun::irregular` (e.g. "child"); a verb can be both `en::parts::verb::irregular` and
  `en::parts::verb::phrasal`. In that case add all of them and drop the bare parent.
- **Nouns** almost always take a classifying sub-tag: `::proper` for proper nouns, otherwise
  `::countable` or `::uncountable` (plus `::irregular` for an irregular plural). A bare
  `en::parts::noun` is therefore rarely the right final tag.
- **Verbs** take a sub-tag only in the special cases named: `::auxiliary` (be/have/do as an
  auxiliary), `::modal` (can/must/should/…), `::phrasal` (e.g. "bat around", "give up"), and/or
  `::irregular` (irregular past forms). A plain regular verb (e.g. "to conquer") has **no**
  applicable sub-tag and correctly keeps the bare `en::parts::verb`.
- **Adjectives** take `::compound` (e.g. "well-known") and/or `::irregular` (irregular comparison,
  e.g. "good/better/best"); a plain adjective keeps the bare `en::parts::adjective`.
- The remaining families (adverb, conjunction, determiner, exclamation, number, predeterminer,
  preposition, pronoun) have no sub-tags — their bare tag is always the final tag.

## Reconciling an existing note's tag

When a note already exists (e.g. the `populate-existing-english-anki-notes` skill, or the
`add-english-word-to-anki` duplicate path), first determine the word's most-specific applicable
`en::parts::*` tag(s) per the rules above, then bring the note's tags into line — four cases:

- **No `en::parts::*` tag at all** → `addTags` the most specific applicable tag(s).
- **A bare parent tag (e.g. `en::parts::noun`) while a specific sub-tag applies** (e.g. the word is a
  countable, irregular-plural noun) → **first** `removeTags` the now-redundant bare parent, **then**
  `addTags` every applicable sub-tag (e.g. `en::parts::noun::countable`, `en::parts::noun::irregular`)
  — the sub-tag replaces it. The order matters: Anki's `removeTags` is hierarchical — removing
  `en::parts::noun` also strips every `en::parts::noun::*` sub-tag the note carries — so removing
  the parent *after* adding the sub-tag silently deletes the sub-tag too. For the same reason, if
  the note already carried sub-tags of that parent that should be kept, include them in the
  `addTags` call — the parent removal wipes them as well.
- **A bare parent tag while no sub-tag applies** (e.g. a plain regular verb keeps `en::parts::verb`, a
  plain adjective keeps `en::parts::adjective`, an adverb, etc.) → leave it as-is; the bare tag is
  already the correct final classification.
- **Already carrying the correct specific tag(s)** → leave as-is.

(In dry-run mode, skip these tag mutations but report the intended change.)
