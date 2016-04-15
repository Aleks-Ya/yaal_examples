#!/bin/bash

function branch {
    echo "$(echo $1 | grep -oP '(?<=>>>)\S*')"
}

echo "a=$(branch "Commit >>>master aa")"
echo "b=$(branch "Commit >>>master")"
echo "c=$(branch ">>>master abc")"
echo "d=$(branch ">>>master")"
