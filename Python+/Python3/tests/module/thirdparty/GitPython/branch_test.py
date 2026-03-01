from git import Repo, Commit


def test_get_latest_commit_in_current_branch(yaal_examples_repo: Repo):
    head: Commit = yaal_examples_repo.head.commit
    print(f"Latest commit: {head}")
