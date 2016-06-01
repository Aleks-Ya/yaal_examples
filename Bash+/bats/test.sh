#!/usr/bin/env bats

@test "success" {
  return 0
}

@test "fail" {
  return 1
}

@test "addition using dc" {
  result="$(echo 2 2+p | dc)"
  [ "$result" -eq 4 ]
}