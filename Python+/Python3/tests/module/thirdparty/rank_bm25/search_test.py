from numpy import dtype, float64, ndarray
from rank_bm25 import BM25Okapi


def test_search():
    corpus: list[str] = [
        "Hello there good man from London",
        "It is quite windy in London",
        "How is the weather today?"
    ]
    tokenized_corpus: list[list[str]] = [doc.split(" ") for doc in corpus]
    bm25: BM25Okapi = BM25Okapi(tokenized_corpus)

    query: str = "windy London"
    tokenized_query: list[str] = query.split(" ")
    doc_scores: ndarray[tuple[int], dtype[float64]] = bm25.get_scores(tokenized_query)

    top_docs: list[str] = bm25.get_top_n(tokenized_query, corpus, n=2)
    print(top_docs)

    act_ids: list[int] = sorted(range(len(doc_scores)), key=lambda i: doc_scores[i], reverse=True)
    assert act_ids == [1, 0, 2]


# Rare words have high rank
def test_weight():
    corpus: list[str] = [
        "Hello London bye London",
        "Where London is London as London",
        "No London windy today",
        "Good weather today",
    ]
    tokenized_corpus: list[list[str]] = [doc.split(" ") for doc in corpus]
    bm25: BM25Okapi = BM25Okapi(tokenized_corpus)

    query: str = "windy London"
    tokenized_query: list[str] = query.split(" ")
    doc_scores: ndarray[tuple[int], dtype[float64]] = bm25.get_scores(tokenized_query)

    top_docs: list[str] = bm25.get_top_n(tokenized_query, corpus, n=2)
    print(top_docs)

    act_ids: list[int] = sorted(range(len(doc_scores)), key=lambda i: doc_scores[i], reverse=True)
    assert act_ids == [2, 1, 0, 3]
