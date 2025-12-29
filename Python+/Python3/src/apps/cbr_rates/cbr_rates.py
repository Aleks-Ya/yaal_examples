from dataclasses import dataclass
from datetime import timedelta, date
from decimal import Decimal
from typing import Optional, NewType

import requests
from bs4 import BeautifulSoup, Tag
from requests import Response

CurrencyCode = NewType("CurrencyCode", str)


@dataclass
class Currency:
    code: CurrencyCode
    rate: Decimal
    unit: int

    def formatted(self) -> str:
        value: Decimal = self.rate / self.unit
        s: str = f"{value:f}"
        return s


def load_cbr_rates(day: date) -> dict[CurrencyCode, Currency]:
    date_str: str = day.strftime("%d.%m.%Y")
    print(f"Date: {date_str}\n")
    url: str = f"https://cbr.ru/currency_base/daily?UniDbQuery.Posted=True&UniDbQuery.To={date_str}"
    response: Response = requests.get(url)
    if response.status_code != 200:
        raise Exception(f"Invalid response code: {response}")
    soup: BeautifulSoup = BeautifulSoup(response.text, 'html.parser')
    data_table: Tag = soup.find("table", {"class": "data"})
    table_rows: list[Tag] = data_table.find_all("tr")
    table_rows_no_header: list[Tag] = table_rows[1:]
    table_rows_cells: list[list[Tag]] = [row.find_all("td") for row in table_rows_no_header]
    rates: dict[CurrencyCode, Currency] = {CurrencyCode(row[1].text): Currency(
        CurrencyCode(row[1].text),
        Decimal(row[4].text.replace(",", ".")),
        int(row[2].text))
        for row in table_rows_cells}
    return rates


def load_wise_rates(code: CurrencyCode, day: date) -> dict[CurrencyCode, Currency]:
    date_str: str = day.strftime("%d-%m-%Y")
    url: str = f"https://wise.com/us/currency-converter/{code.lower()}-to-rub-rate/history/{date_str}"
    response: Response = requests.get(url)
    if response.status_code != 200:
        raise Exception(f"Invalid response code: {response}")
    soup: BeautifulSoup = BeautifulSoup(response.text, 'html.parser')
    rate_tag: Tag = soup.find("input", {"id": "target-input"})
    rate_str: str = rate_tag.get("value").replace(',', '')
    rate: Decimal = Decimal(rate_str) / 1000
    return {code: Currency(code, rate, 1)}


today: date = date.today()
# today: date = date(2025, 12, 1)
last_day_prev_month: date = today.replace(day=1) - timedelta(days=1)
currency_rates: dict[CurrencyCode, Currency] = load_cbr_rates(last_day_prev_month)
php_rate: dict[CurrencyCode, Currency] = load_wise_rates(CurrencyCode("PHP"), last_day_prev_month)
khr_rate: dict[CurrencyCode, Currency] = load_wise_rates(CurrencyCode("KHR"), last_day_prev_month)
currency_rates.update(php_rate)
currency_rates.update(khr_rate)
needed_currency_codes: list[CurrencyCode] = [CurrencyCode(code) for code in
                                             ["USD", "EUR", "PHP", "EGP", "TRY", "THB", "GEL", "IDR", "KHR"]]
needed_currencies: dict[CurrencyCode, Optional[Currency]] = {
    code: currency_rates[code] if code in currency_rates else None
    for code in needed_currency_codes}
for code, currency in needed_currencies.items():
    print(f"""{code}: {currency}: "{currency.formatted() if currency else '-'}" """)
libre_office_calc_row: str = "\t".join([currency.formatted() if currency else ""
                                        for code, currency in needed_currencies.items()])
print(f"\nLibreOffice row:\n{libre_office_calc_row}")
