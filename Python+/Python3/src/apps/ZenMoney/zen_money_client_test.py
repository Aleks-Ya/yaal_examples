import pytest

from apps.ZenMoney.zen_money_client import ZenMoneyClient, Instrument, Account, InstrumentId, CompanyId, Company


@pytest.fixture
def client() -> ZenMoneyClient:
    return ZenMoneyClient()


# Instrument(id=1, title='Доллар США', shortTitle='USD', symbol='$', rate=81.4933)
def test_get_instruments(client: ZenMoneyClient):
    instruments: dict[InstrumentId, Instrument] = client.get_instruments()
    print(f"Instruments count: {len(instruments)}")
    for instrument in instruments.values():
        print(instrument)


# Company(id=3, title='Альфа-Банк', fullTitle=None)
def test_get_companies(client: ZenMoneyClient):
    companies: dict[CompanyId, Company] = client.get_companies()
    print(f"Companies count: {len(companies)}")
    for company in companies.values():
        print(company)


# Account(id='0be0727d-a122-4817-84e9-000000000000', title='Ziraat USD', type=CHECKING,
# instrument=Instrument(id=1, title='Доллар США', shortTitle='USD', symbol='$', rate=81.4933),
# company=Company(id=15849, title='Ziraat Bankasi', fullTitle=None),
# balance=100.10)
def test_get_accounts(client: ZenMoneyClient):
    accounts: list[Account] = client.get_accounts()
    print(f"Accounts count: {len(accounts)}")
    for account in accounts:
        print(account)
