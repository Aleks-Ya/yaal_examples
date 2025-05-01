import time

from zenmoney import ZenmoneyRequest
from zenmoney.models import Diff, Instrument, Company, User, Account


def __diff(client: ZenmoneyRequest, entity: str) -> Diff:
    current_timestamp: int = int(time.time())
    params: Diff = Diff(
        server_timestamp=current_timestamp,
        current_client_timestamp=current_timestamp,
        force_fetch=[entity]
    )
    return client.diff(params=params)


def test_list_instruments(client: ZenmoneyRequest):
    diff: Diff = __diff(client, "instrument")
    instruments: list[Instrument] = diff.instrument
    assert len(instruments) > 0
    for instrument in instruments:
        print(instrument)


def test_list_companies(client: ZenmoneyRequest):
    diff: Diff = __diff(client, "company")
    companies: list[Company] = diff.company
    assert len(companies) > 0
    for company in companies:
        print(company)


def test_list_users(client: ZenmoneyRequest):
    diff: Diff = __diff(client, "user")
    users: list[User] = diff.user
    assert len(users) > 0
    for user in users:
        print(user)


def test_list_account(client: ZenmoneyRequest):
    diff: Diff = __diff(client, "account")
    accounts: list[Account] = diff.account
    assert len(accounts) > 0
    for account in accounts:
        print(account)
