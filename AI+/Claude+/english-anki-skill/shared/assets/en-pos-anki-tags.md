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
