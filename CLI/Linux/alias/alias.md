# alias CLI

## List
List all aliases: `alias`
List one alias: `alias backup_docs_vault`
List multiple aliases: `alias backup_docs_vault backup_joplin`

## Create
Create one-line alias:
```shell
alias mtree='mvn dependency:tree -DoutputFile=target/tree.txt'
```
Create multi-line alias:
```shell
alias vtb-report='
(VERSION=2.13; eval java -jar \
	-Dreport.source.dir=/home/aleks/Vault/Finances/VTB_Broker/VTB_Broker_Report/Origin \
	-Dexporter.dealxls.output.dir=/home/aleks/Vault/Finances/VTB_Broker/VTB_Broker_Report/Generated \
	/home/aleks/.m2/repository/ru/yaal/vtb/vtb-broker-report/${VERSION}/vtb-broker-report-${VERSION}.jar)'
```

## Delete
Remove alias: see `CLI/Linux/unalias.md`
