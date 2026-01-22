from aws_cdk import Stack, RemovalPolicy
from aws_cdk.aws_s3 import Bucket
from constructs import Construct


class S3BucketStack(Stack):

    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        bucket_name: str = "cdk-bucket-14888403871340275063719"
        Bucket(
            self,
            id=bucket_name,
            bucket_name=bucket_name,
            removal_policy=RemovalPolicy.DESTROY,
            auto_delete_objects=True
        )
