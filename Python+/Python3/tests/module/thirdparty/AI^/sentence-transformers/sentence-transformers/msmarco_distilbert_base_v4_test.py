from numpy import ndarray
from sentence_transformers import SentenceTransformer


# https://huggingface.co/sentence-transformers/msmarco-distilbert-base-v4
def test_use_model():
    sentences: list[str] = ["This is an example sentence", "Each sentence is converted"]
    model: SentenceTransformer = SentenceTransformer('sentence-transformers/msmarco-distilbert-base-v4')
    embeddings: ndarray = model.encode(sentences)
    embedding_list: list[list[int]] = embeddings.tolist()
    assert len(embedding_list[0]) == 768
    assert len(embedding_list[1]) == 768
    print(embedding_list)
