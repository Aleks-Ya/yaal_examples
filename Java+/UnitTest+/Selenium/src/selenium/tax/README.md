# NDFL-3 dividend filler

Automatically adds foreign company dividend incomes to NDFL-3 declaration.

## Configuration

File: `~/.nalog/nalogru.properties`
Content:

```properties
login.url=https://lkfl2.nalog.ru/lkfl/login
login.username=
login.password=
declaration.url=https://lkfl2.nalog.ru/lkfl/situations/3NDFL?cardId=173112658&step=sheetA
```

## Tax data

Resource: `selenium/tax/tax_sources.tsv`

## Run

Main class: `selenium.tax.Ndfl3DividendFillerMain`