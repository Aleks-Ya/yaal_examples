# parquet-tools CLI

GitHub: https://github.com/hangxie/parquet-tools

Install: `brew install go-parquet-tools`

## Commands

### Help
Help: `parquet-tools -h`
Help about a command: `parquet-tools show -h`
Install shell completions: `parquet-tools shell-completions`

### Schema
Show schema: `parquet-tools schema -f raw my.parquet | jq`

### Content
Show content of a Parquet file: 
1. All records: `parquet-tools show my.parquet`
2. N records: `parquet-tools show -n 5 my.parquet`
Show given columns: `parquet-tools show -c id my.parquet`
Show content of a Parquet file as CSV (N records): `parquet-tools csv -n 5 my.parquet`

### Inspect
Show row count: `parquet-tools row-count my.parquet`
Show common info about a Parquet file: `parquet-tools inspect my.parquet`
Show detailed info about a Parquet file: `parquet-tools inspect --detail /my.parquet`
