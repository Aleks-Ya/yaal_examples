from huggingface_hub import HfFileSystem


def test_walk_dataset_file_tree():
    fs: HfFileSystem = HfFileSystem()
    repo_path: str = "datasets/Ya-Alex/anki-addons"
    for root, dirs, files in fs.walk(repo_path):
        print(f"Current Directory: {root}")
        for file in files:
            print(f"File: {file}")
