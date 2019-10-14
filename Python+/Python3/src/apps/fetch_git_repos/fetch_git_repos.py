# Execute "git fetch" for all Git repos starting from "root_dir" (1st argument) recursively
# Run example: "python fetch_git_repos.py /home/aleks/pr/home"
# Add Linux alias: alias fetch_all='python3 /home/aleks/pr/home/yaal_examples/Python+/Python3/src/apps/fetch_git_repos/fetch_git_repos.py /home/aleks/pr'
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

cmd: str = 'git fetch --tags'
timeout_sec = 1 * 60
print(f'Command: "{cmd}"')
print(f'Timeout (seconds): {timeout_sec}')

batch_size = 3
slices = [git_repos[i:i + batch_size] for i in range(0, len(git_repos), batch_size)]
print(f'Batch size: {batch_size}')
print(f'Slice number: {len(slices)}')
print()

success_count = 0
success_no_changes_count = 0
success_has_changes_count = 0
fail_count = 0
counter = 0
for repo_slice in slices:
    processes: Dict[str, Popen] = dict(
        [(repo, Popen(cmd, stdout=PIPE, stderr=PIPE, shell=True, executable="/bin/bash", cwd=repo,
                      bufsize=1024000)) for repo in repo_slice])
    for repo, process in processes.items():
        counter = counter + 1
        print(f'[{counter} of {repo_number}] [PID {process.pid}] {repo}... ', end='', flush=True)
        time_spent_sec = 0
        outs: bytearray = bytearray()
        errs: bytearray = bytearray()
        try:
            outs, errs = process.communicate(timeout=timeout_sec)
        except TimeoutExpired:
            process.kill()
            outs, errs = process.communicate()
        if process.returncode == 0:
            success_count += 1
            repo_has_changes = len(errs) > 0
            if repo_has_changes:
                success_has_changes_count = success_has_changes_count + 1
            else:
                success_no_changes_count = success_no_changes_count + 1
            print("HAS CHANGES" if repo_has_changes else "NO CHANGES")
        else:
            fail_count += 1
            print(f'FAIL')
            print(f'Exit code: {process.returncode}')
            print(f"Stdout: {outs.decode()}")
            print(f"Stderr: {errs.decode()}")
print()
print(f'Total: repos={repo_number}, processed={success_count + fail_count}, success total={success_count}, '
      f'success has changes={success_has_changes_count}, success no changes={success_no_changes_count}, '
      f'fail={fail_count}')
