from aws_cdk import App
from aws_cdk.assertions import Template

from python_cdk_project.python_cdk_project_stack import PythonCdkProjectStack


# example tests. To run these tests, uncomment this file along with the example
# resource in python_cdk_project/python_cdk_project_stack.py
def test_sqs_queue_created():
    app = App()
    stack = PythonCdkProjectStack(app, "python-cdk-project")
    template = Template.from_stack(stack)

#     template.has_resource_properties("AWS::SQS::Queue", {
#         "VisibilityTimeout": 300
#     })
