import json
import os
import sys
from typing import List

from src.apps.open_releases.gerrit_issue_number_extractor import find_issue

gerrit_login = sys.argv[1]
gerrit_password = sys.argv[2]

json_dir = '/home/aleks/pr/ahml/gerrit/devops/devops-component-versions/mks-settings'

skip_repos = ['fake-project', 'unified-template-dag', 'submit-container-base', '1c-test-soap-service',
              'container-airflow']
comments = {'1c-download-dag':
                {'1.1': 'in testing'},
            'abm-accreditation-service':
                {'0.4': 'lost ticket, ask Dima',
                 '0.5': 'lost ticket, ask Dima'},
            'abm-misuse-funds':
                {'0.2': 'Тонкие коннекшены УБ AHMLDWH-21385',
                 '0.6': 'Fix /health endpoint AHMLDWH-23127, wait deploy to PROD'},
            'golden-report-db':
                {'2.17': 'объединение банков обновление credit_authority_merger +  Актуализация git AHMLDWH-19727',
                '2.45': 'EMISS region migration'},
            'po-front':
                {'0.8': 'wait deploy to PROD AHMLDWH-22974',
                '0.10': 'Кол-во домов, in progress, AHMLDWH-23217'},
            'emiss-etl-dag':
                {'2.1': 'EMISS region migration'},
            'emiss-etl-spark':
                {'2.1': 'EMISS region migration',
                '2.3': 'DPM UI signle check, in testing, AHMLDWH-23170'},
            'pg-query-executor-dag':
                {'1.4': 'EMISS region migration'},
            'harmonization-engine':
                {'1.5': 'EMISS region migration'},
            'data-processing-module-etl':
                {'1.7': 'DPM UI, in testing'},
            'data-processing-module-airflow':
                {'1.1': 'DPM UI, in testing'},
            'abm-package-service':
                {'4.4': 'Тонкие коннекшены УБ AHMLDWH-21386'},
            'abm-subscription-service':
                {'1.1': 'Тонкие коннекшены УБ AHMLDWH-21387'},
            'ahml-abm':
                {'0.2': 'Тонкие коннекшены УБ AHMLDWH-19690'},
            'liquibase-phoenix-ext':
                {'0.1': 'Джоба закрытия падает AHMLDWH-23252'},
            'golden-report-microservice':
                {'2.8': 'Кол-во домов, in progress, AHMLDWH-23245'},
            'health-checker-microservice':
                {'2.8': 'Grafana, in development, AHMLDWH-21731'},
            'config-service':
                {'1.37': 'Дашборд Grafana AHMLDWH-21731',
                 '1.8': 'Тонкие коннекшены УБ AHMLDWH-21335'},
            'lkz-dag':
                {'1.25': 'syntax error at or near ")"',
                 '1.24.1': 'Логирование lkz_notify_update_dag'}
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
