#!/bin/bash

# If dir exists
dir="../files/"
if [ -d "$dir" ]
then
	echo "SUCCESS: $dir found."
else
	echo "FAIL: $dir not found."
	exit 1
fi

dir="./not-exists-dir/"
if [ -d "$dir" ]
then
	echo "FAIL:  $dir found."
	exit 1
else
	echo "SUCCESS: $dir not found."
fi


# If dir not exists
dir="../files/"
if [ ! -d "$dir" ]
then
	echo "FAIL: $dir found."
	exit 1
else
	echo "SUCCESS: $dir not found."
fi
