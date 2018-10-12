#!/bin/bash

# If file exists
file="/etc/hosts"
if [ -f "$file" ]
then
	echo "SUCCESS: $file found."
else
	echo "FAIL: $file not found."
fi

file="not-exists-file"
if [ -f "$file" ]
then
	echo "FAIL:  $file found."
else
	echo "SUCCESS: $file not found."
fi


# If file not exists
file="/etc/hosts"
if [ ! -f "$file" ]
then
	echo "FAIL: $file found."
else
	echo "SUCCESS: $file not found."
fi
