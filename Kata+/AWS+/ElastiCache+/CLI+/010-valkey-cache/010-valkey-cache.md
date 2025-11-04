# 010-valkey-cache

## Task
Create a serverless Valkey cache. Put and a key from AWS CLI.

## Setup
1. Set environment variables
   ```shell
   set -x
   export CACHE=kata-cache-valkey-cache
   ```
1. Create a cache
	1. Create: `aws elasticache create-serverless-cache --engine valkey --serverless-cache-name $CACHE`
	2. Check `available` status: `aws elasticache describe-serverless-caches --serverless-cache-name $CACHE`

2. Create an EC2 intance
	1. Name: `instance-1`
	2. Quick start: `Ubuntu`
	3. Key pair name: `Proceed without a key pair`
	4. Network settings: default
3. Configure the EC2 instance
	1. Connect using Instance Connect
	2. Install Redis CLI: `sudo apt update && sudo apt install -y redis`
3. Test
	1. Connect to Redis: `redis-cli -h kata-cache-valkey-cache-7d1iqx.serverless.use1.cache.amazonaws.com -p 6379`

NOT FINISHED!!!!!!!!!!!!!!


## Cleanup
1. Delete cache
2. Unset env vars: `set +x; unset CACHE`
