#!/bin/bash

content1=$(<test.txt)
echo -e "Content 1:\n$content1"

content2=$(cat test.txt)
echo -e "Content 2:\n$content2"