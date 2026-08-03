#!/usr/bin/env bash
# Shortcut for: claude --permission-mode auto --model sonnet -p "/populate-existing-english-anki-notes ..."
# Usage: ./english-populate-existing-english-anki-notes.sh [--dry-run] [--no-pictures] [--limit N]
set -euo pipefail

if [[ "${1:-}" == "-h" || "${1:-}" == "--help" ]]; then
    cat <<'EOF'
Usage: english-populate-existing-english-anki-notes.sh [--dry-run] [--no-pictures] [--limit N]

Backfill empty Claude-owned fields on existing English Anki notes tagged
en::to-refine (no input file), via `claude -p "/populate-existing-english-anki-notes ..."`.

  --dry-run      Preview without writing anything to Anki.
  --no-pictures  Skip Picture-field image search/verification.
  --limit N      Process at most N notes in this run.
  -h, --help     Show this help and exit.
EOF
    exit 0
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

prompt="/populate-existing-english-anki-notes"
for arg in "$@"; do
    prompt+=" '${arg//\'/\'\\\'\'}'"
done

exec claude --permission-mode auto --model sonnet -p "$prompt"
