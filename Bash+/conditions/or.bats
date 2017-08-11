#!/usr/bin/env bats

@test "Or" {
  city="SPb"
  [[ "$city" == 'Moscow' ]] || [[ "$city" == 'SPb' ]]
}
