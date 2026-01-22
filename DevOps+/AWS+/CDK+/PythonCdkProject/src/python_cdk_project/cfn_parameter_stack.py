from aws_cdk import Stack, RemovalPolicy, CfnParameter
from aws_cdk.aws_s3 import Bucket
from constructs import Construct


# Deploy: `cdk deploy CfnParameterStack --parameters bucketName=cdk-bucket-8392038`
# Destroy: `cdk destroy MinimalOpenSearchDomainStack`
class CfnParameterStack(Stack):

    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        bucket_name_param: CfnParameter = CfnParameter(self, "bucketName", type="String")
        bucket_name: str = bucket_name_param.value_as_string
        Bucket(
            self,
            id="cdk-bucket-cfn-parameter",
            bucket_name=bucket_name,
            removal_policy=RemovalPolicy.DESTROY,
            auto_delete_objects=True
        )
