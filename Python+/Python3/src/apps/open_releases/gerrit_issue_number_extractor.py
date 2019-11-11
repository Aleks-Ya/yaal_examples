import json
import re
from base64 import b64encode

import requests


def find_issue(project: str, branch_info: str, login: str, password: str) -> (str, str):
    try:
        branch_info = __load_branch(project, branch_info, login, password)
        revision = __extract_revision_from_json(branch_info)
        commit_json = __load_commit(project, revision, login, password)
        try:
            commit_message = __extract_message_from_json(commit_json)
        except:
            commit_message = 'None'
        commit_author = __extract_author_from_json(commit_json)
        issue = __extract_issue(commit_message)
        return issue, commit_author
    except:
        return 'None', 'None'


__host = 'gerrit.ahml-infr.projects.epam.com'
__gerrit_base_url = f'http://{__host}/a'


def __load_branch(project: str, branch: str, login: str, password: str) -> str:
    branch_escaped = branch.replace("/", "%2F")
    get_branches_url = f'{__gerrit_base_url}/projects/{project}/branches/{branch_escaped}'

    r = __send_request(get_branches_url, login, password)
    branch = r.text
    branch_json = __remove_1st_line(branch)
    return branch_json


def __remove_1st_line(text):
    branch_json = text[text.find('\n') + 1:text.rfind('\n')]
    return branch_json


def __send_request(get_branches_url, login, password):
    user_and_pass = b64encode(f'{login}:{password}'.encode()).decode("ascii")
    headers = {'Authorization': 'Basic %s' % user_and_pass}
    r = requests.get(get_branches_url, headers=headers)
    return r


def __load_commit(project: str, revision: str, login: str, password: str):
    get_commits_url = f'{__gerrit_base_url}/projects/{project}/commits/{revision}'
    r = __send_request(get_commits_url, login, password)
    commit = r.text
    commit_json = __remove_1st_line(commit)
    return commit_json


def __extract_revision_from_json(json_str: str):
    json_obj = json.loads(json_str)
    revision = json_obj.get('revision')
    return revision


def __extract_message_from_json(json_str: str):
    json_obj = json.loads(json_str)
    message = json_obj.get('message')
    return message


def __extract_author_from_json(json_str: str):
    json_obj = json.loads(json_str)
    author = json_obj.get('author').get('name')
    return author


def __extract_issue(commit_message):
    match = re.findall(r'(AHMLDWH-\d{1,5})', commit_message)
    if match:
        return match[0]
    else:
        return None
