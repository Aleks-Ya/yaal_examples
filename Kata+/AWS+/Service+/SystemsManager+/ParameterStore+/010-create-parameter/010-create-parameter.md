# 010-create-parameter

## Task
Create Parameters of different types in Systems Manager Parameter Store.

## Steps
1. Set environment variables
   ```shell
   set -x
   export PARAM_STRING=/kata-create-parameter/parameter-string
   export PARAM_STRING_LIST=/kata-create-parameter/parameter-string-list
   export PARAM_SECURE_STRING=/kata-create-parameter/parameter-secure-string
   ```
2. Create a String Parameter:
	1. Create: `aws ssm put-parameter --type String --name $PARAM_STRING --value "text value"`
	2. Get: `aws ssm get-parameter --name $PARAM_STRING`
3. Create a StringList Parameter:
	1. Create: `aws ssm put-parameter --type StringList --name $PARAM_STRING_LIST --value "value1,value2"`
	2. Get as string: `aws ssm get-parameter --name $PARAM_STRING_LIST`
4. Create a SecureString Parameter:
	1. Create: `aws ssm put-parameter --type SecureString --name $PARAM_SECURE_STRING --value "text value"`
	2. Get (encrypted): `aws ssm get-parameter --name $PARAM_SECURE_STRING`
	3. Get (decrypted): `aws ssm get-parameter --name $PARAM_SECURE_STRING --with-decryption`

## Cleanup
1. Delete parameters:
	```shell
	aws ssm delete-parameter --name $PARAM_STRING
	aws ssm delete-parameter --name $PARAM_STRING_LIST
	aws ssm delete-parameter --name $PARAM_SECURE_STRING
	```
2. Unset env vars: `set +x; unset PARAM_STRING PARAM_STRING_LIST PARAM_SECURE_STRING`

## History
- 2025-11-28 success
