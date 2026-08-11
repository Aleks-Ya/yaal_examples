import boto3
from botocore.client import BaseClient


def test_list_buckets_client():
    s3_client: BaseClient = boto3.client("s3")
    response: dict[str, object] = s3_client.list_buckets()
    print(response)


def test_list_buckets_all():
    s3_resource = boto3.resource("s3")
    bucket_collection = s3_resource.buckets.all()
    for bucket in bucket_collection:
        print(bucket.name)
