#!/usr/bin/env bash

#name="release/gvc-3"
name=""
if [[ -z ${name} ]]
then
    echo "SUCCESS: empty"
else
    echo "FAIL: not empty"
fi