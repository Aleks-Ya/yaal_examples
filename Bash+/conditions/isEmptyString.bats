#!/usr/bin/env bats

@test "Is string empty (variable exists)?" {
  name=""
  [[ -z ${name} ]]
}

@test "Is string empty (variable doesn't exist)?" {
  [[ -z ${not_exist_var} ]]
}

@test "Is string not empty?" {
  name="release/gvc-3"
  [[ ! -z ${name} ]]
}
