from datasets import load_dataset, DatasetDict, Dataset
from pandas import DataFrame


def test_read_hugging_face_dataset():
    dataset: DatasetDict = load_dataset("Ya-Alex/anki-addons")
    train: Dataset = dataset["train"]
    print(train)
    assert train.dataset_size > 0


def test_read_as_data_frame():
    dataset: DatasetDict = load_dataset("Ya-Alex/anki-addons")
    train: Dataset = dataset["train"]
    df: DataFrame = train.to_pandas()
    print(df)
