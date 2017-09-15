#!/usr/bin/env bats

@test "test" {
  branch="master"
  isMaster=$([ "$branch" == 'master' ] && echo true || echo false)
  echo ${isMaster}
}
