import pytest

from apps.ZenMoney.zen_money_client import ZenMoneyClient, Instrument, Account, InstrumentId, CompanyId, Company


@pytest.fixture
def client() -> ZenMoneyClient:
    return ZenMoneyClient()


def test_get_instruments(client: ZenMoneyClient):
    instruments: dict[InstrumentId, Instrument] = client.get_instruments()
    for instrument in instruments.values():
        print(instrument)


def test_get_companies(client: ZenMoneyClient):
    companies: dict[CompanyId, Company] = client.get_companies()
    for company in companies.values():
        print(company)


def test_get_accounts(client: ZenMoneyClient):
    accounts: list[Account] = client.get_accounts()
    for account in accounts:
        print(account)
