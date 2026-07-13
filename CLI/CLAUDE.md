# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this directory is

A library of cheat sheets for CLI tools — one markdown file per tool (or per subcommand/service, for
tools large enough to need that split). There's no code to build/run/test here; the only "task" is
writing or editing a cheat sheet file.

## Cheat sheet format

Each file follows the same loose structure — match it when adding or editing entries:

- Start with an H1 title: `# ToolName` or `# ToolName CLI`.
- Optionally follow with a `Docs:` and/or `Site:` line linking to the tool's reference docs.
- Group commands under `##`/`###` headings by task or subcommand area (e.g. `## Install`,
  `## Commands`, `## Encrypt/Decrypt`, `### Filters`). Flat one- or two-command files can skip
  headings entirely.
- Each entry is one line: `Short description: \`the command\``. Use a fenced code block instead of
  backticks only when the command is multi-line or needs its own formatting (e.g. multi-step
  procedures, JSON/YAML bodies).
- Keep descriptions terse and command-focused, consistent with the surrounding file — this is a
  reference, not prose documentation.

## Naming and organization

- File names are kebab-case, usually prefixed with the parent tool/vendor when it disambiguates
  (e.g. `AWS/Service/aws-kms.md`, `Docker/docker-run.md`).
- A directory holding several files (e.g. `AWS/Service/`, most `Linux/*` subfolders) is just a
  grouping of related per-tool or per-service cheat sheets — there's no shared file between them.
- A directory holding one cheat sheet plus non-`.md` files (e.g. `Linux/curl/` with `body.txt`,
  `Jenkins/JenkinsCli/` with sample job XML, `Kaggle/example-dataset-1/` with a `dataset` folder) is a
  cheat sheet bundled with fixtures/examples referenced by that one `.md` file — the `.md` file name
  usually matches the directory.
