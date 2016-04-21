#!/bin/bash

function branch {
    echo exit1
    exit 1
}
function b2 {
    branch
}

b2

echo "The end"
