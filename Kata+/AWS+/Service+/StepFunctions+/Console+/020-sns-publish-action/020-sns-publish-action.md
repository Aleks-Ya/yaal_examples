# 020-sns-publish-action

## Task
Create a StateMachine that publishes a message to an SNS topic.

## Steps
1. Create Queue
	1. Type: `Standard`
	2. Name: `kata-q-sns-publish-action`
2. Create Topic
	1. Create Topic
		1. Type: `Standard`
		2. Name: `kata-topic-sns-publish-action`
	3. Create Subscription
		1. Protocol: `Amazon SQS`
		2. Endpoint: queue `kata-q-sns-publish-action`
	3. Test Topic
		1. Publish a test message
		2. Poll the queue for messages
3. Create StateMachine
	1. Name: `kata-sm-sns-publish-action`
	2. State machine type: `Standard`
	3. Create `SNS Publish` state
		1. Add an `SNS Publish` action
		2. Topic: `kata-topic-sns-publish-action`
	4. Create StateMachine
	5. Confirm role creation
	6. Test the `SNS Publish` action:
		1. Click the action
		2. Click "Test state"
		3. Poll the queue for messages
4. Execute StateMachine
	1. Execute StateMachine
	2. Poll the queue for messages

## Cleanup
1. Delete StateMachine
	1. Delete StateMachine `kata-sm-sns-publish-action`
	2. Delete Execution Role `StepFunctions-kata-sm-sns-publish-action-role-0ei41o7qw`
2. Delete Topic: `kata-topic-sns-publish-action`
3. Delete Queue `kata-q-sns-publish-action`

## History
- 2025-12-01 success
