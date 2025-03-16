from numpy import ndarray
from sentence_transformers import SentenceTransformer


# https://huggingface.co/sentence-transformers/paraphrase-mpnet-base-v2
def test_use_model():
    sentences: list[str] = ["This is an example sentence", "Each sentence is converted"]
    model: SentenceTransformer = SentenceTransformer('sentence-transformers/paraphrase-mpnet-base-v2')
    embeddings: ndarray = model.encode(sentences)
    print(embeddings)
