#!/usr/bin/env bats

@test "Access to environment variable by dynamic name" {
  name='myvar'
  expValue='abc'
  export $name=$expValue

  actValue="${!name}"

  [[ "${actValue}" == "${expValue}" ]]
}
