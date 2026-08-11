from typing import Iterable

from huggingface_hub import list_repo_tree, RepoFile, RepoFolder

repo_id: str = "Ya-Alex/anki-addons"


def test_list_files_in_dir():
    files: Iterable[RepoFile | RepoFolder] = list_repo_tree(repo_id, repo_type="dataset", path_in_repo="history")
    for file in files:
        print(file)
