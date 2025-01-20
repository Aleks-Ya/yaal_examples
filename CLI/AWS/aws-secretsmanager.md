# AWS Secrets Manager

List secrets: `aws secretsmanager list-secrets`
Create a secret ("Other Types"): `aws secretsmanager create-secret --name MyOtherTypeSecret --secret-string '{"key1":"value1","key2":"value2"}'`
Get secret: `aws secretsmanager get-secret-value --secret-id MyOtherTypeSecret`
Delete secret: `aws secretsmanager delete-secret --secret-id MyOtherTypeSecret`