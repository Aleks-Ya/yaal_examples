from tinkoff.invest import InstrumentIdType, \
    InstrumentResponse, CurrenciesResponse, Currency, CurrencyResponse, GetAccountsResponse, PortfolioResponse
from tinkoff.invest.services import Services


def test_get_instrument_by_uid(client: Services):
    instrument: InstrumentResponse = client.instruments.get_instrument_by(
        id_type=InstrumentIdType.INSTRUMENT_ID_TYPE_UID,
        id="70c34f4d-ef8f-408a-8d2a-4098f0656d4f")
    print(instrument)


def test_get_instrument_by_figi(client: Services):
    instrument: InstrumentResponse = client.instruments.get_instrument_by(
        id_type=InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI,
        id="IE00B8XB7377")
    print(instrument)


def test_get_currencies(client: Services):
    currenciesResponse: CurrenciesResponse = client.instruments.currencies()
    currencies: list[Currency] = currenciesResponse.instruments
    for currency in currencies:
        print(currency)


def test_get_currency_by_figi(client: Services):
    currencyResponse: CurrencyResponse = client.instruments.currency_by(
        id_type=InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI,
        id="BBG000VHQTD1")
    currency: Currency = currencyResponse.instrument
    print(currency)


# NOT WORK
def test_get_currency_by_position_uid(client: Services):
    accounts: GetAccountsResponse = client.users.get_accounts()
    account_id: str = accounts.accounts[0].id
    portfolio: PortfolioResponse = client.operations.get_portfolio(account_id=account_id)
    position_uid: str = portfolio.positions[0].position_uid
    currencyResponse: CurrencyResponse = client.instruments.currency_by(
        id_type=InstrumentIdType.INSTRUMENT_ID_TYPE_POSITION_UID,
        id=position_uid)
    currency: Currency = currencyResponse.instrument
    print(currency)
