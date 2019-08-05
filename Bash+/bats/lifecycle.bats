#!/usr/bin/env bats

setup() {
  echo "Setup invoked"
}

@test "Success test" {
  return 0
}

@test "Fail test" {
  return 1
}

@test "Addition using dc" {
  result="$(echo 2 2+p | dc)"
  [ "$result" -eq 4 ]
}

@test "Ignored test" {
  skip
  return 1
}

teardown() {
  echo "Teardown invoked"
}
