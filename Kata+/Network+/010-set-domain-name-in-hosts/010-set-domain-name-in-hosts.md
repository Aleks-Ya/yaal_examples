# 010-set-domain-name-in-hosts

## Task
Set a custom domain name in the `/etc/hosts` file.

## Steps
1. Backup: `sudo cp /etc/hosts /etc/hosts.bak`
2. Choose an IP address:
	1. Test original domain name: `curl http://httpbin.io/uuid`
	2. Get IP address: `dig httpbin.io`
	3. Test IP address: `curl http://3.224.228.208/uuid`
3. Create a custom domain name: `echo "3.224.228.208 bin.google.com" | sudo tee -a /etc/hosts`
4. Test custom domain name: `curl http://bin.google.com/uuid`

## Cleanup
1. Restore from backup: `sudo mv /etc/hosts.bak /etc/hosts`
