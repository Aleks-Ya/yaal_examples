from tabulate import tabulate


def test_tabulate():
    data: list[list[str]] = [
        ["Item", "Price"],
        ["Pizza", "850"],
        ["Burger", "500"],
        ["Salad", "475"],
        ["Pasta", "725"]
    ]
    table: str = tabulate(data)
    print(table)
