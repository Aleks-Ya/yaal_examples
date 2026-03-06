import boto3

bucket_name: str = "yaal-backup"


def test_list_objects_client():
    s3 = boto3.client("s3")
    response = s3.list_objects_v2(Bucket=bucket_name)
    print(response)
    for obj in response["Contents"]:
        print(obj["Key"])
    print()


def test_list_objects_all():
    s3 = boto3.resource('s3')
    bucket = s3.Bucket(bucket_name)
    for obj in bucket.objects.all():
        print(obj.key)


def test_list_objects_filtered():
    s3 = boto3.resource('s3')
    bucket = s3.Bucket(bucket_name)
    for obj in bucket.objects.filter(Prefix="duplicity-backup-joplin-desktop"):
        print(obj.key)
