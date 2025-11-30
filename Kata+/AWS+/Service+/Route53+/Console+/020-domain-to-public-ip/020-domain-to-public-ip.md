# 020-domain-to-public-ip

## Task
Create a Domain Name for a public IP address.

## Steps
1. Choose an IP address:
	1. Test original domain name: `curl http://httpbin.io/uuid`
	2. Get IP address: `dig httpbin.io`
	3. Test IP address: `curl http://3.224.228.208/uuid`
2. Create a Hosted Zone
	1. Domain name: `yaal.com`
	2. Type: `Public hosted zone`
	3. Test domain: `dig yaal.com`
3. Create a Record
	1. Record name: `bin`
	2. Record type: `A`
	3. Alias: false
	4. Value: Lambda URL `3.224.228.208`
	5. Test the record (using NS from the Hosted Zone): `dig @ns-1317.awsdns-36.org bin.yaal.com`
4. (CURL DOES NOT SUPPORT IT!!!) Test: `curl --dns-servers ns-1317.awsdns-36.org http://bin.yaal.com`

## Cleanup
1. Delete the Hosted Zone

## History
