# Execute "git fetch" for all Git repos starting from "root_dir" (1st argument) recursively
# Run example: "python fetch_git_repos.py /home/aleks/pr/home"
# Add Linux alias: alias fetch_all='python3 /home/aleks/pr/home/yaal_examples/Python+/Python3/src/apps/fetch_git_repos/fetch_git_repos.py /home/aleks/pr'
import os
import sys
from subprocess import Popen, PIPE, TimeoutExpired
from typing import Any, List, Dict


class FetchResult:
    def __init__(self, return_code: int, outs: str, errs: str, has_changes: bool, is_success: bool):
        self.return_code = return_code
        self.outs = outs
        self.errs = errs
        self.has_changes = has_changes
        self.is_success = is_success


class FetchProcess:

    def __init__(self, cmd: str, repo: str, buffer_size: int = 1024000) -> None:
        self.__process = Popen(cmd, stdout=PIPE, stderr=PIPE, shell=True, executable="/bin/bash", cwd=repo,
                               bufsize=buffer_size)
        self.__repo = repo

    def get_pid(self) -> int:
        return self.__process.pid

    def get_repo(self) -> str:
        return self.__repo

    def fetch_repo(self) -> FetchResult:
        try:
            outs, errs = self.__process.communicate(timeout=timeout_sec)
        except TimeoutExpired:
            self.__process.kill()
            outs, errs = self.__process.communicate()
        has_changes = len(errs) > 0
        is_success = self.__process.returncode == 0
        return FetchResult(self.__process.returncode, outs.decode(), errs.decode(), has_changes, is_success)


class FetchStatistics:

    def __init__(self) -> None:
        self.success_count = 0
        self.success_no_changes_count = 0
        self.success_has_changes_count = 0
        self.fail_count = 0
        self.total_count = 0

    def add_result(self, result: FetchResult) -> None:
        self.total_count += 1
        if result.is_success:
            self.success_count += 1
            if result.has_changes:
                self.success_has_changes_count += 1
            else:
                self.success_no_changes_count += 1
        else:
            self.fail_count += 1


class Printer:
    @staticmethod
    def print_fetch_process(fetch_process: FetchProcess, repo_number: int) -> None:
        counter = statistics.total_count + 1
        print(f'[{counter} of {repo_number}] [PID {fetch_process.get_pid()}] {fetch_process.get_repo()}... ',
              end='', flush=True)

    @staticmethod
    def print_fetch_result(fetch_result: FetchResult):
        if fetch_result.is_success:
            print("HAS CHANGES" if fetch_result.has_changes else "NO CHANGES")
        else:
            print(f'FAIL')
            print(f'Exit code: {fetch_result.return_code}')
            print(f"Stdout: {fetch_result.outs}")
            print(f"Stderr: {fetch_result.errs}")

    @staticmethod
    def print_statistics(fetch_statistics: FetchStatistics):
        print()
        print(f'Total: repos={fetch_statistics.total_count}, processed={fetch_statistics.total_count}, '
              f'success total={fetch_statistics.success_count}, '
              f'success has changes={fetch_statistics.success_has_changes_count}, '
              f'success no changes={fetch_statistics.success_no_changes_count}, '
              f'fail={fetch_statistics.fail_count}')

    @staticmethod
    def print_parameters(root_dir: str, git_repos: List[str], cmd: str, timeout_sec: int, batch_size: int, slices):
        print(f'Root dir: {root_dir}')
        repo_number = len(git_repos)
        print(f'Git repositories ({repo_number}): {git_repos}')
        print(f'Command: "{cmd}"')
        print(f'Timeout (seconds): {timeout_sec}')
        print(f'Batch size: {batch_size}')
        print(f'Slice number: {len(slices)}')
        print()


def on_error(error: Any):
    raise error


root_dir: str = sys.argv[1]
git_dir: str = ".git"
git_repos: List[str] = [tup[0] for tup in os.walk(root_dir, onerror=on_error) if git_dir in tup[1]]
timeout_sec = 1 * 60
cmd: str = f'timeout -s 9 {timeout_sec}s git fetch --tags'
batch_size = 3
slices = [git_repos[i:i + batch_size] for i in range(0, len(git_repos), batch_size)]

Printer.print_parameters(root_dir, git_repos, cmd, timeout_sec, batch_size, slices)

statistics = FetchStatistics()

for repo_slice in slices:
    processes: Dict[str, FetchProcess] = dict([(repo, FetchProcess(cmd, repo)) for repo in repo_slice])
    for process in processes.values():
        Printer.print_fetch_process(process, len(git_repos))
        result = process.fetch_repo()
        statistics.add_result(result)
        Printer.print_fetch_result(result)

Printer.print_statistics(statistics)
