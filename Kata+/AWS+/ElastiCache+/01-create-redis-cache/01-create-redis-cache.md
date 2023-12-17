# 01-create-redis-cache

## Task
Create a Redis cache. Put a key. Get the key.

## Setup
1. Create a Redis cache
	1. Deployment option: `Serverless`
	2. Creation method: `New cache`
	3. Name: `redis-cache-1`
	4. Default settings: `Use default settings`
2. Create an EC2 intance
	1. Name: `instance-1`
	2. Quick start: `Ubuntu`
	3. Key pair name: `Proceed without a key pair`
	4. Network settings: default
3. Configure the EC2 instance
	1. Connect using Instance Connect
	2. Install Redis CLI: `sudo apt update && sudo apt install -y redis`
3. Test
	1. Connect to Redis: `redis-cli -h redis-cache-1-7d1iqx.serverless.use1.cache.amazonaws.com -p 6379`

NOT FINISHED!!!!!!!!!!!!!!


## Cleanup
1. Delete cache
