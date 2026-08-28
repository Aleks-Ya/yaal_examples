from qdrant_client import QdrantClient
from qdrant_client.http.models import ClusterStatusOneOf, ClusterStatusOneOf1


def test_collection_exists():
    client: QdrantClient = QdrantClient(host="localhost", port=6333)
    exists: bool = client.collection_exists('abc')
    assert not exists


def test_cluster_status():
    client: QdrantClient = QdrantClient(host="localhost", port=6333)
    status: ClusterStatusOneOf | ClusterStatusOneOf1 = client.cluster_status()
    assert status.status == "disabled"
