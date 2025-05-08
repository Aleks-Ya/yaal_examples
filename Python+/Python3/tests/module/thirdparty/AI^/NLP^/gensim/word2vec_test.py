import os
import tempfile
from typing import Type

from gensim.models import Word2Vec
from numpy import ndarray
from numpy.testing import assert_array_equal


def test_get_vector():
    sentences: list[list[str]] = [["word1", "word2", "word3"], ["word4", "word5"]]
    vector_size: int = 100
    model: Word2Vec = Word2Vec(sentences, vector_size=vector_size, window=5, min_count=1, workers=4)
    vector: Type[ndarray] = model.wv['word1']
    print(vector)
    assert len(vector) == vector_size


def test_find_similar_words():
    sentences: list[list[str]] = [["word1", "word2", "word3"], ["word4", "word5"]]
    model: Word2Vec = Word2Vec(sentences, vector_size=100, window=5, min_count=1, workers=4)
    similar_tuples: list[tuple[str, float]] = model.wv.most_similar('word1')
    print(similar_tuples)
    similar_words: list[str] = [word for word, _ in similar_tuples]
    assert similar_words == ['word3', 'word4', 'word5', 'word2']


def test_save_load_model():
    word1: str = "word1"
    sentences: list[list[str]] = [[word1, "word2", "word3"], ["word4", "word5"]]
    model_exp: Word2Vec = Word2Vec(sentences, vector_size=100, window=5, min_count=1, workers=4)
    _, file = tempfile.mkstemp(suffix=".model")
    assert os.path.getsize(file) == 0
    model_exp.save(file)
    print(f"file={file}, size = {os.path.getsize(file)}")
    assert os.path.getsize(file) > 0
    model_act: Word2Vec = Word2Vec.load(file)
    word_act: Type[ndarray] = model_act.wv[word1]
    word_exp: Type[ndarray] = model_exp.wv[word1]
    assert_array_equal(word_act, word_exp)
