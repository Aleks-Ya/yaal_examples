from aws_cdk import App
from aws_cdk import Stack
from aws_cdk.cx_api import CloudAssembly
from constructs import Construct


class HelloCdkStack(Stack):
    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)


def test_create_bucket():
    app: App = App()
    HelloCdkStack(app, "HelloCdkStack")
    assembly: CloudAssembly = app.synth()
    print(assembly.stacks[0].template)
