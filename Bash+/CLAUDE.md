# CLAUDE.md

Guidance for Claude Code (claude.ai/code) when working in `Bash+/`. See the repository root
`../CLAUDE.md` for the overall structure; this file covers only this directory.

## What this folder is

A cheat-sheet / example collection for Bash, not a single application. Content splits into two kinds:

1. **`.bats` example tests** — the bulk of the folder. Each file demonstrates a Bash feature through
   small, self-contained [Bats](https://github.com/bats-core/bats-core) test cases. They are learning
   references *and* executable assertions: the `@test` body doubles as documentation of expected
   behavior. Organized by topic into subdirectories.
2. **Standalone `.sh` scripts** — plain shell scripts, either illustrating a single concept
   (`core/echo.sh`, `signal/trap_SIGINT.sh`, `files/current_dir.sh`) or real personal utilities under
   `apps/` (backups, upgrades, symlink setup). The `apps/` scripts reference machine-specific paths
   (e.g. `/home/aleks/...`, flash-drive mount points) and are not meant to be run in CI.

## Topic directories

`conditions/`, `core/`, `data_structures/`, `data_types/`, `files/`, `variables/` (+ `variables/string/`),
`sed/`, `regex/`, `signal/`, `process/`, `JSON/`, `bats/` (Bats framework's own features),
`alias/`, `apps/`.

## Running the examples

Bats is installed system-wide (`bats`, currently v1.10.0). Run a single file:

```
bats conditions/if.bats
bats data_structures/arrays.bats
```

There is no build step, no aggregate runner, and no shared helper library. To run everything:

```
bats $(find . -name '*.bats')
```

### Known intentionally-failing test

`bats/lifecycle.bats` deliberately includes a `@test "Fail test"` (and a `skip`ped test) to
demonstrate how Bats reports failures and skips. A `not ok` there is expected — **do not "fix" it**.

## Conventions when adding or editing examples

- `.bats` files start with `#!/usr/bin/env bats` and use `@test "description" { ... }` blocks.
- Keep each test self-contained: define the variables/arrays it needs inside its own block (no shared
  state between tests). Existing files follow this even when it means repeating setup.
- Assertions are bare test commands — `[ ... ]`, `[[ ... ]]`, or a command whose exit status is the
  pass/fail signal. A test passes iff its last command returns 0.
- Some examples depend on external CLI tools: `JSON/parse_json.bats` needs `jq`, `bats/lifecycle.bats`
  uses `dc`. Note such dependencies in a comment as those files do.
- File naming is `snake_case` describing the demonstrated feature (`is_dir_empty.bats`,
  `command_substitution.bats`). Match the directory's topic.
- Don't try to run `apps/*.sh` to "verify" them — they act on the author's real machine/filesystem.
