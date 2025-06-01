from github import Github
from github.NamedUser import NamedUser
from github.PaginatedList import PaginatedList
from github.Repository import Repository


def test_list_user_repos(github_anonymous: Github):
    user: NamedUser = github_anonymous.get_user("Aleks-Ya")
    repos: PaginatedList[Repository] = user.get_repos()
    for repo in repos:
        print(repo.name)
    assert "yaal_examples" in [repo.name for repo in repos]


def test_get_repo_by_name(github_anonymous: Github):
    repo: Repository = github_anonymous.get_repo("Aleks-Ya/yaal_examples")
    repo_id: int = repo.id
    assert repo_id == 14327752


def test_list_repo_languages(github_anonymous: Github):
    repo: Repository = github_anonymous.get_repo("Aleks-Ya/yaal_examples")
    languages: dict[str, int] = repo.get_languages()
    print(languages)
