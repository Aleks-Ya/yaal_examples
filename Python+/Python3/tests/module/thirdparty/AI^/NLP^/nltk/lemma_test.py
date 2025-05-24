import pytest
import nltk
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import word_tokenize


@pytest.mark.skip(reason="Run once")
def test_download_data():
    nltk.download()
    nltk.download('punkt')
    nltk.download('punkt_tab')
    nltk.download('wordnet')


def test_tokenize():
    sentence: str = "The striped bats are hanging on their feet for best"
    tokens: list[str] = word_tokenize(sentence)
    lemmatizer: WordNetLemmatizer = WordNetLemmatizer()
    lemmas: list[str] = [lemmatizer.lemmatize(token) for token in tokens]
    print(lemmas)
