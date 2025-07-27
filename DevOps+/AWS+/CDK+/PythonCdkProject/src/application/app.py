#!/usr/bin/env python3

import aws_cdk as cdk
from aws_cdk import App

from python_cdk_project.cloud_watch_stack import CloudWatchStack
from python_cdk_project.minimal_opensearch_domain_stack import MinimalOpenSearchDomainStack
from python_cdk_project.python_cdk_project_stack import PythonCdkProjectStack

app: App = cdk.App()
environment = cdk.Environment(region="us-east-1")
PythonCdkProjectStack(app, "PythonCdkProjectStack",
                      env=environment
                      # If you don't specify 'env', this stack will be environment-agnostic.
                      # Account/Region-dependent features and context lookups will not work,
                      # but a single synthesized template can be deployed anywhere.

                      # Uncomment the next line to specialize this stack for the AWS Account
                      # and Region that are implied by the current CLI configuration.

                      # env=cdk.Environment(account=os.getenv('CDK_DEFAULT_ACCOUNT'), region=os.getenv('CDK_DEFAULT_REGION')),

                      # Uncomment the next line if you know exactly what Account and Region you
                      # want to deploy the stack to. */

                      # env=cdk.Environment(account='123456789012', region='us-east-1'),

                      # For more information, see https://docs.aws.amazon.com/cdk/latest/guide/environments.html
                      )
CloudWatchStack(app, "CloudWatchStack", env=environment)
MinimalOpenSearchDomainStack(app, "MinimalOpenSearchDomainStack", env=environment)

app.synth()
