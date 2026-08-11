import fnmatch

import pytest
from huggingface_hub import HfApi, CommitOperationDelete

api: HfApi = HfApi()
repo_id: str = "Ya-Alex/anki-addons"
repo_type: str = "dataset"
pattern: str = "*"


@pytest.mark.skip(reason="not tested")
def test_delete_folder():
    api.delete_folder(pattern, repo_id=repo_id, repo_type=repo_type)


@pytest.mark.skip(reason="not tested")
def test_delete_folder2():
    print(f"Fetching files from {repo_type} repository '{repo_id}'...")
    all_files: list[str] = api.list_repo_files(repo_id=repo_id, repo_type=repo_type)
    files_to_delete: list[str] = [f for f in all_files if fnmatch.fnmatch(f, pattern)]
    if not files_to_delete:
        print("No files found matching the pattern.")
        return

    operations: list[CommitOperationDelete] = [
        CommitOperationDelete(path_in_repo=file_path) for file_path in files_to_delete
    ]

    print(f"Deleting {len(operations)} file(s)...")
    api.create_commit(
        repo_id=repo_id,
        repo_type=repo_type,
        operations=operations,
        commit_message=f"Delete files matching pattern '{pattern}'"
    )
    print("Files deleted successfully.")
