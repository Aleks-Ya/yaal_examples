from qdrant_client import QdrantClient
from qdrant_client.http.models import Document, ScoredPoint, VectorParams, Distance


# Source: https://github.com/qdrant/qdrant-client
def test_example(client: QdrantClient):
    model_name: str = "sentence-transformers/all-MiniLM-L6-v2"
    payload: list[dict[str, str]] = [
        {"document": "Qdrant has Langchain integrations", "source": "Langchain-docs", },
        {"document": "Qdrant also has Llama Index integrations", "source": "LlamaIndex-docs"},
    ]
    docs: list[Document] = [Document(text=data["document"], model=model_name) for data in payload]
    ids: list[int] = [42, 2]

    collection: str = "demo_collection"
    client.create_collection(
        collection,
        vectors_config=VectorParams(size=client.get_embedding_size(model_name), distance=Distance.COSINE)
    )

    client.upload_collection(collection_name=collection, vectors=docs, ids=ids, payload=payload)

    search_result: list[ScoredPoint] = client.query_points(
        collection_name=collection,
        query=Document(text="This is a query document", model=model_name)
    ).points
    print(search_result)
    assert len(search_result) == 2
