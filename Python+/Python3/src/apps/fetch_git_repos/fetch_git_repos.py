# Execute "git fetch" for all Git repos starting from "root_dir" (1st argument) recursively
# Run example: "python fetch_git_repos.py /home/aleks/pr/home"
import os
import sys
from subprocess import Popen, PIPE, TimeoutExpired
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
timeout_sec = 2 * 60
cmd_with_timeout: str = f'timeout -s 9 {timeout_sec}s {cmd}'
print(f'Command: "{cmd_with_timeout}"')
print()

batch_size = 5
slices = [git_repos[i:i + batch_size] for i in range(0, len(git_repos), batch_size)]

success_count = 0
fail_count = 0
for repo_slice in slices:
    processes: Dict[str, Popen] = dict(
        [(repo, Popen(cmd_with_timeout, stdout=PIPE, stderr=PIPE, shell=True, executable="/bin/bash", cwd=repo,
                      bufsize=1024000)) for repo in repo_slice])
    for repo, process in processes.items():
        print(f'{repo}... ', end='', flush=True)
        outs: bytearray = bytearray()
        errs: bytearray = bytearray()
        try:
            outs, errs = process.communicate(timeout=timeout_sec)
        except TimeoutExpired:
            process.kill()
            outs, errs = process.communicate()
        if process.returncode == 0:
            success_count += 1
            print(f'SUCCESS')
        else:
            fail_count += 1
            print(f'FAIL')
            print(f'Exit code: {process.returncode}')
            print(f"Stdout: {outs.decode()}")
            print(f"Stderr: {errs.decode()}")
print()
print(f'Total: repos={repo_number}, processed={success_count + fail_count}, success={success_count}, fail={fail_count}')
