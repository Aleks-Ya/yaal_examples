from apps.TinkoffInvestemnts.tinkoff_client import TinkoffClient, PositionDetails

client: TinkoffClient = TinkoffClient()
positions_by_currency: dict[str, list[PositionDetails]] = client.group_positions_by_currency()
for currency, positions in positions_by_currency.items():
    print()
    print(f"{currency}")
    for position in positions:
        print(position)
    position_sums: list[str] = [str(position.position_sum) for position in positions]
    excel_sum: str = "=" + "+".join(position_sums)
    print(f"Excel formula: {excel_sum}")
    print(f"Number of positions: {len(positions)}")
