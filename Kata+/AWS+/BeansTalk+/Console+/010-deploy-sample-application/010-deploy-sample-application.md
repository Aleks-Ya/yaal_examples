# 010-deploy-sample-application

## Task
Deploy the Sample Application into BeansTalk.

## Setup
1. Create an IAM role
    1. Trusted entity type: `AWS service`
    2. Service or use case: `EC2`
    3. Use case: `EC2`
    4. Permissions
       policies: `AWSElasticBeanstalkMulticontainerDocker`, `AWSElasticBeanstalkWorkerTier`, `AWSElasticBeanstalkWebTier`
    5. Role name: `beanstalk-ec2-role-1`
2. Create an Environment
    1. Environment tier: `Web server environment`
    2. Application name: `application-1`
    3. Environment name: default `Application-1-env`
    4. Domain: `env-1`
    5. Platform
        1. Platform type: `Managed platform`
        2. Platform: `Python`
        3. Platform branch: default
        4. Platform version: default
    6. Application code: `Sample application`
    7. Presets: `Single instance (free tier eligible)`
    8. Service access
        1. Service role: `Create and use new service role`
        2. Service role name: `aws-elasticbeanstalk-service-role-1`
        3. EC2 key pair: empty
        4. EC2 instance profile: `beanstalk-ec2-role-1`

## Cleanup
1. Delete Application: `application-1`
2. Terminate Environment: `Application-1-env`
3. IAM roles: `aws-elasticbeanstalk-service-role-1`, `beanstalk-ec2-role-1`
