from apps.ZenMoney.account_aggregator import AccountAggregator, InstrumentAccounts
from apps.ZenMoney.zen_money_client import ZenMoneyClient, Account, Company


def test_group_by_company_and_instrument():
    client: ZenMoneyClient = ZenMoneyClient()
    accounts: list[Account] = client.get_accounts()
    aggregator: AccountAggregator = AccountAggregator(accounts)

    company_to_instrument_to_account: dict[
        Company, list[InstrumentAccounts]] = aggregator.group_by_company_and_instrument()
    for company, instrument_accounts_list in company_to_instrument_to_account.items():
        print()
        title: str = company.title if company is not None else "Unknown"
        print(title)
        for instrument_accounts in instrument_accounts_list:
            print(f"    {instrument_accounts.get_instrument().shortTitle}: {instrument_accounts.get_sum_excel_formula()}")
