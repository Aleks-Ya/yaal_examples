import boto3


def test_get_caller_identity():
    sts = boto3.client('sts')
    response = sts.get_caller_identity()
    print(response)
    assert 'UserId' in response
    assert 'Account' in response
    assert 'Arn' in response
