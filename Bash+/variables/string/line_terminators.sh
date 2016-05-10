#!/usr/bin/env bash

str="a\nb"
echo "Ignore line terminators: ${str}"
echo -e "Use line terminators: ${str}"

str2="\nc"
concated="$str$str2"
echo -e "Concat strings with line terminators: $concated"
