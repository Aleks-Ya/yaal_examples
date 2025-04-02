from huggingface_hub import hf_hub_download


def test_download_file():
    path: str = hf_hub_download(repo_id="google/pegasus-xsum", filename="config.json")
    print(path)
