#!/usr/bin/env bash

branch="master"
isMaster=$([ "$branch" == 'master' ] && echo true || echo false)
echo ${isMaster}