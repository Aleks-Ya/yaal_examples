#!/usr/bin/env bats

@test "String equals" {
  name="gvc-3"
  [[ "${name}" == "gvc-3" ]]
}
