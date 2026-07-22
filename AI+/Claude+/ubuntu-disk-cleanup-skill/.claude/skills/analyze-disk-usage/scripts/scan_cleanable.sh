#!/usr/bin/env bash
# scan_cleanable.sh — measure well-known safe-to-clean locations on Ubuntu and
# print them largest-first. Read-only: it only runs `du`, never deletes.
#
# Run under sudo so system-owned and other-user paths are readable:
#   sudo ./scan_cleanable.sh
# It still reports the *human* user's caches (not root's) by resolving
# $SUDO_USER's home.
#
# See references/cleanable-locations.md for the reclaim command for each entry.
set -uo pipefail

# Resolve the invoking human's home even when run via sudo.
if [[ -n "${SUDO_USER:-}" ]]; then
  home="$(getent passwd "$SUDO_USER" | cut -d: -f6)"
else
  home="${HOME:-/root}"
fi

# Curated list of locations that are safe (or safe-with-caution) to empty.
# Globs are expanded below; missing paths are silently skipped.
paths=(
  # --- system (need root) ---
  /var/cache/apt/archives          # downloaded .deb packages
  /var/log                         # system logs / journal
  /var/crash                       # apport crash reports
  /var/lib/systemd/coredump        # core dumps
  /var/tmp                         # persistent temp
  /tmp                             # temp (also cleared on reboot)
  /var/lib/snapd/cache             # snap download cache
  /var/lib/docker                  # docker images/containers/volumes (CAUTION)
  /root/.cache                     # root user cache

  # --- current human user ---
  "$home"/.cache                   # general user cache
  "$home"/.cache/thumbnails        # image thumbnails
  "$home"/.local/share/Trash       # trash bin
  "$home"/.cache/pip               # pip download cache
  "$home"/.npm/_cacache            # npm cache
  "$home"/.cache/yarn              # yarn cache
  "$home"/.gradle/caches           # gradle build cache
  "$home"/.m2/repository           # maven local repo (rebuildable)
  "$home"/.cargo/registry          # rust cargo registry cache
  "$home"/.ivy2/cache              # sbt/ivy cache
  "$home"/.cache/JetBrains         # IDE caches
  "$home"/.cache/google-chrome     # chrome cache
  "$home"/.cache/mozilla           # firefox cache
)

# Collect "bytes<TAB>path" for existing entries, then sort numerically desc and
# render sizes human-readable. Using bytes for the sort keeps it exact.
{
  for p in "${paths[@]}"; do
    [[ -e "$p" ]] || continue
    # -s summary, -b bytes, -x stay on one filesystem.
    du -sbx -- "$p" 2>/dev/null
  done
} | sort -rn | while IFS=$'\t' read -r bytes path; do
  printf '%s\t%s\n' "$(numfmt --to=iec --suffix=B "$bytes")" "$path"
done
