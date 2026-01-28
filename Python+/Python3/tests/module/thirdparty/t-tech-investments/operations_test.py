from t_tech.invest import PortfolioResponse, PortfolioPosition, MoneyValue, Quotation, InstrumentIdType, \
    InstrumentResponse
from t_tech.invest.services import Services


def test_get_portfolio_positions(client: Services):
    account_id: str = client.users.get_accounts()[0].id
    portfolio: PortfolioResponse = client.operations.get_portfolio(account_id=account_id)
    positions: list[PortfolioPosition] = portfolio.positions
    for position in positions:
        print(position)
        average_price: MoneyValue = position.average_position_price
        quantity: Quotation = position.quantity
        position_sum: int = average_price.units * quantity.units
        print(position.instrument_uid, position_sum)


def test_get_portfolio_positions_sum(client: Services):
    account_id: str = client.users.get_accounts()[0].id
    portfolio: PortfolioResponse = client.operations.get_portfolio(account_id=account_id)
    positions: list[PortfolioPosition] = portfolio.positions
    for position in positions:
        instrument: InstrumentResponse = client.instruments.get_instrument_by(
            id_type=InstrumentIdType.INSTRUMENT_ID_TYPE_POSITION_UID,
            id=position.position_uid)
        price: MoneyValue = position.current_price
        quantity: Quotation = position.quantity
        position_sum: int = price.units * quantity.units
        print(instrument.instrument.name, price, instrument.instrument.currency, quantity, position_sum)
