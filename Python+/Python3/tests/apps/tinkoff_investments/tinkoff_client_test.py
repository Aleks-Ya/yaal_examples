import pytest

from apps.tinkoff_investments.tinkoff_client import TinkoffClient, PositionDetails


@pytest.fixture
def client() -> TinkoffClient:
    return TinkoffClient()


@pytest.mark.integration
def test_get_portfolio_positions(client: TinkoffClient):
    positions: list[PositionDetails] = client.get_portfolio_positions()
    for position in positions:
        print(position)
    assert positions


@pytest.mark.integration
def test_group_positions_by_currency(client: TinkoffClient):
    positions_by_currency: dict[str, list[PositionDetails]] = client.group_positions_by_currency()
    for currency, positions in positions_by_currency.items():
        print()
        print(f"{currency}")
        for position in positions:
            print(position)
