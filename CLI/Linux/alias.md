# alias CLI

Create one-line alias:
```bash
alias mtree='mvn dependency:tree -DoutputFile=target/tree.txt'
```
Create multi-line alias:
```bash
alias vtb-report='
(VERSION=2.13; eval java -jar \
	-Dreport.source.dir=/home/aleks/Vault/Finances/VTB_Broker/VTB_Broker_Report/Origin \
	-Dexporter.dealxls.output.dir=/home/aleks/Vault/Finances/VTB_Broker/VTB_Broker_Report/Generated \
	/home/aleks/.m2/repository/ru/yaal/vtb/vtb-broker-report/${VERSION}/vtb-broker-report-${VERSION}.jar)'
```

