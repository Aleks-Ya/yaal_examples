import pytest
from pydiscourse import DiscourseClient


@pytest.fixture
def anonymous_anki_forum_client() -> DiscourseClient:
    return DiscourseClient(
        host="https://forums.ankiweb.net",
        api_username=None,
        api_key=None
    )
