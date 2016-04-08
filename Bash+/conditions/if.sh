#!/usr/bin/env bash

name="release/gvc-3"
if [[ "${name}" == release/* ]]
then
    echo "'${name}' starts with 'release/'"
else
    echo "'${name}' doesn't starts with 'release/'"
fi