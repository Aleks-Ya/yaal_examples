---
name: clean-movie-subtitles
description: Clean a movie-subtitle file (SRT or plain text) into readable prose saved to a ` clean` file. Use when the user hands you a subtitle/transcript file and wants the timestamps stripped, sentences on separate lines, punctuation fixed, censored words restored, and leading dialogue dashes removed.
---

# Claude skill: Clean movie subtitles

Turn a raw movie-subtitle file into clean, readable text. Given a path to an `.srt` or plain-text
subtitle file, produce a cleaned copy saved beside it with a ` clean` suffix
(`/tmp/Backrooms 2026.txt` -> `/tmp/Backrooms 2026 clean.txt`). The cleanup:

1. Removes time marks (SRT cue numbers + `-->` timestamp lines; stray timestamps in plain text).
2. Puts each sentence on its own line.
3. Corrects punctuation.
4. Restores censored coarse words (`f*ck` -> `fuck`, `sh*t` -> `shit`, ...).
5. Removes leading dialogue dashes (`- You okay?` -> `You okay?`).
6. Removes empty lines.

This skill is **standalone** — it does not touch Anki and shares none of the Anki skills' references
or note logic. It lives in this project only because its cleaned sentences are a natural input to
`add-english-word-to-anki`.

## Input
A single path to a subtitle file, given as the skill argument — SRT (`.srt`) or plain text (which may
or may not contain timestamps), e.g. `/clean-movie-subtitles '/tmp/Backrooms 2026.txt'`.

## Mode
Optional `--dry-run` (in either order with the path): do everything **except** the final write —
report the derived output path and a short preview instead of creating the file, e.g.
`/clean-movie-subtitles --dry-run '/tmp/Backrooms 2026.txt'`.

## Helper script
Delegate the deterministic stripping to the script — **run it; never read its source** (its contract
is documented here):

- `.claude/skills/clean-movie-subtitles/scripts/strip_subtitles.py` — run
  `python3 ".claude/skills/clean-movie-subtitles/scripts/strip_subtitles.py" "<input path>"`.
  Removes SRT cue indices + `-->` timestamp lines, standalone/leading timestamps in plain text,
  inline formatting tags (`<i>`, `<b>`, `<font …>`, `{…}`), and empty lines. It **keeps** sound cues
  (`[music]`) and speaker labels (`JOHN:`) — you remove the speaker labels below. Prints JSON
  `{output_path, format, text, input_lines, output_lines}` to stdout (exit 0); on an unreadable path
  it prints `{"error": …}` to stderr and exits 2. It writes no file — you write the final output.

## Steps
1. Resolve the input path and whether `--dry-run` is present from the arguments.
2. Run `python3 ".claude/skills/clean-movie-subtitles/scripts/strip_subtitles.py" "<input path>"`.
   If it exits non-zero, stop and show the user the reported error. Otherwise take `text` (the
   stripped dialogue), `output_path`,
   `format`, and the line counts from its JSON.
3. Produce the cleaned text from `text` — this is your linguistic pass, not the script's:
   - **One sentence per line.** Join subtitle wrapping (a sentence split across cue lines, trailing
     `-` hyphenation) and split at sentence boundaries so each line holds exactly one sentence.
   - **Correct punctuation:** capitalization, missing terminal marks, spacing, and stray dashes left
     over from wrapping.
   - **Restore censored coarse words:** `f*ck`/`f**k`/`f---` -> `fuck`, `sh*t` -> `shit`,
     `a*s`/`a$$` -> `ass`, etc. Restore the obvious intended word; don't invent new profanity.
   - **Remove leading dialogue dashes** (`- `, `– `, `— ` at the start of a line) that mark a speaker
     turn — e.g. `- You okay, Macer?` -> `You okay, Macer?` — but first use the dash to recognize that
     the line begins a new speaker turn / new sentence, so it is **not** joined onto the previous line
     during reflow.
   - **Remove speaker labels** (`JOHN:`, `NARRATOR (V.O.):`, a leading `- MARY:` — the dash goes too,
     per the bullet above) but **keep sound/scene cues** (`[music]`, `(GUNSHOT)`) on their own lines.
   - Otherwise **do not add, drop, translate, or summarize** content — this is repair/reformat only.
   - No empty lines in the output.
   - For a long file, process `text` in order in chunks and concatenate — do not skim or abridge.
     Then sanity-check the result isn't drastically shorter than the input (guard against dropped
     content); if it is, redo the missing part.
4. Write the cleaned text to `output_path` with the Write tool. **Skip this write in `--dry-run`.**
5. Report a short summary:
   - Input path, detected `format`, and (unless dry-run) the output path written.
   - Input -> output line counts, and roughly how many censored words were restored, leading dialogue
     dashes stripped, and speaker labels removed.
   - A 3–5 line preview of the cleaned result.
   - In `--dry-run`, state the output path that *would* be written and that nothing was saved.
