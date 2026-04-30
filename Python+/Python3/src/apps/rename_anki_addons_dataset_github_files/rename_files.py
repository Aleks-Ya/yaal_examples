from pathlib import Path


BASE_DIR = Path("/home/aleks/anki-addons-dataset/history/2025-06-22/1-raw/2-github")


def rename_files(base_dir: Path, dry_run: bool = True) -> None:
    for repo_dir in base_dir.glob("*/*"):
        if not repo_dir.is_dir():
            continue

        github_user = repo_dir.parent.name
        github_repo = repo_dir.name
        prefix = f"{github_user}_{github_repo}_"

        for file_path in repo_dir.iterdir():
            if not file_path.is_file():
                continue

            old_name = file_path.name

            if not old_name.startswith(prefix):
                continue

            new_name = old_name.removeprefix(prefix)
            new_path = file_path.with_name(new_name)

            if new_path.exists():
                print(f"SKIP: target already exists: {new_path}")
                continue

            print(f"RENAME: {file_path} -> {new_path}")

            if not dry_run:
                file_path.rename(new_path)


if __name__ == "__main__":
    rename_files(BASE_DIR, dry_run=True)