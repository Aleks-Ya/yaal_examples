from qdrant_client import QdrantClient


def test_in_memory():
    qdrant: QdrantClient = QdrantClient(":memory:")
    exists: bool = qdrant.collection_exists('abc')
    assert not exists
