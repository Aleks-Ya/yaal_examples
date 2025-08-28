import boto3


def test_put_get_item():
    table_name: str = 'TmpItemTable'
    __create_table(table_name)

    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table(table_name)
    item_id: str = '1'
    exp_city: str = 'London'

    # Put item
    item: dict[str, str] = {
        'id': item_id,
        'city': exp_city
    }
    response_put = table.put_item(Item=item)
    print(response_put)

    # Get item
    response_get = table.get_item(Key={'id': item_id})
    print(response_get)
    act_item = response_get['Item']
    act_city: str = act_item['city']
    assert act_city == exp_city

    __delete_table(table_name)


def __create_table(table_name: str) -> None:
    dynamodb = boto3.client('dynamodb')
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


def __delete_table(table_name: str) -> None:
    dynamodb = boto3.client('dynamodb')
    dynamodb.delete_table(TableName=table_name)
    dynamodb.get_waiter('table_not_exists').wait(TableName=table_name)
