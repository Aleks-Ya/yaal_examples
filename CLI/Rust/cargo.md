# cargo CLI

## Install
Install (outdated version): `sudo apt install -y cargo`

Install (latest): `curl --proto '=https' --tlsv1.2 https://sh.rustup.rs -sSf | sh`
Install docs: https://doc.rust-lang.org/book/ch01-01-installation.html#installation

## Commands
Help: `cargo --help`
Version: `cargo --version`

List installed crates: `cargo install --list`
Search crates by name: `cargo search openssl` 
Install a crate: `cargo install mdbook`

Create a new project: `cargo new my_project`
Analyze current project: `cargo check`
Build current projec: `cargo build`
Run current project: `cargo run`
