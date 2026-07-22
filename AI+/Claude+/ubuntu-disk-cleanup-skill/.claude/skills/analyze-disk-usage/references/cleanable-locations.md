# Cleanable locations on Ubuntu/Debian

Catalog of directories that are commonly safe to empty, with the **least-destructive** reclaim
command for each. "Safe" = the data is a cache/temp/log/trash that is regenerated or genuinely
disposable. "Caution" = reclaimable but there's a real cost (re-download, lost history, removes
things still in use). Never delete a large directory that is **not** listed here — treat it as user
data and let the user decide.

Always show findings and get explicit approval before running any command below.

## System locations (need `sudo`)

| Location | What it is | Reclaim command | Risk |
|---|---|---|---|
| `/var/cache/apt/archives` | Downloaded `.deb` package files | `sudo apt clean` (all) or `sudo apt autoclean` (only obsolete) | safe |
| (old kernels / orphaned deps) | Packages no longer needed | `sudo apt autoremove --purge` | safe |
| `/var/log` | System logs + systemd journal | Journal: `sudo journalctl --vacuum-size=200M` or `--vacuum-time=7d`. Rotated `*.gz`/`*.1` logs can be removed. | safe; don't blanket-`rm` active `.log` files |
| `/var/crash` | Apport crash reports | `sudo rm -f /var/crash/*` | safe |
| `/var/lib/systemd/coredump` | Core dumps | `sudo rm -f /var/lib/systemd/coredump/*` | safe |
| `/tmp`, `/var/tmp` | Temp files (`/tmp` clears on reboot) | Remove stale files; simplest is a reboot for `/tmp` | safe (avoid deleting files in use) |
| `/var/lib/snapd/cache` | Snap download cache | `sudo rm -rf /var/lib/snapd/cache/*` | safe |
| Disabled snap revisions | Old snap versions kept for rollback | `snap list --all`, then `sudo snap remove <name> --revision=<n>` for each `disabled` row | caution (loses rollback) |
| `/var/lib/docker` | Docker images/containers/volumes/build cache | `docker system prune` (dangling) or `docker system prune -a --volumes` (everything unused) | caution: `-a --volumes` deletes unused images **and volume data** |
| `/root/.cache` | root user's cache | `sudo rm -rf /root/.cache/*` | safe |
| Timeshift snapshots | System restore points (often on a separate disk) | Manage via `timeshift --list` / `timeshift --delete` | caution (loses restore points) |

## Current user locations (no `sudo` needed for own home)

| Location | What it is | Reclaim command | Risk |
|---|---|---|---|
| `~/.cache` | General per-app cache | `rm -rf ~/.cache/*` | safe (apps rebuild it) |
| `~/.cache/thumbnails` | Image/video thumbnails | `rm -rf ~/.cache/thumbnails/*` | safe |
| `~/.local/share/Trash` | Trash bin | `gio trash --empty` or `rm -rf ~/.local/share/Trash/*` | safe (permanently deletes trashed files) |
| `~/.cache/pip` | pip download cache | `pip cache purge` | safe (re-downloads) |
| `~/.npm/_cacache` | npm cache | `npm cache clean --force` | safe |
| `~/.cache/yarn` | yarn cache | `yarn cache clean` | safe |
| `~/.gradle/caches` | Gradle build/dependency cache | `rm -rf ~/.gradle/caches` | safe (re-downloads/rebuilds) |
| `~/.m2/repository` | Maven local repo | `rm -rf ~/.m2/repository` | safe but re-downloads whole repo |
| `~/.cargo/registry` | Rust cargo registry cache | `rm -rf ~/.cargo/registry/cache ~/.cargo/registry/src` | safe (re-downloads) |
| `~/.ivy2/cache` | sbt/Ivy dependency cache | `rm -rf ~/.ivy2/cache` | safe (re-downloads) |
| `~/.cache/JetBrains`, `~/.cache/google-chrome`, `~/.cache/mozilla` | IDE / browser caches | delete contents | safe (rebuilt; browser stays logged in) |

## Notes
- After apt/snap/docker cleanups, re-check with `df -h` — freed space may not show until the tool
  finishes its own bookkeeping.
- `du` reports space used by files; a running process holding a deleted file keeps its space until it
  exits (visible as a gap between `du` and `df`). A reboot resolves those.
- For a quick, guided interactive explorer, `ncdu -x /path` (install: `sudo apt install ncdu`) is a
  good complement to these scripts.
