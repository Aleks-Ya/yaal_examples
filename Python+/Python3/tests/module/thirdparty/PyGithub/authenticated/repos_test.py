from github import Github
from github.AuthenticatedUser import AuthenticatedUser
from github.PaginatedList import PaginatedList
from github.RateLimit import RateLimit
from github.Repository import Repository


def test_list_own_repos(github_authenticated: Github):
    user: AuthenticatedUser = github_authenticated.get_user()
    repos: PaginatedList[Repository] = user.get_repos()
    for repo in repos:
        print(repo.name)


def test_get_repo_by_name(github_authenticated: Github):
    user: AuthenticatedUser = github_authenticated.get_user()
    repo: Repository = user.get_repo("yaal_examples")
    repo_id: int = repo.id
    assert repo_id == 14327752


def test_list_repo_languages(github_authenticated: Github):
    user: AuthenticatedUser = github_authenticated.get_user()
    repo: Repository = user.get_repo("yaal_examples")
    languages: dict[str, int] = repo.get_languages()
    print(languages)


def test_rate_limit(github_anonymous: Github):
    limit: RateLimit = github_anonymous.get_rate_limit()
    print(limit)
