#!/usr/bin/env bash
# Shortcut for: claude --permission-mode auto --model sonnet -p "/add-english-word-to-anki ..."
# Usage: ./english-add-english-word-to-anki.sh [--dry-run] [--no-pictures] [<input-file>]
set -euo pipefail

if [[ "${1:-}" == "-h" || "${1:-}" == "--help" ]]; then
    cat <<'EOF'
Usage: english-add-english-word-to-anki.sh [--dry-run] [--no-pictures] [<input-file>]

Turn real-life sentences from <input-file> into new English Anki flashcards
(or backfill a matching duplicate), via `claude -p "/add-english-word-to-anki ..."`.
<input-file> defaults to new_anki_words.md in this directory if omitted.

  --dry-run      Preview without writing anything to Anki or the input file.
  --no-pictures  Skip Picture-field image search/verification.
  -h, --help     Show this help and exit.
EOF
    exit 0
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

prompt="/add-english-word-to-anki"
for arg in "$@"; do
    prompt+=" '${arg//\'/\'\\\'\'}'"
done

exec claude --permission-mode auto --model sonnet -p "$prompt"
