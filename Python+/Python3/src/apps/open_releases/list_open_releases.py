import json
import os
from typing import List

json_dir = '/home/aleks/pr/ahml/gerrit/devops/devops-component-versions/mks-settings'

skip_repos = ['fake-project', 'unified-template-dag', 'submit-container-base', '1c-test-soap-service']


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

gerrit_url = 'http://gerrit.ahml-infr.projects.epam.com'
for repo in repo_objects_with_open_versions:
    print(f'{repo.name} {make_url(repo.name)}')
    counter = 1
    if repo.has_open_releases():
        for release_key in repo.open_releases:
            print(f'{counter}) {gerrit_url}/gitweb?p={repo.name}.git;a=log;h=refs/heads/release/{release_key}')
            counter += 1
    if repo.has_open_hotfixes():
        for hotfix_key in repo.open_hotfixes:
            print(f'{counter}) {gerrit_url}/gitweb?p={repo.name}.git;a=log;h=refs/heads/hotfix/{hotfix_key}')
            counter += 1
    print()
