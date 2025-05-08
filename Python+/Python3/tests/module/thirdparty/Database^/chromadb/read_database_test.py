import chromadb
import pytest
from chromadb import ClientAPI, QueryResult, GetResult
from chromadb.api.models.Collection import Collection
from chromadb.api.types import IncludeEnum
from numpy import ndarray

doc1: str = "This is a document about pineapple"
doc2: str = "This is a document about oranges"
doc3: str = "Another information provided"
id1: str = "id1"
id2: str = "id2"
id3: str = "id3"


@pytest.fixture
def collection() -> Collection:
    chroma_client: ClientAPI = chromadb.Client()
    collection: Collection = chroma_client.get_or_create_collection(name="my_collection")
    collection.upsert(
        documents=[doc1, doc2, doc3],
        ids=[id1, id2, id3]
    )
    return collection


def test_query(collection: Collection):
    results: QueryResult = collection.query(
        query_texts=["This is a query document about florida"],  # Chroma will embed this for you
        n_results=2  # how many results to return
    )

    for k, v in results.items():
        print(k, v)

    found_documents: list[list[str]] = results["documents"]
    assert found_documents == [[doc2, doc1]]


def test_get_document(collection: Collection):
    result: GetResult = collection.get(id1)
    document: list[str] = result["documents"]
    assert document == [doc1]


def test_get_embeddings(collection: Collection):
    result: GetResult = collection.get(id1, include=[IncludeEnum.embeddings])
    embeddings: ndarray = result["embeddings"]
    assert embeddings is not None
    print(embeddings)
