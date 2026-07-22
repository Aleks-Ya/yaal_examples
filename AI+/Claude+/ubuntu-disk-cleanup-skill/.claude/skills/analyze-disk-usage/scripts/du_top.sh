#!/usr/bin/env bash
# du_top.sh — show the largest directories under a path, biggest first.
#
# Read-only. Stays on one filesystem (du -x) so it won't wander into other
# mounts, and suppresses permission-denied noise. Prefix with sudo for
# system-owned paths (e.g. sudo du_top.sh /var).
#
# Usage: du_top.sh [PATH] [DEPTH] [COUNT]
#   PATH   directory to inspect            (default: /)
#   DEPTH  how many levels deep to list    (default: 1)
#   COUNT  how many rows to show           (default: 25)
set -uo pipefail

path="${1:-/}"
depth="${2:-1}"
count="${3:-25}"

if [[ ! -d "$path" ]]; then
  echo "du_top.sh: not a directory: $path" >&2
  exit 1
fi

# -x: don't cross filesystem boundaries. GNU sort -rh sorts human sizes.
du -x -h --max-depth="$depth" -- "$path" 2>/dev/null \
  | sort -rh \
  | head -n "$count"
