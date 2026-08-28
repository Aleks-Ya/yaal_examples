import pytest
from qdrant_client import QdrantClient


@pytest.fixture
def client() -> QdrantClient:
    return QdrantClient(":memory:")
