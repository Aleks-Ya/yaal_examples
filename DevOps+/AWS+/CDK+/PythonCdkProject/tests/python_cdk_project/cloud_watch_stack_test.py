from aws_cdk import App
from aws_cdk.assertions import Template

from python_cdk_project.cloud_watch_stack import CloudWatchStack


def test_template():
    app: App = App()
    stack: CloudWatchStack = CloudWatchStack(app, "CloudWatchStack")
    template: Template = Template.from_stack(stack)
    print(template.to_json())

#     template.has_resource_properties("AWS::SQS::Queue", {
#         "VisibilityTimeout": 300
#     })
