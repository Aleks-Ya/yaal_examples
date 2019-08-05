#!/bin/bash

echo "File names only"
for filename in *.*; do
	echo "File $filename"
done
echo

echo "File and dir names"
for filename in $PWD/*.*; do
	echo "File $filename"
done
echo

echo "Current dir and file names"
for filename in ./*.*; do
	echo "File $filename"
done