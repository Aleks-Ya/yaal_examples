import boto3

s3 = boto3.resource('s3')

def lambda_handler(event, context):
    bucket_collection = s3.buckets.all()
    bucket_names: list[str] = [bucket.name for bucket in bucket_collection]
    names: str = ", ".join(bucket_names)
    print(f"Buckets: {names}")
    return names