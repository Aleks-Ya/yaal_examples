from numpy import ndarray
from sentence_transformers import SentenceTransformer


# https://huggingface.co/sentence-transformers/all-distilroberta-v1
def test_use_model():
    model: SentenceTransformer = SentenceTransformer('sentence-transformers/all-distilroberta-v1')
    sentences: list[str] = ["This is an example sentence", "Each sentence is converted"]
    embeddings: ndarray = model.encode(sentences)
    print(embeddings)
