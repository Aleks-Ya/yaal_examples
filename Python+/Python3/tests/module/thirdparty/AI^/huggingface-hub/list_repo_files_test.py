from huggingface_hub import list_repo_files, list_repo_tree

repo_id: str = "Ya-Alex/anki-addons"


def test_list_all_files():
    files: list[str] = list_repo_files(repo_id, repo_type="dataset")
    print(files)


def test_list_parquet_files():
    all_files: list[str] = list_repo_files(repo_id, repo_type="dataset")
    parquet_files: list[str] = [file for file in all_files if file.endswith(".parquet")]
    print(parquet_files)


def test_list_files_in_branch():
    files: list[str] = list_repo_files(repo_id, repo_type="dataset", revision="main")
    print(files)


def test_list_files_in_dir():
    all_files: list[str] = list_repo_files(repo_id, repo_type="dataset")
    dir_files: list[str] = [file for file in all_files if file.startswith("history")]
    print(dir_files)
