#!/usr/bin/env bats

@test "Is string empty?" {
  name=""
  [[ -z ${name} ]]
}

@test "Is string not empty?" {
  name="release/gvc-3"
  [[ ! -z ${name} ]]
}
