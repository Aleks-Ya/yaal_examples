#!/usr/bin/env bash
# Shortcut for: claude --permission-mode auto --model sonnet -p "/clean-movie-subtitles ..."
# Usage: ./english-clean-movie-subtitles.sh [--dry-run] <subtitle-file>
set -euo pipefail

if [[ "${1:-}" == "-h" || "${1:-}" == "--help" ]]; then
    cat <<'EOF'
Usage: english-clean-movie-subtitles.sh [--dry-run] <subtitle-file>

Clean a movie-subtitle file (SRT or plain text) into readable prose saved
beside it with a " clean" suffix, via `claude -p "/clean-movie-subtitles ..."`.
Standalone: no Anki, no MCP.

  --dry-run   Report the planned output path without writing it.
  -h, --help  Show this help and exit.
EOF
    exit 0
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

prompt="/clean-movie-subtitles"
for arg in "$@"; do
    prompt+=" '${arg//\'/\'\\\'\'}'"
done

exec claude --permission-mode auto --model sonnet -p "$prompt"
