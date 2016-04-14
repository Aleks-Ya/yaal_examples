#!/usr/bin/env bash

name="release/gvc-3"
if [[ "${name}" == release/* ]]
then
    echo "SUCCESS"
else
    echo "FAIL"
fi