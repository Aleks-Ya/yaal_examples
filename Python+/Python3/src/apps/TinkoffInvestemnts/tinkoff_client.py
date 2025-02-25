from dataclasses import dataclass
from pathlib import Path

from tinkoff.invest import Client, GetAccountsResponse, PortfolioResponse, PortfolioPosition, InstrumentResponse, \
    InstrumentIdType, MoneyValue, Quotation, Instrument
from tinkoff.invest.services import Services


@dataclass
class PositionDetails:
    name: str
    quantity: int
    currency: str
    position_sum: float


class TinkoffClient:
    def __init__(self):
        token: str = (Path.home() / ".tinkoff" / "token.txt").read_text().strip()
        self.__client: Client = Client(token)

    def get_portfolio_positions(self) -> list[PositionDetails]:
        result: list[PositionDetails] = []
        with self.__client as client:
            account_id: str = self.__get_account_id(client)
            portfolio: PortfolioResponse = client.operations.get_portfolio(account_id=account_id)
            positions: list[PortfolioPosition] = portfolio.positions
            for position in positions:
                instrument: Instrument = self.__get_instrument(client, position)
                price: MoneyValue = position.current_price
                quantity: Quotation = position.quantity
                quantity_units: int = quantity.units
                price_units: int = price.units
                price_currency: str = price.currency
                if self.__is_usd(instrument):
                    price_currency: str = "usd"
                    price_units: int = 1
                position_sum: float = price_units * quantity_units
                details: PositionDetails = PositionDetails(
                    instrument.name, quantity_units, price_currency, position_sum)
                result.append(details)
        return result

    @staticmethod
    def __is_usd(instrument: Instrument) -> bool:
        return instrument.figi == "BBG0013HGFT4"

    def group_positions_by_currency(self) -> dict[str, list[PositionDetails]]:
        result: dict[str, list[PositionDetails]] = {}
        for position in self.get_portfolio_positions():
            currency: str = position.currency
            if currency not in result:
                result[currency] = []
            result[currency].append(position)
        return result

    @staticmethod
    def __get_instrument(client, position) -> Instrument:
        instrument: InstrumentResponse = client.instruments.get_instrument_by(
            id_type=InstrumentIdType.INSTRUMENT_ID_TYPE_POSITION_UID,
            id=position.position_uid)
        return instrument.instrument

    @staticmethod
    def __get_account_id(client: Services) -> str:
        accounts: GetAccountsResponse = client.users.get_accounts()
        if (len(accounts.accounts)) != 1:
            raise RuntimeError(f"Invalid number of accounts: {accounts}")
        return accounts.accounts[0].id
