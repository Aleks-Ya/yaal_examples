from tinkoff.invest.services import Services


def test_get_accounts(client: Services):
    print(client.users.get_accounts())
