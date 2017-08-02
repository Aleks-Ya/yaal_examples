#!/usr/bin/env bash

a="abc"
export | grep "a"
export a=bcd
export | grep "a"
declare -x b="gbhd"