# parquet-tools CLI
 
Install: `pip3 install parquet-tools`
 
Help: `parquet-tools -h`
Help about a command: `parquet-tools csv -h`
 
Show common info about a Parquet file: `parquet-tools inspect /tmp/my.parquet`
Show detailed info about a Parquet file: `parquet-tools inspect --detail /tmp/my.parquet`
Show content of a Parquet file: 
1. All records: `parquet-tools show /tmp/my.parquet`
2. N records: `parquet-tools show -n 5 /tmp/my.parquet`
Show content of a Parquet file as CSV: `parquet-tools csv /tmp/my.parquet`
