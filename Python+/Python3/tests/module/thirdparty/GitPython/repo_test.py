from pathlib import Path

from git import Repo

from temp_helper import TempPath


def test_get_repo(yaal_examples_repo: Repo):
    assert not yaal_examples_repo.bare


def test_create_repo():
    repo_dir: Path = TempPath.dir_exists()
    print(f"repo_dir: {repo_dir}")
    repo: Repo = Repo.init(repo_dir)
    assert repo.working_dir == str(repo_dir)
