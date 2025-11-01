# 020-grant-table-access-to-user

## Task
Create a table under the Root User and grant full access to the table for an IAM user.

## Setup
1. Create Table1 (visible to the user)
    1. Choose `us-east-1` region
    2. Table name: `kata-table-grant-table-access-to-user-visible`
    3. Partition key: `id`, String
    4. Table settings: `Default settings`
2. Create Table2 (invisible to the user)
    1. Choose `us-east-1` region
    2. Table name: `kata-table-grant-table-access-to-user-invisible`
    3. Partition key: `id`, String
    4. Table settings: `Default settings`
3. Create a User
    1. User name: `kata-user-grant-table-access-to-user`
    2. Provide user access to the AWS Management Console: `true`
    3. User type: `I want to create an IAM user`
    4. Console password: 
        1. Choice: `Custom password`
        2. Password: `Pass12`
    5. Users must create a new password at next sign-in: `false`
    6. Permissions options: `Attach policies directly`
        1. Create policy
            1. Service: `DynamoDB`
            2. Actions allowed: `All DynamoDB actions (dynamodb:*)`
            3. Resources: `table` - Add ARN
                1. Resource region: `*`
                2. Resource table name: `kata-table-grant-table-access-to-user-visible`
            4. Policy name: `kata-policy-grant-table-access-to-user`
        2. Permissions policies: `kata-policy-grant-table-access-to-user`
4. Test access
    1. Login as `kata-user-grant-table-access-to-user`
    2. Choose `us-east-1` region
    3. Open DynamoDB service
    4. Check that user can see both tables:
        1. `kata-table-grant-table-access-to-user-visible` - detail are available
        2. `kata-table-grant-table-access-to-user-invisible` - detail are NOT available
    5. Open table `kata-table-grant-table-access-to-user-visible`
        1. Actions -> Create item
            1. id: `1`

## Cleanup
1. Delete user `kata-user-grant-table-access-to-user`
2. Delete policy `kata-policy-grant-table-access-to-user`
3. Delete table: `kata-table-grant-table-access-to-user-visible`
4. Delete table: `kata-table-grant-table-access-to-user-invisible`

## History
- 2025-11-02 success
