# Kata ideas

## AWS

1. S3
    - [ ] Create a static web-site based on S3
    - [ ] S3
2. DynamoDB
    - [X] 01 Create table
    - [X] 02 Grant table access to user
    - [X] 03 Use LeadingKeys
    - [X] 04 Batch operations
    - [X] 05 Exceed throughput capacity
    - [X] 06 Use local index
    - [X] Use global secondary index
    - [X] Eventually and strongly consistent reads
    - [ ] Filter expressions in Scan and Query operations
    - [ ] DynamoBD Streams as Lambda event-source
    - [ ] Use DAX
3. ElastiCache
	- [ ] Create a Cache, use it from CLI or Console
	- [ ] Use Lazy Loading caching strategy
	- [ ] Use Write-Through caching strategy
4. API Gateway
	- [ ] See latencies in CloudWatch logs
	- [ ] 2 stages with 2 Lambda versions
	- [ ] Create a WebSocket API 
5. Lambda
	- [X] Create a function
	- [X] Use function aliases
	- [X] Execute function asynchronously
	- [X] Use request parameters
	- [ ] Put libraries into a layer
	- [ ] Share a layer among 2 functions
	- [ ] Using different triggers
6. EC2
	- [ ] 
7. VPC
	- [ ] Connect EC2 to RDS
	- [ ] Connect resources from VPCs in different Regions
	- [ ] Create PrivateLink
8. AppSync
	- [ ] Create an API
9. Beanstalk
	- [ ] 
10. Cognito
	- [ ] Implement sing-up and sign-in functionality
	- [ ] Use Identity pool for temporary access to resources
11. KMS
	- [ ] Envelope encryption: encrypt object using a data key, store object and its data key in a database 
12. RDS
	- [ ] Create RDS database
	- [ ] Restore RDS DB from backup
	- [ ] CloudFormation
	- [ ] Deploy EC2 and RDS
14. EventBridge
	- [ ] EventBridge collects lifecycle events of an EC2 instance
	- [ ] EventBridge sends push notifications from CodeCommit to SNS
	- [ ] EventBridge Pipe creation
	- [ ] EventBridge Event Bus creation
15. CloudWatch
	- [ ] Use metric filter to parse logs
16. Route53
	- [ ] Domain name for Lambda Function
	- [ ] Domain name for EC2 instance
17. SNS
	- [ ] SNS sends emails to users via SES
18. ECS
	- [ ] 
19. SQS
	- [ ] Use FIFO queue
20. CloudFront
	- [ ] Create a Destination with EC2 origin
21. SSM
	- [ ] Rotate password for a RDS database
22. STS
	- [ ] Assume role
