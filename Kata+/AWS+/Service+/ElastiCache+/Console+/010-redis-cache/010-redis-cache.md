# 010-redis-cache

## Task
Create a serverless Redis cache. Put and a key from CloudShell.

## Steps
1. Create a cache
	1. Engine: `Redis OSS`
	2. Deployment option: `Serverless`
	3. Creation method: `New cache`
	4. Name: `kata-cache-redis-cache`
	5. Default settings: `Use default settings`
3. Use the cache
	1. Open CloudShell
	2. Actions - "Create VPC environment"
		1. Name: `env1`
		2. Virtual private cloud (VPC): `default`
		3. Subnet: any
		4. Security group: `default`
	3. Connect to Redis: `redis-cli -h kata-cache-redis-cache-7d1iqx.serverless.use1.cache.amazonaws.com -p 6379`
	4. Use connection
		1. Test: `PING`
		2. Set a key: `SET key1 abc`
		3. Get a key: `GET key1`

## Cleanup
1. Delete cache

## History
- 2025-11-04 success
