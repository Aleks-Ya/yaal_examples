from pathlib import Path

from git import Repo


def test_commit_changes(repo: Repo):
    working_dir: Path = Path(repo.working_dir)
    (working_dir / "file1.txt").write_text("New content")
    repo.index.add(["file1.txt"])
    repo.index.commit("Your commit message")
    assert repo.head.commit.message == "Your commit message"
