#!/usr/bin/env bash

echo Without braces

echo "With double braces. PWD=${PWD}"

echo 'With single braces. PWD=${PWD}'

echo "Multiline
Line two
Line three"

echo -e "\nEmpty lines before and after\n"
echo -e '\nEmpty lines before and after\n'
