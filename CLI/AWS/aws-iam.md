# AWS IAM CLI

## Role
List all roles: `aws iam list-roles`
Show role details (table): `aws iam get-role --role-name AssumedRole1`
Show role details (JSON): `aws iam get-role --output json --role-name AssumedRole1`
Delete a role: `aws iam delete-role --role-name AssumedRole1`

## User
List all users: `aws iam list-users`
Create a user: `aws iam create-user --user-name user1`
Delete a user: `aws iam delete-user --user-name user1`

## Access Key
Create an access key for a user: `aws iam create-access-key --user-name user1`
List access keys of a user: `aws iam list-access-keys --user-name user1`
Delete an access key of a user: `aws iam delete-access-key --user-name user1 --access-key-id AKIAIOSFODNN7EXAMPLE`
