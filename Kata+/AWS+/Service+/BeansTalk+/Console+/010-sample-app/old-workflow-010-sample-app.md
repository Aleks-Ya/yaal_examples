# 010-sample-app

**Using deprecated Old Workflow**

## Task
Deploy the Sample Application into BeansTalk.

## Steps
1. Create an Environment
    1. Environment tier: `Web server environment`
    2. Application name: `kata-app-sample-app`
    3. Environment name: default `kata-app-sample-app-env`
    4. Domain: `env-1`
    5. Platform
        1. Platform: `Python`
        2. Platform branch: default
        3. Platform version: default
    6. Application code: `Sample application`
    7. Presets: `Single instance (free tier eligible)`
    8. Service access
        1. Service role:
            1. Create role:
                1. Trusted entity type: `AWS service`
                2. Service or use case: `Elastic Beanstalk`
                3. Use case: `Elastic Beanstalk - Environment`
                4. Permissions policies: default
                5. Role name: `kata-role-sample-app-service`
        2. EC2 instance profile: `kata-role-sample-app-instance`
            1. Create role:
                1. Trusted entity type: `AWS service`
                2. Service or use case: `Elastic Beanstalk`
                3. Use case: `Elastic Beanstalk - Compute`
                4. Permissions policies: default
                5. Role name: `kata-role-sample-app-instance`
        3. EC2 key pair: empty
    9. VPC: `-`
    10. Configure instance traffic and scaling: default

## Cleanup
1. Delete Application: `kata-app-sample-app`
2. Terminate Environment: `kata-app-sample-app-env`
3. IAM roles: `kata-role-sample-app-service`, `kata-role-sample-app-instance`

## History
