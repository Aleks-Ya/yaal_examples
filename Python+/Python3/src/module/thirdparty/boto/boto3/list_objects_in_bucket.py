import boto3

access_key_id = "xxx"
secret_access_key = "yyy"

bucket_name = "zzz"

client = boto3.client(
    "s3",
    aws_access_key_id=access_key_id,
    aws_secret_access_key=secret_access_key,
)

response = client.list_objects_v2(Bucket=bucket_name)

for obj in response["Contents"]:
    print(obj["Key"])
