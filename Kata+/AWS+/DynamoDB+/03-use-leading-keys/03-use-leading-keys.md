# 03-use-leading-keys

## Task
Use LeadingKeys condition to grant access to items having key equal to the User ID.

## Setup
1. Create a table
    1. Table name: `table-1`
    2. Partition key: `id`, String
    3. Sort key: `ticket`, String
    4. Table settings: `Default settings`
2. Create an IAM policy
    1. Select service 1:
        1. Service: `DynamoDB`
        2. Actions allowed: 
            - `dynamodb:ListTables`
            - `dynamodb:DescribeTable`
        3. Effect: `Allow`
        4. Resources: `All`
    2. Select service 2:
        1. Service: `DynamoDB`
        2. Actions allowed: 
            - `dynamodb:GetItem`
            - `dynamodb:BatchGetItem`
            - `dynamodb:Query`
            - `dynamodb:PutItem`
            - `dynamodb:UpdateItem`
            - `dynamodb:DeleteItem`
            - `dynamodb:BatchWriteItem`
        3. Effect: `Allow`
        4. Resources: `Specific`, `table` = table ARN
        5. Request conditions:
            1. Add another condition
                1. Condition key: `dynamodb:LeadingKeys`
                2. Qualifier: `For all values in request`
                3. Operator: `StringEquals`
                4. Value: `${aws:username}`
    3. Policy name: `dynamodb_table-1_leading_keys_policy`
3. Create User1
	1. User name: `user-1`
	2. Provide user access to the AWS Management Console: `true`
	3. User type: `I want to create an IAM user`
	4. Console password: `Autogenerated password`
	5. Users must create a new password at next sign-in: `false`
	6. Permissions options: `Attach policies directly`
	7. Permissions policies: `dynamodb_table-1_leading_keys_policy`
4. Create user `user-2`
	1. User name: `user-2`
	2. Provide user access to the AWS Management Console: `true`
	3. User type: `I want to create an IAM user`
	4. Console password: `Autogenerated password`
	5. Users must create a new password at next sign-in: `false`
	6. Permissions options: `Attach policies directly`
	7. Permissions policies: `dynamodb_table-1_leading_keys_policy`
5. Test
    1. (optional) Use `us-east-1` region
    2. Create new items:
        1. Create new items as `user-1`: 
            - `id`=`user-1`, `ticket`=`t11`
            - `id`=`user-1`, `ticket`=`t12`
            - Failed to create item with different id: `id`=`user-2`, `ticket`=`t13`
        2. Create new items as `user-2`: 
            - `id`=`user-2`, `ticket`=`t21`
            - `id`=`user-2`, `ticket`=`t22`
            - Failed to create item with different id: `id`=`user-3`, `ticket`=`t23`
    3. Scan items: denied
    4. Query items:
        1. `user-1`
            1. Can with `id`=`user-1`
            2. Can't with `id`=`user-2`
        2. `user-2`
            1. Can with `id`=`user-2`
            2. Can't with `id`=`user-1`

## Cleanup
1. Delete users: `user-1` and `user-2`
2. Delete policy `dynamodb_table-1_leading_keys_policy`
3. Delete the table

## Errors
Tables are not visible or no permission to list: check current region.
