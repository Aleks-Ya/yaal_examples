import json
import os
import sys
from typing import List

from src.apps.open_releases.gerrit_issue_number_extractor import find_issue

gerrit_login = sys.argv[1]
gerrit_password = sys.argv[2]

json_dir = '/home/aleks/pr/ahml/gerrit/devops/devops-component-versions/mks-settings'

skip_repos = ['fake-project', 'unified-template-dag', 'submit-container-base', '1c-test-soap-service']
comments = {'1c-download-dag':
                {'1.1': 'in testing'},
            'abm-accreditation-service':
                {'0.4': 'lost ticket, ask Dima',
                 '0.5': 'lost ticket, ask Dima'},
            'abm-misuse-funds':
                {'0.2': 'closed, to Dima',
                 '0.6': 'wait deploy to PROD'},
            'golden-report-db':
                {'2.45': 'EMISS region migration'},
            'po-front':
                {'0.8': 'wait deploy to PROD'},
            'lkz-dag':
                {'1.10.1': 'release is absent in Jenkins'},
            'abm-schema':
                {'1.7': 'release is absent in Jenkins'},
            'gateway-service':
                {'0.1': 'release is absent in Jenkins',
                 '0.2': 'release is absent in Jenkins'},
            'kafka-bridge-microservice':
                {'1.2': 'release is absent in Jenkins'},
            'simple-table-loader':
                {'1.4': 'release is absent in Jenkins'},
            'config-service':
                {'1.22.1': 'release is absent in Jenkins'}
            }


def make_url(repo_name: str):
    return f'https://jenkins.ahml-infr.projects.epam.com/view/{repo_name}/'


file_paths = []
for subdir, dirs, files in os.walk(json_dir):
    for file in files:
        file_paths.append(os.path.join(subdir, file))

jsons = []
for file_path in file_paths:
    with open(file_path) as f:
        json_str = f.read()
        jsons.append(json.loads(json_str))

repos = []
for repo_list in jsons:
    for repo in repo_list:
        repos.append(repo)


class Repo:
    def __init__(self):
        self.name: str = ''
        self.open_hotfixes: List[str] = []
        self.open_releases: List[str] = []

    def has_open_releases(self):
        return len(self.open_releases) > 0

    def has_open_hotfixes(self):
        return len(self.open_hotfixes) > 0

    def has_open_versions(self):
        return self.has_open_releases() or self.has_open_hotfixes()


repo_objects: List[Repo] = []
for repo in repos:
    repo_obj = Repo()
    repo_obj.name = repo.get('name')

    versions = repo.get('version')

    hotfixes = versions.get('hotfix')
    for hotfix_key in hotfixes:
        hotfix_value = hotfixes[hotfix_key]
        closed = hotfix_value['closed'].lower() == 'true'
        version = hotfix_value['value']
        if not closed:
            repo_obj.open_hotfixes.append(hotfix_key)

    releases = versions.get('release')
    for release_key in releases:
        release_value = releases[release_key]
        closed: bool = release_value['closed'].lower() == 'true'
        version = release_value['value']
        if not closed:
            repo_obj.open_releases.append(release_key)

    repo_objects.append(repo_obj)

repo_objects.sort(key=lambda repo: repo.name)
repo_objects_with_open_versions = [repo_obj for repo_obj in repo_objects
                                   if repo_obj.name not in skip_repos and repo_obj.has_open_versions()]
print(f'Repo with open versions: {len(repo_objects_with_open_versions)}\n')


def get_comment(repo_name: str, version_key: str) -> str:
    comment_str = ''
    if repo_name in comments:
        version_comments = comments[repo_name]
        if version_key in version_comments:
            comment_str = f' [{version_comments[version_key]}]'
    return comment_str


gerrit_url = 'http://gerrit.ahml-infr.projects.epam.com'
for repo in repo_objects_with_open_versions:
    print(f'{repo.name} {make_url(repo.name)}')
    counter = 1
    if repo.has_open_releases():
        for release_key in repo.open_releases:
            comment = get_comment(repo.name, release_key)
            issue, author = find_issue(repo.name, f'release/{release_key}', gerrit_login, gerrit_password)
            print(f'{counter}){comment} '
                  f'{gerrit_url}/gitweb?p={repo.name}.git;a=log;h=refs/heads/release/{release_key} '
                  f'[Issue: {issue}] [Author: {author}]')
            counter += 1
    if repo.has_open_hotfixes():
        for hotfix_key in repo.open_hotfixes:
            comment = get_comment(repo.name, hotfix_key)
            issue, author = find_issue(repo.name, f'hotfix/{hotfix_key}', gerrit_login, gerrit_password)
            print(
                f'{counter}){comment} '
                f'{gerrit_url}/gitweb?p={repo.name}.git;a=log;h=refs/heads/hotfix/{hotfix_key} '
                f'[Issue: {issue}] [Author: {author}]')
            counter += 1
    print()
