#!/bin/bash
echo "Hello from EC2 instance!" > index.html
sudo python3 -m http.server 80 &
