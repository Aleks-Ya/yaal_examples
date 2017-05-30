#!/usr/bin/env bash

name="release/gvc-3"
#name=""
if [[ ! -z ${name} ]]
then
    echo "SUCCESS: not empty"
else
    echo "FAIL: empty"
fi