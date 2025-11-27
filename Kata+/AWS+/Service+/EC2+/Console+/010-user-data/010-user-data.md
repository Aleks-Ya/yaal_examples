# 010-user-data

## Task
Management interface: Management Console
Create a EC2 instance with a web-server which should start automatically.

## Steps
1. Create a EC2 instance
    1. Name: `kata-i-user-data`
    2. Key pair name: `Proceed without a keypair`
    3. Network settings
        1. VPC: `default`
        2. Auto-assign public IP: `enabled`
        3. Security group: `default`
    4. User data:
        ```bash
        #!/bin/bash
        echo "Hello from EC2 instance!" > index.html
        python3 -m http.server 80 &
        ```
2. Test web-server
    1. Copy "Public IPv4 address" from `kata-i-user-data`
    2. Test: `curl 50.19.33.2`

## Cleanup
1. Terminate instance: `kata-i-user-data`
