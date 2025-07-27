from aws_cdk import Stack, RemovalPolicy
from aws_cdk.aws_logs import LogGroup, LogStream
from constructs import Construct


class CloudWatchStack(Stack):

    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        group_name: str = "/tmp/CloudWatchStack-Group"
        group: LogGroup = LogGroup(self, id=group_name, log_group_name=group_name, removal_policy=RemovalPolicy.DESTROY)

        stream_name_1: str = "CloudWatchStack-Stream-1"
        LogStream(self, id=stream_name_1, log_stream_name=stream_name_1, log_group=group,
                  removal_policy=RemovalPolicy.DESTROY)

        stream_name_2: str = "CloudWatchStack-Stream-2"
        LogStream(self, id=stream_name_2, log_stream_name=stream_name_2, log_group=group,
                  removal_policy=RemovalPolicy.DESTROY)
