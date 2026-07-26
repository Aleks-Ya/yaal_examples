# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repository is

A personal knowledge base of small, independent example projects and cheat sheets covering dozens of
languages, frameworks, and tools (Java, Scala, Kotlin, Python, Rust, Web, Android, BigData, DevOps,
Databases, CLI tools, etc.). There is **no single application** and **no repository-wide build/test
command** — each example lives in its own directory with its own (or a shared per-language) build
config, and most examples are unrelated to each other.

When asked to work on "the project," first figure out which specific example/subdirectory is meant —
almost every task here is scoped to one leaf directory, not the whole repo.

## Directory conventions

- Top-level and nested directories suffixed with `+` (e.g. `Java+`, `BigData+`, `JSE+`) are **category
  folders** used purely for grouping; they usually don't contain a runnable project themselves.
- Directories *without* a trailing `+` that contain a build file (`build.gradle`, `pom.xml`,
  `build.sbt`, `Cargo.toml`, `package.json`, etc.) are the actual **leaf example projects**.
- `CLI/` is not code — it's a library of one-markdown-file-per-tool cheat sheets (commands, flags,
  examples) for CLI programs.
- `Kata+/` contains practice exercises, often numbered step-by-step system-admin walkthroughs
  (e.g. `Kata+/FileSystem+/030-gpt-ext4`) rather than source code.

## Working in each ecosystem

There's no root build tool; `cd` into the relevant project and use its own tooling.

**Java (`Java+/`)** — a single multi-module Gradle build. `Java+/settings.gradle` includes ~400
modules (paths like `BigData+:Kafka+:Kafka2+:Kafka2`), and `Java+/build.gradle` centralizes shared
dependency versions/coordinates (as `ext` properties, e.g. `mockitoDep`, `jacksonVersion`) applied to
all subprojects via `subprojects {}`. Tests use JUnit 5/6 (`useJUnitPlatform()`), default Gradle task
is `test`.
```
cd Java+
./gradlew :JSE+:Core:test                    # run one module's tests
./gradlew :JSE+:Core:test --tests "*ClassName"
```
Some modules depend on real external services (Kafka, DBs, Hadoop, etc.) and will fail without that
infra — check the module's source/resources before assuming a failure is a real bug.

**Scala (`Scala+/`)** — `ScalaSbt212/` and `ScalaSbt213/` are sbt multi-project builds (own `build.sbt`
at their root, subprojects nested inside e.g. `Libs+`, `Kafka+`, `Akka+`); use `sbt` from that root.
`ScalaMaven/` instead holds standalone Maven projects, one per directory, each with its own `pom.xml`.
`PlayFramework/` is a separate sbt multi-project build (own `build.sbt`) covering Play Framework and
Slick examples.

**Kotlin (`Kotlin+/`)** — independent projects, either Gradle (`build.gradle.kts` + `./gradlew`) or
Maven (`pom.xml`), one per directory (e.g. `kotlin-gradle`, `kotlin-maven`).

**Python (`Python+/Python3/`)** — the most actively developed subproject; it has its own
`Python+/Python3/AGENTS.md` with detailed conventions — read that before editing here. Summary:
source under `src/`, imported as `apps.<name>...` (pytest uses `pythonpath = src` from `pytest.ini`);
tests under `tests/`.
```
cd Python+/Python3
pytest                     # excludes tests marked "integration" (see pytest.ini addopts)
pytest -k <expr> -q        # run a single test
pytest -m integration      # run integration tests
```
Other `Python+/` subfolders (`Cython`, `RobotFramework`, `Tox+`, `Anki+`, `PythonDocker+`) are separate,
smaller examples with their own setup.

**Rust (`Rust+/`)** — `Cargo+/NewProject/guessing_game` is a Cargo project (`cargo run`/`cargo test`
from that directory). `HelloWorld/` is a single `.rs` file with no Cargo project, compiled directly
with `rustc`.

**Web (`Web+/`)** — `Angular+/quickstart` and `Svelte+/playground` are standalone npm projects with
their own `package.json`; run `npm install` then the scripts defined there from that directory.
`JavaScript+` is also its own npm project (own `package.json`), grouping plain-JS/jQuery examples and
unit tests. `CSS/`, `HTML/`, and `CodeHighlight/` hold small standalone markup/snippet examples with no
build step.

**Bash (`Bash+/`)** — examples are `.bats` files (Bats testing framework), organized by topic
(`conditions/`, `data_structures/`, `files/`, ...). Run a file with `bats <file>.bats`.

**Android (`Android/AndroidExamples/`)** — a Gradle Android project; use its `./gradlew`.

Everything else (`Database+`, `DevOps+`, `Documentation+`, `Building+`, `BigData+`, `CPP+`, `Groovy+`,
`IDE+`, `Bat+`, `Lua`) follows the same pattern: look for a build/config file in the specific leaf
directory you're touching and use that ecosystem's native tool — there's no shared convention to lean
on beyond what's described above.
