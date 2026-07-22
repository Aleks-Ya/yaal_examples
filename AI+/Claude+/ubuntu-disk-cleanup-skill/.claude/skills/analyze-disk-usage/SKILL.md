---
name: analyze-disk-usage
description: Analyze disk usage with du (using sudo where needed) to find the largest directories and which ones are safe to empty to free disk space on Ubuntu. Use when a disk/partition is full or the user asks where their space went / what they can delete.
---

# Claude skill: Analyze disk usage

Find where disk space is going and report which directories can be safely emptied to reclaim it, on
Ubuntu/Debian. The engine is `du` (with `sudo` for system-owned paths). This skill **analyzes and
recommends**; it never deletes anything without the user's explicit, per-run confirmation.

## Safety rules (read first)
- `du`, `df`, and the helper scripts are **read-only** — run them freely.
- **Never** run a destructive command (`rm`, `apt clean`, `journalctl --vacuum-*`, `docker … prune`,
  `snap remove`, emptying Trash, etc.) until you have shown the user the findings and they have
  explicitly approved that specific cleanup. Approving one cleanup does not approve the others.
- Prefer the purpose-built cleanup command over `rm -rf` when one exists (e.g. `sudo apt clean`
  rather than deleting `/var/cache/apt/archives` by hand) — see `references/cleanable-locations.md`.
- Deleting caches is safe (they get rebuilt) but slows the next run of that tool. Say so.
- **Never run a `sudo` command yourself** — not the scans, not the cleanups. `sudo`'s cached
  credential is per-terminal, so a password the user types at a `! <command>` prompt does not reach
  your shell and `sudo -n` just fails. For every `sudo` step below, hand the user the exact command,
  ask them to run it in shell mode (a `! <command>` prompt line), and read the output they paste
  back. Never pipe a password (`sudo -S`) or rely on passwordless sudo to get around this.

## When to use
The user says the disk is full, a partition is at 100%, an install/build failed for lack of space,
or asks "where did my space go" / "what can I delete to free space".

## Context: the user's regular maintenance script
The user regularly runs `Bash+/apps/upgrade_linux.sh` (in this same repo), which updates packages and
already performs some of these cleanups as a side effect: `sudo apt autoremove -y`,
`python -m pip cache purge` / `python3 -m pip cache purge`, `flatpak uninstall --unused -y`, and
`sudo truncate -s 0 /var/log/syslog /var/log/kern.log`. So the apt-orphan, pip-cache, unused-flatpak,
and syslog/kern.log offenders may already be small. It does **not** clean `~/.cache`, Trash,
`/var/lib/docker`, snap revisions, browser/IDE caches, or build caches (Gradle/Maven/npm), so those
are the more likely culprits. Re-running that script is itself a low-effort first step you can suggest.

## Steps

1. **See which filesystem is full.** Run `df -h -x tmpfs -x devtmpfs` and identify the mount point(s)
   that are (nearly) full and the device backing them. All later `du` work targets that mount point,
   not blindly `/`. If several partitions exist (e.g. separate `/home`), focus on the full one.

2. **Drill down from the top with `du`.** Use the helper (it passes `-x` so it stays on one
   filesystem and won't descend into other mounts):
   ```
   scripts/du_top.sh <mount-point>               # largest immediate children, biggest first
   ```
   Then drill into the biggest child, repeat, until you reach the specific directories responsible
   for the bulk of the usage:
   ```
   scripts/du_top.sh <that-big-subdir>
   ```
   Anything under `/var`, `/usr`, `/opt`, `/root`, or another user's home needs `sudo` to measure
   accurately — and per the safety rules you never run `sudo` yourself, so hand the user the exact
   `! sudo scripts/du_top.sh <path>` line and read back what they paste. A user's own home needs no
   `sudo`, so run those directly. Stop drilling once a directory is clearly a leaf offender or a
   known location from step 3.

3. **Scan known safe-to-empty locations.** In parallel with (or instead of) the manual drill-down,
   get the cleanable sizes. This needs `sudo` to read system and other-user paths, so ask the user
   to run it and paste the output (without `sudo` it silently reports 0 for those, e.g. missing a
   53G `/var/lib/docker`):
   ```
   ! sudo scripts/scan_cleanable.sh              # sizes of well-known cleanable dirs, biggest first
   ```
   This measures package/build/browser caches, logs, Trash, crash dumps, core dumps, snap/docker
   leftovers, etc. Cross-reference every large directory found in step 2 against
   `references/cleanable-locations.md` (read it now) to decide whether it is safe to empty and what
   the correct cleanup command is. Anything large that is **not** in that reference is real user data
   — surface it for the user to judge, never propose deleting it yourself.

4. **Report.** Print a single Markdown table sorted by size, then a one-line summary of total
   reclaimable space. Columns:
   - **Path** — the directory.
   - **Size** — human-readable (from `du`).
   - **Safe to empty?** — `yes` (cache/temp/trash/log, rebuildable), `caution` (rebuildable but has a
     cost or needs a flag, e.g. Docker data, old snap revisions), or `no / user data` (not in the
     reference — the user must decide).
   - **How to reclaim** — the specific command from `references/cleanable-locations.md`, or "review
     manually" for user data.

   Example:
   ```
   | Path                         | Size  | Safe to empty? | How to reclaim                        |
   |------------------------------|-------|----------------|---------------------------------------|
   | /var/cache/apt/archives      | 3.1G  | yes            | sudo apt clean                        |
   | ~/.cache                     | 2.4G  | yes            | rm -rf ~/.cache/*  (rebuilt on demand)|
   | /var/lib/docker              | 8.7G  | caution        | docker system prune -a (removes images)|
   | ~/Videos/recordings          | 12G   | no / user data | review manually                       |
   ```

5. **Offer to clean, then wait for approval.** Ask which of the `yes`/`caution` rows to reclaim.
   Only after the user names them, run those exact cleanup commands — but any command needing `sudo`
   you hand to the user as a ready-to-paste `! <command>` line for them to run (per the safety
   rules), while non-`sudo` cleanups in the user's own home you may run directly. Then re-check
   `df -h` on the affected mount (a `! sudo`-free `df -h` you can run yourself) and report the space
   actually freed.

## Helper scripts and reference
Run the scripts; don't read their source to answer questions — their contracts are documented above.
- `scripts/du_top.sh <path> [depth] [count]` — largest directories under `<path>` (default depth 1,
  top 25), staying on one filesystem (`du -x`), permission errors suppressed. Prefix with `sudo` for
  system paths.
- `scripts/scan_cleanable.sh` — sizes of the curated safe-to-clean locations, largest first; resolves
  the human user's home even under `sudo` (via `$SUDO_USER`). Run under `sudo` so system and
  other-user paths are readable.
- `references/cleanable-locations.md` — the catalog of known-cleanable Ubuntu locations with the
  correct, least-destructive reclaim command for each; read it at step 3.
