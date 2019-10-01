# Execute "git fetch" for all Git repos starting from "root_dir" (1st argument) recursively
# Run example: "python fetch_git_repos.py /home/aleks/pr/home"
# TODO stuck on wait() if many repos (about 100)
import os
import sys
from subprocess import Popen, PIPE
from typing import Any, List, Dict

root_dir: str = sys.argv[1]
print(f'Root dir: {root_dir}')

git_dir: str = ".git"


def on_error(error: Any):
    raise error


git_repos: List[str] = [tup[0] for tup in os.walk(root_dir, onerror=on_error) if git_dir in tup[1]]
repo_number = len(git_repos)
print(f'Git repositories ({repo_number}): {git_repos}')

cmd: str = 'git fetch'
timeout_millis = 1 * 60 * 1000
processes: Dict[str, Popen] = dict(
    [(repo, Popen(cmd, stdout=PIPE, stderr=PIPE, shell=True, executable="/bin/bash", cwd=repo))
     for repo in git_repos])
success_count = 0
fail_count = 0
for repo, process in processes.items():
    print(f'{repo}... ', end='', flush=True)
    exit_code = process.wait(timeout=timeout_millis)
    if exit_code == 0:
        success_count += 1
        print(f'SUCCESS')
    else:
        fail_count += 1
        print(f'FAIL')
        print(f'Exit code: {exit_code}')
        print(f"Stdout: {process.stdout.read()}")
        print(f"Stderr: {process.stderr.read()}")
print()
print(f'Total: repos={repo_number}, processed={success_count + fail_count}, success={success_count}, fail={fail_count}')
