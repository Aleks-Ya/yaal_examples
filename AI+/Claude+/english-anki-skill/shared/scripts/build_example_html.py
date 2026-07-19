#!/usr/bin/env python3
"""Build/update a `<ul><li>...</li></ul>` example-sentence field from one new sentence.

Reads a JSON object from stdin:
    {
      "existing": "<ul>...</ul>" | "legacy plain text" | null,
      "word": "beggars",        // the exact marked substring as it occurs in `sentence`
                                 // (the surface form from parse_input.py's output, NOT the
                                 // dictionary base form used for the English field)
      "sentence": "They're eating you alive, the beggars.",
      "source": "The Guard 2011" | null   // omit/null for a source-less field (e.g.
                                           // Examples1-generated style)
    }

Behavior:
- `word`'s occurrence in `sentence` is wrapped in <b>...</b>.
- If `existing` already contains this exact sentence (checked against its HTML-stripped text),
  nothing changes.
- If `existing` is null/empty, a fresh single-item list is produced.
- If `existing` is a legacy plain-text field (no `<ul>`), it's wrapped as the first `<li>` before
  the new one is appended.
- If `existing` is already a `<ul><li>...</li></ul>` list, the new `<li>` is appended before
  `</ul>`.

Prints a JSON object to stdout: {"html": ..., "changed": bool, "already_present": bool}.
"""
import json
import re
import sys

TAG_RE = re.compile(r"<[^>]+>")


def strip_tags(html):
    return TAG_RE.sub("", html)


def build(existing, word, sentence, source):
    if existing and sentence in strip_tags(existing):
        return {"html": existing, "changed": False, "already_present": True}

    if word in sentence:
        bolded = sentence.replace(word, f"<b>{word}</b>", 1)
    else:
        print(f"warning: word {word!r} not found in sentence {sentence!r}; leaving unbolded", file=sys.stderr)
        bolded = sentence

    li_text = bolded + (f" ({source})" if source else "")

    if not existing:
        html = f"<ul><li>{li_text}</li></ul>"
    elif "<ul>" in existing:
        idx = existing.rfind("</ul>")
        html = existing[:idx] + f"<li>{li_text}</li>" + existing[idx:]
    else:
        html = f"<ul><li>{existing}</li><li>{li_text}</li></ul>"

    return {"html": html, "changed": True, "already_present": False}


def main():
    data = json.load(sys.stdin)
    result = build(
        data.get("existing"),
        data["word"],
        data["sentence"],
        data.get("source"),
    )
    print(json.dumps(result, ensure_ascii=False))


if __name__ == "__main__":
    main()
