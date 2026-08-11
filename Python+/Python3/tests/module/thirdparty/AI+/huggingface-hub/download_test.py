from pathlib import Path

from huggingface_hub import hf_hub_download


def test_download_file_from_root_folder():
    path: str = hf_hub_download(repo_id="google/pegasus-xsum", filename="config.json")
    print(path)


def test_download_file_from_subfolder():
    path: str = hf_hub_download(repo_id="google/smol", subfolder="smoldoc", filename="aa_en.jsonl", repo_type="dataset")
    print(path)


def test_download_file_to_local_path():
    local_file: Path = Path("/tmp/downloaded_config.json")
    path: str = hf_hub_download(repo_id="google/pegasus-xsum", filename="config.json", local_dir=str(local_file.parent))
    assert path == str(local_file)


def test_download_file_from_subfolder_to_local_path():
    local_file: Path = Path("/tmp/downloaded_aa_en.jsonl")
    path: str = hf_hub_download(repo_id="google/smol", subfolder="smoldoc", filename="aa_en.jsonl", repo_type="dataset",
                                local_dir=str(local_file.parent))
    assert path == "/tmp/smoldoc/aa_en.jsonl"
