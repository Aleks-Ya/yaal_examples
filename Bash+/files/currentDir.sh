#!/usr/bin/env bats

BASEDIR=$(dirname "$0")
echo "dir: $BASEDIR"

SCRIPT=$(readlink -f "$0")
echo "$SCRIPT"

SCRIPTPATH=$(dirname "$SCRIPT")
echo "$SCRIPTPATH"

SCRIPTPATH2=$(dirname "$(readlink -f "$0")")
echo "$SCRIPTPATH2"
