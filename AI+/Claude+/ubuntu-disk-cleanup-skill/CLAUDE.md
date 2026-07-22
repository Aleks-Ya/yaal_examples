# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A single Claude Skill, `analyze-disk-usage`, that uses `du` (with `sudo` where needed) to find the
largest directories on an Ubuntu/Debian system and report which are safe to empty to reclaim disk
space. See `README.md` for the user-facing summary and `.claude/skills/analyze-disk-usage/SKILL.md`
for the full step-by-step process.

## Related: the user's regular maintenance script

The "Context" section of `SKILL.md` notes that the user regularly runs
`Bash+/apps/upgrade_linux.sh` (elsewhere in this repo), which already does some cleanup as a side
effect — see there for the specifics. If that script's cleanup commands change, update that section.

## Core invariant: analyze, never auto-delete

The whole design rests on a read/destructive split — preserve it in any change:

- `df`, `du`, and both helper scripts are **read-only** and may run freely (including under `sudo`).
- No destructive command (`rm`, `apt clean`, `journalctl --vacuum-*`, `docker … prune`,
  `snap remove`, emptying Trash…) runs until the user explicitly approves *that specific* cleanup for
  *that run*. Approving one cleanup never implies the others.
- Prefer the purpose-built reclaim command over hand-rolled `rm -rf`; the canonical, least-destructive
  command for each location lives in `references/cleanable-locations.md` and is the single source of
  truth. When adding a cleanable location, update that reference **and** the `paths` array in
  `scripts/scan_cleanable.sh` together — the skill classifies any large dir *not* in the reference as
  user data.
- **Never run a `sudo` command yourself** — not even the read-only scans. `sudo`'s cached credential
  is per-terminal (`tty_tickets`), so a password the user enters in an interactive `! <command>`
  prompt does **not** carry into Claude's shell; `sudo -n` there just fails. When a step needs
  `sudo`, hand the user the exact command and ask them to run it in shell mode (a `! <command>`
  prompt line), then read the output they paste back. Never pipe a password (`sudo -S`) or set up
  passwordless sudo to work around this.

## Layout

Everything is under `.claude/skills/analyze-disk-usage/`:

- `SKILL.md` — the workflow (df → du drill-down → scan known locations → size-ranked report → clean
  only what's approved) plus the safety rules. Scripts and reference are addressed by
  skill-relative path (`scripts/…`, `references/…`).
- `scripts/du_top.sh [PATH] [DEPTH] [COUNT]` — largest dirs under a path, biggest first. Uses
  `du -x` (stays on one filesystem, won't descend into other mounts) and suppresses permission
  errors. Prefix with `sudo` for system paths.
- `scripts/scan_cleanable.sh` — `du`-measures the curated cleanable locations, largest first. Run
  under `sudo` so system/other-user paths are readable; it deliberately resolves the **human** user's
  home via `$SUDO_USER` (not root's) so it reports the invoking user's caches. Sorts on exact bytes
  (`du -sbx`) then renders human sizes via `numfmt`.
- `references/cleanable-locations.md` — catalog of cleanable Ubuntu locations, each tagged safe /
  caution / user-data with its reclaim command; the report's classification comes from here.

## Working on the scripts

Bash targeting GNU coreutils on Ubuntu (relies on `du -x`/`--max-depth`, GNU `sort -rh`, `numfmt`,
`getent`). There's no build or test harness; validate changes directly:

```
bash -n scripts/du_top.sh scripts/scan_cleanable.sh   # syntax check
./scripts/du_top.sh ~ 1 5                              # smoke test (no sudo needed for own home)
./scripts/scan_cleanable.sh                            # user paths only without sudo
```
