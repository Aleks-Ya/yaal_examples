#!/usr/bin/env bash

name="feature/gvc-3"
if [[ "$name" == release/* ]] || [[ "$name" == feature/* ]]
 then
    echo "yes"
else
    echo "no"
fi

name="feature/gvc-3"
isMaster=$()
if [[ "$name" == release/* ]] || [[ "$name" == feature/* ]]
 then
    echo "yes"
else
    echo "no"
fi