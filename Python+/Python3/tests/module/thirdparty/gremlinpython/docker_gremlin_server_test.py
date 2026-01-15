# Run Gremlin server: Database+/Gremlin+/GremlinDocker/GremlinDocker.md

from gremlin_python.driver.client import Client


def test_connection_to_localhost_8182():
    client: Client = Client('ws://localhost:8182/gremlin', 'g')
    try:
        result: list[int] = client.submit('1+1').all().result()
        assert result == [2]
    finally:
        client.close()
