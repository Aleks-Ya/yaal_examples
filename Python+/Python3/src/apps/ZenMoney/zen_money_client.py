from dataclasses import dataclass
from enum import Enum
from pathlib import Path
import time
import json
from typing import NewType, Any

import requests
from requests import Response

InstrumentId = NewType("InstrumentId", int)
CompanyId = NewType("CompanyId", int)


@dataclass(frozen=True)
class Instrument:
    id: InstrumentId
    title: str
    shortTitle: str
    symbol: str
    rate: float


@dataclass(frozen=True)
class Company:
    id: CompanyId
    title: str
    fullTitle: str


class AccountType(Enum):
    CASH = "cash"
    CCARD = "ccard"
    CHECKING = "checking"
    LOAN = "loan"
    DEPOSIT = "deposit"
    EMONEY = "emoney"
    DEBT = "debt"


@dataclass(frozen=True)
class Account:
    id: str
    title: str
    type: AccountType
    instrument: Instrument
    company: Company
    balance: float


class ZenMoneyClient:
    def __init__(self):
        self.__url: str = "https://api.zenmoney.ru/v8/diff"
        token_file: Path = Path.home() / ".zenmoney" / "token.txt"
        token: str = token_file.read_text().strip()
        self.__headers: dict[str, str] = {
            'Content-Type': 'application/json',
            'Authorization': f'Bearer {token}'
        }

    def get_instruments(self) -> dict[InstrumentId, Instrument]:
        response_json: dict[str, Any] = self.__request_json(["instrument"])
        return self.__parse_instruments(response_json)

    def get_companies(self) -> dict[CompanyId, Company]:
        response_json: dict[str, Any] = self.__request_json(["company"])
        return self.__parse_companies(response_json)

    def get_accounts(self) -> list[Account]:
        response_json: dict[str, Any] = self.__request_json(["account", "instrument", "company"])
        instruments: dict[InstrumentId, Instrument] = self.__parse_instruments(response_json)
        companies: dict[CompanyId, Company] = self.__parse_companies(response_json)
        accounts_json: list[dict[str, Any]] = response_json["account"]
        accounts: list[Account] = []
        for account_json in accounts_json:
            instrument_id: InstrumentId = InstrumentId(account_json["instrument"])
            instrument: Instrument = instruments[instrument_id] if instrument_id in instruments else None
            company_id: CompanyId = CompanyId(account_json["company"])
            company: Company = companies[company_id] if company_id in companies else None
            account_type: AccountType = AccountType(account_json["type"]) if "type" in account_json else None
            account: Account = Account(account_json["id"],
                                       account_json["title"],
                                       account_type,
                                       instrument,
                                       company,
                                       account_json["balance"])
            accounts.append(account)

        return accounts

    @staticmethod
    def __parse_instruments(response_json: dict[str, Any]) -> dict[InstrumentId, Instrument]:
        instruments_json: list[dict[str, Any]] = response_json["instrument"]
        instruments: dict[InstrumentId, Instrument] = {}
        for instrument_json in instruments_json:
            instrument_id: InstrumentId = InstrumentId(instrument_json["id"])
            instrument: Instrument = Instrument(instrument_id,
                                                instrument_json["title"],
                                                instrument_json["shortTitle"],
                                                instrument_json["symbol"],
                                                instrument_json["rate"])
            instruments[instrument_id] = instrument
        return instruments

    @staticmethod
    def __parse_companies(response_json: dict[str, Any]) -> dict[CompanyId, Company]:
        companies_json: list[dict[str, Any]] = response_json["company"]
        companies: dict[CompanyId, Company] = {}
        for company_json in companies_json:
            company_id: CompanyId = CompanyId(company_json["id"])
            company: Company = Company(company_id,
                                       company_json["title"],
                                       company_json["fullTitle"])
            companies[company_id] = company
        return companies

    def __request_json(self, entities: list[str]) -> dict[str, Any]:
        current_timestamp: int = int(time.time())
        payload: str = json.dumps({
            "currentClientTimestamp": current_timestamp,
            "lastServerTimestamp": current_timestamp,
            "forceFetch": entities
        })
        response: Response = requests.request("POST", self.__url, headers=self.__headers, data=payload)
        return response.json()
