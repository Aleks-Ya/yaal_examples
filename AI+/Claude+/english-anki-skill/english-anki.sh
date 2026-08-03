#!/usr/bin/env bash
# Shortcut for: claude --permission-mode auto --model sonnet (interactive, cwd = this project)
# Usage: english-anki.sh [extra claude args/flags]
set -euo pipefail

if [[ "${1:-}" == "-h" || "${1:-}" == "--help" ]]; then
    cat <<'EOF'
Usage: english-anki.sh [extra claude args/flags]

Open a normal interactive `claude --permission-mode auto --model sonnet` session
with cwd set to this project, for freeform work on this skill set. Any arguments
are forwarded to `claude` as-is (see `claude --help` for those).

  -h, --help  Show this help and exit (does not start claude).
EOF
    exit 0
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

exec claude --permission-mode auto --model sonnet "$@"
