# Example-real-life field — format (shared)

The single home for the HTML format of the `Example-real-life` field of an `En-word-or-sentence`
note, reached through `field-plan.md`'s Example-real-life row. Unlike the other Claude-owned fields,
its content comes from the **user's input** (the real-life sentence and its source); only the two
Anki skills' add flow writes it — the populate skill leaves it untouched.

## Format

An HTML bullet list `<ul><li>sentence (Source)</li></ul>`:

- one `<li>` per sentence;
- the Source title-cased, in parentheses after the sentence;
- the word/phrase wrapped in `<b>` where it occurs in the sentence.

Format example (read only if the prose here is not enough):
`shared/assets/Example of field Example-real-life.html`.

## Building it

Don't assemble the HTML by hand — `shared/scripts/build_example_html.py` does the mechanics
(legacy plain-text wrapping, sentence dedupe, `<b>` bolding); JSON in on stdin, JSON out.

On a **duplicate** (add SKILL.md step 2.2), a new `<li>` is **appended** inside the existing `<ul>`
instead of replacing it — which is what passing the current field value as the script's `existing`
input does.
