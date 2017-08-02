#!/usr/bin/env bats

@test "Use backticks" {
  result=`pwd`
  result2=$(pwd)
  [ $result == $result2 ]
  [ $result == $PWD ]
}
