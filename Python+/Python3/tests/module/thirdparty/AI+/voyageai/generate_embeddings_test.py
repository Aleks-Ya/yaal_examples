from voyageai import Client
from voyageai.object import EmbeddingsObject


def test_generate_embeddings(client: Client):
    embeddings_object: EmbeddingsObject = client.embed(["cake"])
    embeddings: list[list[float | int]] | list[list[int]] = embeddings_object.embeddings
    print(embeddings)
