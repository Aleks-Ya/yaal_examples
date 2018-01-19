#!/usr/bin/env bats

@test "And Strings" {
  city="Paris"
  [[ "$city" != 'Moscow' ]] && [[ "$city" != 'SPb' ]]
}

@test "And Numbers" {
  num="200"
  (( "$num" >= 200 )) && (( "$num" < 300 ))
}
