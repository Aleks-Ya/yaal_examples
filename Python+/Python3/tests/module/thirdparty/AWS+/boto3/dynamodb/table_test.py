import boto3


def test_list_tables():
    dynamodb = boto3.client('dynamodb')
    response = dynamodb.list_tables()
    print(response['TableNames'])


def test_create_delete_table():
    dynamodb = boto3.client('dynamodb')
    table_name: str = 'SimpleTable'

    # Create table
    assert table_name not in dynamodb.list_tables()['TableNames']
    dynamodb.create_table(
        TableName=table_name,
        KeySchema=[
            {'AttributeName': 'id', 'KeyType': 'HASH'}
        ],
        AttributeDefinitions=[
            {'AttributeName': 'id', 'AttributeType': 'S'}
        ],
        BillingMode='PAY_PER_REQUEST'
    )
    dynamodb.get_waiter('table_exists').wait(TableName=table_name)
    assert table_name in dynamodb.list_tables()['TableNames']

    # Delete table
    dynamodb.delete_table(TableName=table_name)
    dynamodb.get_waiter('table_not_exists').wait(TableName=table_name)
    assert table_name not in dynamodb.list_tables()['TableNames']
