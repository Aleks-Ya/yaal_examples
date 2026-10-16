# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working in this repository.

## What this is

This is the guide to a personal movie library on an external drive
(`/media/aleks/ADATA/movies`), plus a subtitle text-extraction workflow used for
language learning. Most work here means managing files and media on that drive,
and processing subtitle text.

The only code is `sync-phone`, a small Python tool (uv project: `pyproject.toml`,
`src/phone_sync/`, `tests/`) that copies the library to the phone. See
[Syncing to the phone](#syncing-to-the-phone).

This folder is part of the `yaal_examples` git repo and holds only `CLAUDE.md`
and the tool. The library itself is not here; it stays on the drive. Media files
must never be committed.

The owner is a **native Russian speaker learning English as a second language**.
Subtitle text produced here is for English reading practice, so it should be
written for an English learner.

## Safety rules

- Video files are large (1–11 GB `.mp4`/`.mkv`/`.avi`). Never copy, re-encode, or
  delete a video, and never move one to another drive, unless explicitly told to.
  Renaming or moving within the drive (`mv -n`, which is instant and never
  overwrites) is fine once the user has approved the plan.
- When renaming, rename **only the title folders**. Leave the video and `.srt`
  files inside with their original release names (e.g.
  `Ditja.Chelovecheskoe.2006.BDRip...mkv` stays as is), so torrent seeding still
  works. The only exception is series episodes (see [Series](#series)).
- Show the list of renames and get confirmation before doing bulk renames.
- Hidden `.ts/` folders (holding `tsm.json` and cached thumbnails) belong to the
  **TagSpaces** app and are not library content. Ignore them when listing or
  processing files. They move together with their title folder. Renaming files
  inside a folder can lose that file's TagSpaces tags.

## Layout

The library root is `/media/aleks/ADATA/movies/`. Relative `Movies/…` and
`download/` paths in this file are relative to that root.

```
/media/aleks/ADATA/movies/
  Movies/
    English/   all titles whose original language is NOT Russian
    Russian/   titles originally made in Russian
  download/    staging area for new titles before they are sorted into Movies/
```

- The folder is chosen by the film's **original production language**, not by
  which audio tracks or subtitles it has. English, French, Italian, Danish,
  Japanese, etc. originals all go in `English/`, even if they only have a Russian
  dub.
- `Movies/` has no other subfolders. Series live next to movies.
- Each title gets one folder. It holds the video plus sidecar files: `.srt`
  subtitles, and sometimes a `.torrent`, a poster `.jpg`, an `.nfo`, `.txt`
  study files, or a `.no-phone` marker (see
  [Syncing to the phone](#syncing-to-the-phone)).
- Files sit directly in the title folder, with no nested release subfolder (e.g.
  `Title/Release.Name/Release.Name.mkv`). If a download comes with one, move its
  files up into the title folder and remove the empty subfolder. The only
  subfolders allowed are a series' `Season NN/` folders.

### Phone copy

The owner's Samsung phone holds some of the library, in the same layout and with
the same folder names as the drive. When it is connected over MTP, it is mounted at:

```
/run/user/1000/gvfs/mtp:host=SAMSUNG_SAMSUNG_Android_R5GL24N2ZCJ/Internal storage/Movies/
  English/
  Russian/
  .thumbnails/   Android's thumbnail cache; ignore it (Android recreates it)
```

- Keep phone folder names in sync with the drive. When a folder is renamed on the
  drive, rename its phone copy too.
- Use `gio`, not `mv`, on the MTP mount:
  - Rename: `gio rename <path> <new-name>`
  - Move: `gio move --no-copy-fallback <src> <dest-dir>/`. Without the flag, a
    failed move can become a slow copy-and-delete.
- MTP listings can go stale, for example showing deleted files as 0-byte
  entries dated 1970. Unplugging and reconnecting the phone fixes this.

### Syncing to the phone

`uv run sync-phone` copies titles from the drive's `Movies/` to the phone's
`Movies/`. Run `--dry-run` first to see the plan without changing anything.
The drive folder defaults to `/media/aleks/ADATA/movies/Movies`; override it
with `--drive`.

- **What is copied:** every title is included by default. A `.no-phone` file in a
  title folder controls exclusions:
  - an empty marker keeps the whole title off the phone;
  - a marker with lines treats each line as a glob pattern and excludes only the
    matching files, e.g. `Season 0[3-6]/*` or `*.mkv`;
  - `.torrent`, `.nfo`, and `.ts/` are never copied.
- **When a file is copied:** only if it is missing on the phone or its size
  differs, so an interrupted copy is redone. Files are copied under a `.partial`
  name and renamed when complete.
- **Renamed folders:** a folder renamed or moved on the drive is followed with a
  rename on the phone; the video is not copied again. Likewise, a file moved
  within a title folder (same name and size, e.g. out of a release subfolder) is
  moved on the phone, and the folders it leaves empty are removed.
- **Before copying:** it checks that the phone is connected with file transfer
  enabled and that its storage is writable. It then shows the titles to copy, the
  skipped titles, the extras on the phone, and free space before and after, and
  asks `[y/N]`. It refuses to run if free space would drop below `--reserve`
  (default 10 GB).
- **Deleting:** nothing is deleted from the phone without `--prune`, which asks
  separately before deleting.
- **Development:** run the tests with `uv run pytest`. Tests use a fake `gio` on
  temp folders and never touch the phone.

### Playback on the phone

Some H.264 files freeze on the phone: the picture stops while the audio keeps
playing. This happens in both MX Player and VLC. The cause is extreme x264
settings, 16 reference frames and 14–16 B-frames, which the phone's hardware
decoder can't handle. The files themselves are fine.

- **Fix:** switch the player to software decoding (MX Player: **SW**; VLC:
  Settings → Video → Hardware acceleration → Disabled). This fixed American
  Psycho 2000 and Ex Machina 2015. Don't re-encode unless software decoding fails.
- **Check a file:** read `max_num_ref_frames` from the stream header:
  ```
  ffmpeg -i <file> -map 0:v:0 -c copy -bsf:v trace_headers -frames:v 1 -f null - 2>&1 | grep max_num_ref_frames
  ```
- **Known affected files** (16 reference frames): American Psycho 2000,
  Ex Machina 2015, Fargo 1996, Мастер и Маргарита 2023, Трудно быть богом 2026.
  Files with 10–14 reference frames haven't been tested; files with 8 or fewer
  are probably fine.

## Naming conventions

### Folder name

`Title Year [tags]`, with space-separated tags inside a single `[...]`.

**Title:**

- If the folder has any Russian audio or subtitles, use the bilingual form
  `Primary · Secondary`, joined by a middle dot (`·`, U+00B7):
  - **Russian originals**: Russian title first. Example:
    `Морфий · Morphine 2008 [ru en 1080p]`.
  - **English originals**: English title first. Example:
    `Alien - Romulus · Чужой - Ромул 2024 [en ru 2160p]`.
  - **Originals in other languages**: the film's **English** title first, not the
    original-language title. Examples: `Another Round · Ещё по одной 2020`
    (not *Druk*), `Hidden · Скрытое 2005` (not *Caché*).
  - This applies even when the film only has a Russian dub. Example:
    `The Big Lebowski · Большой Лебовски 1998 [ru 1080p goblin]`.
- If the folder has no Russian audio or subtitles, use the English title only.
  Example: `The Matrix 1999 [en 1080p subtitles]`.
- If a Russian original has no English audio or subtitles, use the Russian title
  only. Example: `Мастер и Маргарита 2023 [ru 480p]`.
- Edition words go before the year. Examples: `Leon The Professional Extended 1994`,
  `… Unrated 1996`.
- Never use `:` `"` `*` `<` `>` `?` `\` `|` in a name. The phone (Android)
  accepts a folder with such a name but can't store files inside it. Write
  ` -` instead of a colon, e.g. *Alien: Romulus* → `Alien - Romulus`.
  `sync-phone` reports any such name instead of copying it.

**Tags, in this order:**

1. **Languages** of available audio and subtitles: `en`, `ru`, `fr`, `it`, `hi`, ….
   - The original language comes first, then `ru`, then `en`, then any others.
     Examples: `[en ru …]`, `[ru en …]`, `[fr ru en …]`, `[en hi …]`.
   - Built-in subtitle tracks count as well as `.srt` files.
2. **Resolution**, from the frame width:
   - 3840 → `2160p`
   - about 1920 → `1080p`
   - about 1280 → `720p`
   - anything smaller → `480p`
3. **`subtitles`**: a separate `.srt` is in the folder.
4. **`goblin`**: a Russian translation by "Goblin" (Дмитрий Пучков), including
   tracks voiced by someone else using his translation.
5. **`series`**: a multi-episode title.

Check resolution and languages with `ffprobe`. AVI files carry no language tags,
so for those, work out the languages from the release or torrent name and the
number of audio tracks, and tell the user it was a guess.

### Series

- Series use the same folder naming as movies. Example:
  `Трудно быть богом 2026 [ru 480p series]`.
- If the source is one merged video per season, leave it as one file directly in
  the title folder. Example: `Мастер и Маргарита 2005 [ru 720p series]`.
- If there are separate episode files:
  - Put them in `Season 01/`, `Season 02/`, … subfolders (two-digit, zero-padded).
  - Rename them `Title SxxExx.ext`, using the Primary title. Example:
    `Season 01/The Sopranos S01E01.avi`.
  - Give matching `.srt` files the same base name as their episode.
  - Keep any `.torrent` at the top of the title folder.

## Subtitle text extraction

Some English titles have a plain-text version of their `.srt` for reading
practice. It is named `<srt-basename> clean.txt` and sits next to the `.srt`.
Examples are in `Movies/English/Sicario 2015 [en 720p subtitles]/` and
`Movies/English/Leon The Professional Extended 1994 [en 1080p subtitles]/`.

Format, as seen in the existing files:

- Cue numbers, timestamps, and blank lines are removed.
- There is one spoken line per text line. Dialogue cues (`- A … - B …`) are split
  into separate lines with the leading `-` dropped.
- Encoding is UTF-8 with no BOM and LF line endings, even when the source `.srt`
  has a BOM and CRLF line endings.

To process a new title, use the same naming and format, based on the source
`.srt` file name. Some `.srt` files are not UTF-8 (e.g. cp1251), so check
the encoding first.
