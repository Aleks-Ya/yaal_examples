# Claude skill "Ubuntu Disk Cleanup"

A Claude Code skill that uses `du` (with `sudo` where needed) to find the largest directories on an
Ubuntu/Debian system and identify which ones are safe to empty to reclaim disk space. It analyzes and
recommends only — nothing is deleted without explicit per-run approval.

## Skill

`.claude/skills/analyze-disk-usage/` — invoke with `/analyze-disk-usage` when a disk/partition is
full or you want to know where space went and what can be cleaned.

- `SKILL.md` — the workflow: `df` to find the full filesystem, `du` drill-down, cross-reference known
  cleanable locations, report a size-ranked table, then clean only what the user approves.
- `scripts/du_top.sh [PATH] [DEPTH] [COUNT]` — largest directories under a path, biggest first;
  read-only, stays on one filesystem. Prefix with `sudo` for system paths.
- `scripts/scan_cleanable.sh` — sizes of curated safe-to-clean locations, largest first; run under
  `sudo` to read system/other-user paths (resolves the human user's home via `$SUDO_USER`).
- `references/cleanable-locations.md` — catalog of cleanable Ubuntu locations with the correct,
  least-destructive reclaim command for each.
