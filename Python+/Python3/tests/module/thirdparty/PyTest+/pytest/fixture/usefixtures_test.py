import pytest


@pytest.fixture
def app_name() -> str:
    return "editor"


@pytest.fixture
def app_version() -> str:
    return "1.0"


@pytest.fixture
@pytest.mark.usefixtures("app_name", "app_version")
def app_description(app_name: str, app_version: str) -> str:
    return f"{app_name}-{app_version}"


def test_inject_fixture(app_description: str):
    assert app_description == "editor-1.0"
