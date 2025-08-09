# AWS RDS CLI

List DB clusters: `aws rds describe-db-clusters`
List DB instances: `aws rds describe-db-instances`

## SQL queries
List tables in an Aurora DB:
```shell
aws rds-data execute-statement \
	--resource-arn arn:aws:rds:ap-south-1:182399681840:cluster:cmtr-o6t6f8mk-aurora-db \
	--secret-arn arn:aws:secretsmanager:ap-south-1:182399681840:secret:cmtr-o6t6f8mk-aurora-db_secret-zudqS6 \
	--database event_log \
	--sql "SHOW TABLES;"
```