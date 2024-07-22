import pytest
from _pytest.fixtures import FixtureRequest


@pytest.fixture
def app_name() -> str:
    return "my-app"


def test_inject_fixture(app_name: str):
    assert app_name == "my-app"


def test_get_fixture_from_non_test_method(request: FixtureRequest):
    upper_case_app_name: str = use_fixture(request)
    assert upper_case_app_name == "MY-APP"


def use_fixture(request: FixtureRequest) -> str:
    name: str = request.getfixturevalue("app_name")
    return name.upper()
