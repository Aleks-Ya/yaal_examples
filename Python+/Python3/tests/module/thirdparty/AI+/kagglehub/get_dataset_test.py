from pathlib import Path

import kagglehub
from kagglehub import KaggleDatasetAdapter
from pandas import DataFrame

handle: str = "alexeyya/anki-addon-catalog"


def test_download_to_file():
    act_path: str = kagglehub.dataset_download(handle)
    exp_path: str = str(Path.home() / ".cache" / "kagglehub" / "datasets" / handle / "versions" / "1")
    assert act_path == exp_path
    print("Path to dataset files:", act_path)


def test_read_by_panda():
    df: DataFrame = kagglehub.dataset_load(KaggleDatasetAdapter.PANDAS, handle, "addons.json")
    head: DataFrame = df.head()
    print("First 5 records:", head)
