from qdrant_client import QdrantClient
from qdrant_client.http.models import Document, ScoredPoint, VectorParams, Distance, QueryResponse


def test_example(client: QdrantClient):
    model_name: str = "sentence-transformers/all-MiniLM-L6-v2"
    payload: list[dict[str, str]] = [
        {"document": "A computer store sells Apple and Samsung products"},
        {"document": "A farmer sells apples"},
        {"document": "Apple sells iPhones"},
    ]
    docs: list[Document] = [Document(text=data["document"], model=model_name) for data in payload]
    ids: list[int] = [1, 2, 3]

    collection: str = "demo_collection"
    client.create_collection(
        collection,
        vectors_config=VectorParams(size=client.get_embedding_size(model_name), distance=Distance.COSINE)
    )

    client.upload_collection(collection_name=collection, vectors=docs, ids=ids, payload=payload)

    response: QueryResponse = client.query_points(
        collection_name=collection, query=Document(text="fruit", model=model_name)
    )
    search_result: list[ScoredPoint] = response.points
    act_ids: list[int] = [point.id for point in search_result]
    assert act_ids == [2, 1, 3]
