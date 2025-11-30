# 040-httpd

## Task
Create a EC2 instance with httpd server available from the Internet.

## Steps
1. Create an EC2 instance
    1. Name: `kata-i-httpd`
    2. Key pair name: `Proceed without a keypair`
    3. Network settings
    	1. Security Group: `default`
    4. User data:
        ```bash
		#!/bin/bash
		dnf install -y httpd
		systemctl enable httpd
		systemctl start httpd
		echo "My httpd page" > /var/www/html/index.html
        ```
2. Test web-server
    1. Copy "Public IPv4 address" from `kata-i-httpd`
    2. Test: `curl 100.26.47.158`

## Cleanup
1. Terminate instance: `kata-i-httpd`

## History
