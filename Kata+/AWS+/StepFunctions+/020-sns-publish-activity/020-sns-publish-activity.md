# 020-sns-publish-activity

## Task
Create a StateMachine that publishes a message to an SNS topic.

## Setup
1. Create Queue
	1. Type: `Standard`
	2. Name: `kata-sns-publish-activity-queue`
2. Create Topic
	1. Create Topic
		1. Type: `Standard`
		2. Name: `kata-sns-publish-activity-topic`
	3. Create Subscription
		1. Protocol: `Amazon SQS`
		2. Endpoint: queue `kata-sns-publish-activity-queue`
	3. Test Topic
		1. Publish a test message
		2. Find the message in the queue
3. Create StateMachine
	1. Name: `kata-sns-publish-activity-sm`
	2. State machine type: `Standard`
	3. Create `SNS Publish` state
		1. Add an `SNS Publish` action
		2. Execute "Test state"
4. Execute StateMachine
	1. Execute StateMachine
	2. Find the message in the queue

## Cleanup
1. Delete StateMachine
	1. Delete StateMachine `kata-sns-publish-activity-sm`
	2. Delete Execution Role `SStepFunctions-kata-sns-publish-activity-sm-role-e7mmv6plv`
2. Delete Topic
	1. Delete Topic `kata-sns-publish-activity-topic`
	2. Delete Service Role `SNSSuccessFeedback`
	3. Delete Service Role `SNSFailureFeedback`
3. Delete Queue `kata-sns-publish-activity-queue`
