from numpy import ndarray
from sentence_transformers import CrossEncoder


# https://huggingface.co/cross-encoder/ms-marco-MiniLM-L6-v2
def test_use_model():
    model: CrossEncoder = CrossEncoder('cross-encoder/ms-marco-MiniLM-L6-v2')
    scores: ndarray = model.predict([
        ("How many people live in Berlin?",
         "Berlin had a population of 3,520,031 registered inhabitants in an area of 891.82 square kilometers."),
        ("How many people live in Berlin?", "Berlin is well known for its museums."),
    ])
    print(scores)
    # [ 8.607138 -4.320078]
