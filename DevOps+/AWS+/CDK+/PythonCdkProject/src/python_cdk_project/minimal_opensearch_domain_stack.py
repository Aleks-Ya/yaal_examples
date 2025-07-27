from aws_cdk import Stack, RemovalPolicy
from aws_cdk.aws_logs import LogGroup
from constructs import Construct
from aws_cdk.aws_opensearchservice import Domain, EngineVersion, CapacityConfig, EbsOptions, LoggingOptions
from aws_cdk.aws_iam import PolicyStatement, Effect, User
from aws_cdk.aws_ec2 import EbsDeviceVolumeType


class MinimalOpenSearchDomainStack(Stack):

    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        domain_name: str = "domain1"

        group_name: str = f"/cdk/{self.__class__.__name__}/{domain_name}"
        group: LogGroup = LogGroup(self, id=group_name, log_group_name=group_name, removal_policy=RemovalPolicy.DESTROY)

        Domain(
            self,
            id=domain_name,
            domain_name=domain_name,
            version=EngineVersion.OPENSEARCH_2_19,
            capacity=CapacityConfig(
                data_node_instance_type="r7g.medium.search",
                multi_az_with_standby_enabled=False,
                master_nodes=0,
                data_nodes=1
            ),
            ebs=EbsOptions(
                volume_size=10,
                volume_type=EbsDeviceVolumeType.GP3
            ),
            removal_policy=RemovalPolicy.DESTROY,
            fine_grained_access_control=None,
            node_to_node_encryption=False,
            enforce_https=False,
            access_policies=[
                PolicyStatement(
                    effect=Effect.ALLOW,
                    principals=[User.from_user_arn(self, "Postman", "arn:aws:iam::523633434047:user/Postman")],
                    actions=["es:*"],
                    resources=[f"arn:aws:es:{self.region}:{self.account}:domain/{domain_name}/*"]
                )
            ],
            logging=LoggingOptions(app_log_enabled=True, app_log_group=group)
        )
