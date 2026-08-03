## Field plan

This table is the **index** of the `En-word-or-sentence` note's fields: how each field's value is
derived and who creates it (the `Created by` column marks the Claude-owned, backfillable fields).
Where a field needs more than a one-line rule, its `Remarks` cell links to the reference file that
is that field's single home — read that file when the field is being generated, not before.

| Field                      | Value                                                                            | Created by      | Remarks                                                                                             |
|----------------------------|----------------------------------------------------------------------------------|-----------------|-----------------------------------------------------------------------------------------------------|
| English                    | base form of the word (see SKILL.md step 2.1.4)                                  | Claude          | Prefixed with `a`/`an`/`to` where applicable: `shared/references/english-article-prefix.md`         |
| Transcription              | American IPA                                                                     | Claude          | —                                                                                                   |
| Definition                 | short, simple dictionary-style definition                                        | Claude          | Wording rules: `shared/references/definition-rules.md`                                              |
| Definition-wq-generated    | empty                                                                            | —               | Obsolete, from "Word Query" addon, unused now                                                       |
| Picture                    | representative image of the word/sense, as `<img src="filename">`                | Claude          | Find/verify/store + `--no-pictures`: `shared/references/picture-procedure.md`                       |
| Russian                    | translation                                                                      | Claude          | —                                                                                                   |
| Example-my                 | empty                                                                            | —               | —                                                                                                   |
| Example-real-life          | HTML bullet list of the real-life sentences, each with its source                | User (input)    | Format + append-on-duplicate: `shared/references/example-real-life-format.md`                       |
| Synonym1                   | simplest synonym, picked from Synonyms                                           | Claude          | If no synonym exists, leave empty and tag the note `~api::absent::synonym1` (see SKILL.md step 2.4) |
| Synonyms                   | up to 10 synonyms (if any exist)                                                 | Claude          | If none exist, leave empty and tag the note `~api::absent::synonyms` (see SKILL.md step 2.4)        |
| Antonym1                   | simplest antonym, picked from Antonyms                                           | Claude          | If no antonym exists, leave empty and tag the note `~api::absent::antonym1` (see SKILL.md step 2.4) |
| Antonyms                   | up to 10 antonyms (if any exist)                                                 | Claude          | If none exist, leave empty and tag the note `~api::absent::antonyms` (see SKILL.md step 2.4)        |
| Tense                      | empty                                                                            | —               | —                                                                                                   |
| Comment                    | empty                                                                            | —               | —                                                                                                   |
| Examples1-generated        | HTML bullet list `<ul><li>sentence</li></ul>`, up to 10 sentences using the word | Claude          | Same list format as Example-real-life, but without a source (Claude-generated)                      |
| Examples2-generated        | empty                                                                            | —               | —                                                                                                   |
| Examples3-generated        | empty                                                                            | —               | —                                                                                                   |
| English-audio-generated    | mp3 of English field (verbatim, incl. its `a`/`an`/`to` prefix)                  | Claude (script) | Audio procedure: `shared/references/audio-procedure.md`                                             |
| Definition-audio-generated | mp3 of Definition field                                                          | Claude (script) | Per `shared/references/audio-procedure.md`                                                          |
| Synonym1-audio-generated   | mp3 of Synonym1 field (only if Synonym1 non-empty)                               | Claude (script) | Per `shared/references/audio-procedure.md`; skipped when Synonym1 is empty                          |
| Synonyms-audio-generated   | empty                                                                            | —               | —                                                                                                   |
| Antonym1-audio-generated   | mp3 of Antonym1 field (only if Antonym1 non-empty)                               | Claude (script) | Per `shared/references/audio-procedure.md`; skipped when Antonym1 is empty                          |
| Antonyms-audio-generated   | empty                                                                            | —               | —                                                                                                   |
| Synonyms-generated         | empty                                                                            | —               | Obsolete, from "Word Query" addon, unused                                                           |
