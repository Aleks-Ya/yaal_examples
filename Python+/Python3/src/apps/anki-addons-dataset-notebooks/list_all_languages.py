from numpy import ndarray
from pandas import DataFrame, Series
from datasets import load_dataset, DatasetDict, Dataset

dataset: DatasetDict = load_dataset("Ya-Alex/anki-addons")
train: Dataset = dataset["train"]
df: DataFrame = train.to_pandas()
github: Series = df['github'].apply(Series)
languages: Series = github['languages']
unique_languages: ndarray = languages.explode('languages').unique()
print(unique_languages)
