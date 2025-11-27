#!/bin/bash
echo "Hello from EC2 instance!" > index.html
python3 -m http.server 80 &
