# AWS IAM CLI

## Role
List all roles: `aws iam list-roles`
List all service-linked roles: `aws iam list-roles --path-prefix /aws-service-role`
Show role details: `aws iam get-role --role-name AssumedRole1`
Create a role: `aws iam create-role --role-name role1 --assume-role-policy-document file://assume-role-policy.json`
Delete a role: `aws iam delete-role --role-name AssumedRole1`
Update the trust policy on a role: `aws iam update-assume-role-policy --role-name role1 --policy-document file://trust-policy.json`

## User
List all users: `aws iam list-users`
Create a user: `aws iam create-user --user-name user1`
Delete a user: `aws iam delete-user --user-name user1`

## Group
List all groups: `aws iam list-groups`
List groups of a user: `aws iam list-groups-for-user --user-name user1`
Show group details: `aws iam get-group --group-name group1`

## Access Key
Create an access key for a user: `aws iam create-access-key --user-name user1`
List access keys of a user: `aws iam list-access-keys --user-name user1`
Delete an access key of a user: `aws iam delete-access-key --user-name user1 --access-key-id AKIAIOSFODNN7EXAMPLE`

## Policy
List all policies: `aws iam list-policies`
List all customer-manager policies: `aws iam list-policies --scope Local`
List policies by substring: `aws iam list-policies --query "Policies[?PolicyName.contains(@, 'S3')]"`
Show policy details: `aws iam get-policy --policy-arn arn:aws:iam::aws:policy/AdministratorAccess`
Create a policy from file: `aws iam create-policy --policy-name MyPolicy --policy-document file://policy.json`
Delte a policy: `aws iam delete-policy --policy-arn arn:aws:iam::523633434047:policy/MyPolicy`

### Standalone policy
#### Standalone User policy
List policies attached to a user: `aws iam list-attached-user-policies --user-name user1`
Attach a policy to a user: `aws iam attach-user-policy --user-name user1 --policy-arn arn:aws:iam::aws:policy/S3AccessPolicy`
Detach a policy from a user: `aws iam detach-user-policy --user-name user1 --policy-arn arn:aws:iam::aws:policy/S3AccessPolicy`

#### Standalone Group policy
List policies of a group: `aws iam list-attached-group-policies --group-name group1`
Attach policy to a group: `aws iam attach-group-policy --group-name group1 --policy-arn arn:aws:iam::aws:policy/AmazonEC2FullAccess`

#### Standalone Role policy
List policies attached to a role: `aws iam list-attached-role-policies --role-name MyS3ReadOnlyRole`
Attach a policy to a role: `aws iam attach-role-policy --role-name MyS3ReadOnlyRole --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess`
Detach a policy from a role: `aws iam detach-role-policy --role-name MyS3ReadOnlyRole --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess`

### Inline policy

#### Inline user policy
List inline policies: `aws iam list-user-policies --user-name user1`
Show an inline policy on a user: `aws iam get-user-policy --user-name role1 --policy-name S3InlinePolicy`
Create an inline policy for a user:
```shell
aws iam put-user-policy \
    --user-name role1 
    --policy-name S3InlinePolicy 
    --policy-document file://user-inline-policy.json
```
Delete an inline policy from a user: `aws iam delete-user-policy --user-name user1 --policy-name S3InlinePolicy`

#### Inline role policy
List inline role policies: `aws iam list-role-policies --role-name role1`
Show an inline policy on a role: `aws iam get-role-policy --role-name role1 --policy-name S3InlinePolicy`
Create an inline policy for a role:
```shell
aws iam put-role-policy \
    --role-name role1 \
    --policy-name S3InlinePolicy \
    --policy-document file://role-inline-policy.json
```
Delete an inline policy from a role: `aws iam delete-role-policy --role-name role1 --policy-name S3InlinePolicy`

#### Inline group policy
List inline group policies: `aws iam list-group-policies --group-name group1`

### Policy Versions
List policy versions: `aws iam list-policy-versions --policy-arn arn:aws:iam::523633434047:policy/MyPolicy`
Show policy version details: `aws iam get-policy-version --policy-arn arn:aws:iam::523633434047:policy/MyPolicy --version-id v2`
Create a policy version: 
```shell
aws iam create-policy-version \
    --policy-arn arn:aws:iam::123456789012:policy/MyPolicy \
    --policy-document file://modified-policy.json \
    --set-as-default
```
Delete a policy version: `aws iam delete-policy-version --policy-arn arn:aws:iam::523633434047:policy/MyPolicy --version-id v1`

### Simulate policy
```shell
aws iam simulate-principal-policy \
    --policy-source-arn arn:aws:iam::123456789012:user/ExampleUser \
    --action-names s3:ListBucket \
    --resource-arns arn:aws:s3:::example-bucket
```

## Permission boundary
Show Permission boundary of a user: `aws iam get-user --user-name user1`
Attach a role as permission boundary for a user:
```shell
aws iam put-user-permissions-boundary \
    --user-name user1 \
    --permissions-boundary arn:aws:iam::123456789012:policy/MyPermissionBoundaryPolicy
```

## Instance Profile
List instance profiles: `aws iam list-instance-profiles`
Show an instance profile: `aws iam get-instance-profile --instance-profile-name MyInstanceProfile2`
Create an instance profile: `aws iam create-instance-profile --instance-profile-name MyInstanceProfile2`
Attach a role to an instance profile: 
```shell
aws iam add-role-to-instance-profile \
    --instance-profile-name MyInstanceProfile2 \
    --role-name BedrockAccessRole
```

## Errors
### MalformedPolicyDocument
Command: `aws iam create-role --role-name role1 --assume-role-policy-document assume-role-policy.json`
Message: `An error occurred (MalformedPolicyDocument) when calling the CreateRole operation: This policy contains invalid Json`
Fix: add protocol `file://` to JSON file path (`file://assume-role-policy.json`)
