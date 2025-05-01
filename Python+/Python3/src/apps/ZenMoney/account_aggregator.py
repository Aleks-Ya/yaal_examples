from apps.ZenMoney.zen_money_client import Account, Company, Instrument


class InstrumentAccounts:
    def __init__(self, instrument: Instrument, accounts: list[Account]):
        self.__instrument = instrument
        self.__accounts = accounts
        for account in accounts:
            if account.instrument != instrument:
                raise ValueError(f"Account {account} has instrument {account.instrument} != {instrument}")

    def get_instrument(self) -> Instrument:
        return self.__instrument

    def get_sum_excel_formula(self) -> str:
        balances: list[str] = [str(account.balance) for account in self.__accounts]
        formula: str = '=' + '+'.join(balances)
        return formula


class AccountAggregator:
    def __init__(self, accounts: list[Account]):
        self.__accounts = accounts

    def group_by_company_and_instrument(self) -> dict[Company, list[InstrumentAccounts]]:
        by_company: dict[Company, list[Account]] = self.group_by_company()
        result: dict[Company, list[InstrumentAccounts]] = {}
        for company, accounts in by_company.items():
            result[company] = self.__group_by_instrument(accounts)
        return dict(sorted(result.items(), key=lambda item: item[0].title if item[0] is not None else ''))

    def group_by_company(self) -> dict[Company, list[Account]]:
        result: dict[Company, list[Account]] = {}
        for account in self.__accounts:
            company: Company = account.company
            if company not in result:
                result[company] = []
            result[company].append(account)
        return result

    @staticmethod
    def __group_by_instrument(accounts: list[Account]) -> list[InstrumentAccounts]:
        instrument_to_accounts: dict[Instrument, list[Account]] = {}
        for account in accounts:
            if account.instrument not in instrument_to_accounts:
                instrument_to_accounts[account.instrument] = []
            instrument_to_accounts[account.instrument].append(account)
        result: list[InstrumentAccounts] = []
        for instrument, accounts in instrument_to_accounts.items():
            result.append(InstrumentAccounts(instrument, accounts))
        return result
