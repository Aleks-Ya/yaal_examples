# parquet-cli CLI

## Info
GitHub: https://github.com/chhantyal/parquet-cli
PyPi: https://pypi.org/project/parquet-cli/

## Install
Latest version: `pipx install parquet-cli`
Latest version (externally-managed-environment error): `pip install -U parquet-cli`

## Commands
Help: `parq -h`
Show metadata: `parq my.parquet`
Display first N rows: `parq --head 10 my.parquet`
Display last N rows: `parq --tail 10 my.parquet`
Show schema: `parq my.parquet --schema`
Count rows: `parq my.parquet --count`
