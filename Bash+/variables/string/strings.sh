#!/usr/bin/env bash

str="Feature/Test-8"
echo "Source string: ${str}"
echo "Replace 8 with 9: ${str/8/9}"
echo "Replace '/' with '-': ${str////-}"
echo "Interval from 8 to the end: ${str:8}"
echo "To uppercase: ${str^^}"
