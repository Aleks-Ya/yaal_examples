from aws_cdk import App
from aws_cdk.assertions import Template

from python_cdk_project.minimal_opensearch_domain_stack import MinimalOpenSearchDomainStack


def test_template():
    app: App = App()
    stack: MinimalOpenSearchDomainStack = MinimalOpenSearchDomainStack(app, "MinimalOpenSearchDomainStack")
    template: Template = Template.from_stack(stack)
    print(template.to_json())

#     template.has_resource_properties("AWS::SQS::Queue", {
#         "VisibilityTimeout": 300
#     })
