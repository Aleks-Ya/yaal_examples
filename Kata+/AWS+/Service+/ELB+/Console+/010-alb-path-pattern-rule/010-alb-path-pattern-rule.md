# 010-alb-path-pattern-rule

## Task
Use path pattern in a Listener rule of an Application Load Balancer (ALB)
1. Create an ALB which returns:
	1. 518 status on `/teapot`
	2. 202 status on `/accept/request`
	3. 501 status on other paths

## Steps
1. Create a Security Group
	1. Security group name: `kata-sg-alb-path-pattern-rule`
	2. Description: `Allow all traffic`
	3. VPC: default
	4. Inbound rules
		1. Add rule
			1. Type: `All traffic`
			2. Source: `Anywhere-IPv4`, `0.0.0.0/0`
1. Create a Target Group
	1. Choose a target type: `Instances`
	2. Target group name: `kata-tg-alb-path-pattern-rule`
3. Create an ALB:
	1. Basic configuration
		1. Load balancer name: `kata-lb-alb-path-pattern-rule`
		2. Scheme: `Internet-facing`
	2. Network mapping
		1. VPC: default
		2. Availability Zones and subnets: mark all
	3. Security groups: `kata-sg-alb-path-pattern-rule`
	4. Listeners and routing
		1. Listener HTTP:80
			1. Default action: `kata-tg-alb-path-pattern-rule`
4. Configure the ALB
	1. Listeners and rules
		1. HTTP:80
			1. Listener rules
				1. Add rule Teapot
					1. Name: `Teapot`
					2. Conditions
						1. Path
						2. Path condition value: `/teapot`
					3. Actions
						1. Routing action: `Return fixed response`
						2. Response code: `418`
						3. Response body: `Tea is ready!`
					4. Priority: `1`
				2. Add rule Accept
					1. Name: `Accept`
					2. Conditions
						1. Path
						2. Path condition value: `/accept/request`
					3. Actions
						1. Routing action: `Return fixed response`
						2. Response code: `202`
						3. Response body: `Request was accepted!`
					4. Priority: `1`
				3. Configure rule Default
					1. Actions
						1. Routing action: `Return fixed response`
						2. Response code: `501`
						3. Response body: `Nothing here!`

## Test
1. Copy `DNS name` in the ABL
2. Teapot path: `curl -i http://lb-2-1629303854.eu-west-1.elb.amazonaws.com/teapot` (response 418 `Tea is ready!`)
3. Accept path: `curl -i http://lb-2-1629303854.eu-west-1.elb.amazonaws.com/accept/request` (response 202 `Request was accepted!`)
4. Default response (root): `curl -i http://lb-2-1629303854.eu-west-1.elb.amazonaws.com` (response 501 `Nothing here!`)
5. Default response (path): `curl -i http://lb-2-1629303854.eu-west-1.elb.amazonaws.com/abc` (response 501 `Nothing here!`)

## Cleanup
1. Delete ALB
2. Delete Target Group
3. Delete Secuit Group

## History
