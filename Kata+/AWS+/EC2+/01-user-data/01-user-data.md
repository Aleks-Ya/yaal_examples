# 01-user-data

## Task

Create a EC2 instance with a web-server which should start automatically.

## Setup

1. Create a EC2 instance
    1. Name: `instance-1`
    2. Key pair name: `Proceed without a keypair`
    3. Network settings
        1. Allow HTTP traffic from the internet: enabled
    4. User data:
        ```bash
        #!/bin/bash
        echo "Hello from EC2 instance!" > index.html
        sudo python3 -m http.server 80 &
        ```
2. Test web-server
    1. Copy "Public IPv4 address" from `instance-1`
    2. Test: `curl -i http://50.19.33.2`

## Cleanup

1. Terminate instance: `instance-1`
