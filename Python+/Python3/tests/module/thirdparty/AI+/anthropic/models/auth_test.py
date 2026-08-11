from anthropic import Anthropic


def test_validate_authentication(client: Anthropic):
    client.models.list(limit=1)
