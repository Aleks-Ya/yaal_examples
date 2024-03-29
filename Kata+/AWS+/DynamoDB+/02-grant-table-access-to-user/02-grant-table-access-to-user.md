# 02-grant-table-access-to-user

## Task
Create a table under the Root User and grant full access to the table for an IAM user.

## Setup
1. Create Table1 (visible to the user)
    1. Choose `us-east-1` region
    2. Table name: `table-1`
    3. Partition key: `id`, String
    4. Table settings: `Default settings`
2. Create Table2 (invisible to the user)
    1. Choose `us-east-1` region
    2. Table name: `table-2`
    3. Partition key: `id`, String
    4. Table settings: `Default settings`
3. Create a User
    1. User name: `user-1`
    2. Provide user access to the AWS Management Console: `true`
    3. User type: `I want to create an IAM user`
    4. Console password: `Autogenerated password`
    5. Users must create a new password at next sign-in: `false`
    6. Permissions options: `Attach policies directly`
    7. Create policy
        1. Service: `DynamoDB`
        2. Actions allowed: `All DynamoDB actions (dynamodb:*)`
        3. Resources: `table` = table ARN
        4. Policy name: `dynamodb_table-1_full_access_policy`
4. Test access
    1. Login as `user-1`
    2. Choose `us-east-1` region
    3. Open DynamoDB service
    4. Check that user can see both tables `table-1` and `table-2` in the list, but details are available only for `table-1`
    5. Open table `table-1`
    6. Actions -> Create item
        1. id: `1`

## Cleanup
1. Delete user `user-1`
2. Delete policy `dynamodb_table-1_full_access_policy`
3. Delete tables: `table-1` and `table-2`
