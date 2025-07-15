# AWS IAM CLI

## Role
List all roles: `aws iam list-roles`
Show role details (table): `aws iam get-role --role-name AssumedRole1`
Show role details (JSON): `aws iam get-role --output json --role-name AssumedRole1`
Delete a role: `aws iam delete-role --role-name AssumedRole1`
Create a role: `aws iam create-role --role-name role1 --assume-role-policy-document file://assume-role-policy.json`

## User
List all users: `aws iam list-users`
Create a user: `aws iam create-user --user-name user1`
Delete a user: `aws iam delete-user --user-name user1`

## Group
List groups of a user: `aws iam list-groups-for-user --user-name user1`

## Access Key
Create an access key for a user: `aws iam create-access-key --user-name user1`
List access keys of a user: `aws iam list-access-keys --user-name user1`
Delete an access key of a user: `aws iam delete-access-key --user-name user1 --access-key-id AKIAIOSFODNN7EXAMPLE`

## Policy
List all policies: `aws iam list-policies`
List policies by substring: `aws iam list-policies --query "Policies[?PolicyName.contains(@, 'S3')]"`
List policies attached to a user: `aws iam list-attached-user-policies --user-name user1`
Show policy details: `aws iam get-policy --policy-arn arn:aws:iam::aws:policy/AdministratorAccess`
Show policy version details: `aws iam get-policy-version --policy-arn arn:aws:iam::523633434047:policy/MyPolicy --version-id v2`
Create a policy from file: `aws iam create-policy --policy-name MyPolicy --policy-document file://policy.json`
Attach a policy to a user: `aws iam attach-user-policy --user-name user1 --policy-arn arn:aws:iam::aws:policy/S3AccessPolicy`
Detach a policy from a user: `aws iam detach-user-policy --user-name user1 --policy-arn arn:aws:iam::aws:policy/S3AccessPolicy`
Delte a policy: `aws iam delete-policy --policy-arn arn:aws:iam::523633434047:policy/MyPolicy`
List policy versions: `aws iam list-policy-versions --policy-arn arn:aws:iam::523633434047:policy/MyPolicy`
Create a policy version: `aws iam create-policy-version --policy-arn arn:aws:iam::123456789012:policy/MyPolicy --policy-document file://modified-policy.json --set-as-default`
Delete a policy version: `aws iam delete-policy-version --policy-arn arn:aws:iam::523633434047:policy/MyPolicy --version-id v1`

## Errors
### MalformedPolicyDocument
Command: `aws iam create-role --role-name role1 --assume-role-policy-document assume-role-policy.json`
Message: `An error occurred (MalformedPolicyDocument) when calling the CreateRole operation: This policy contains invalid Json`
Fix: add protocol `file://` to JSON file path (`file://assume-role-policy.json`)
