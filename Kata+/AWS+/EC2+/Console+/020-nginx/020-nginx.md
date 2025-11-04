# 020-nginx

## Task
Create a EC2 instance with NGinx server available from the Internet.

## Steps
1. Create a Security Group
	1. Name: `kata-sg-nginx`
	2. Description: `NGinx`
	3. VPC: `default`
	4. Inbound rules:
		1. Rule 1:
			1. Type `HTTP`
			2. Source: `Anywhere-IPv4`
	5. Outbound rules: (default) `All traffic`
2. Create an EC2 instance
    1. Name: `kata-i-nginx`
    2. Key pair name: `Proceed without a keypair`
    3. Network settings
    	1. Security Group: `kata-sg-nginx`
    4. User data:
        ```bash
        #!/bin/bash
        dnf install -y nginx
        systemctl enable nginx
        systemctl start nginx
        ```
3. Test web-server
    1. Copy "Public IPv4 address" from `kata-i-nginx`
    2. Test: `curl -i http://50.19.33.2`

## Cleanup
1. Terminate instance: `kata-i-nginx`
2. Delete Security Group: `kata-sg-nginx`
