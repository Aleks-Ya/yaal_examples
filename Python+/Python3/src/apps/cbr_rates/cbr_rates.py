from dataclasses import dataclass
from datetime import timedelta, date
from decimal import Decimal
from typing import Optional

import requests
from bs4 import BeautifulSoup, Tag
from requests import Response


@dataclass
class Currency:
    code: str
    rate: Decimal
    unit: int

    def formatted(self) -> str:
        value: Decimal = self.rate / self.unit
        s: str = f"{value:f}"
        return s


last_day_prev_month: date = date.today().replace(day=1) - timedelta(days=1)
date: str = last_day_prev_month.strftime("%d.%m.%Y")
print(f"Date: {date}\n")
url: str = f"https://cbr.ru/currency_base/daily?UniDbQuery.Posted=True&UniDbQuery.To={date}"
response: Response = requests.get(url)
if response.status_code != 200:
    raise Exception(f"Invalid response code: {response}")
soup: BeautifulSoup = BeautifulSoup(response.text, 'html.parser')
data_table: Tag = soup.find("table", {"class": "data"})
table_rows: list[Tag] = data_table.find_all("tr")
table_rows_no_header: list[Tag] = table_rows[1:]
table_rows_cells: list[list[Tag]] = [row.find_all("td") for row in table_rows_no_header]
currency_rates: dict[str, Currency] = {row[1].text: Currency(
    row[1].text,
    Decimal(row[4].text.replace(",", ".")),
    int(row[2].text))
    for row in table_rows_cells}
needed_currency_codes: list[str] = ["USD", "EUR", "PHP", "EGP", "TRY", "THB", "GEL", "IDR", "KHR"]
needed_currencies: dict[str, Optional[Currency]] = {code: currency_rates[code] if code in currency_rates else None
                                                    for code in needed_currency_codes}
for code, currency in needed_currencies.items():
    print(f"""{code}: {currency}: "{currency.formatted() if currency else '-'}" """)
libre_office_calc_row: str = "\t".join([currency.formatted() if currency else ""
                                        for code, currency in needed_currencies.items()])
print(f"\nLibreOffice row:\n{libre_office_calc_row}")
