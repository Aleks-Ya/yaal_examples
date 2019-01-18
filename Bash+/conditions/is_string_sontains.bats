#!/usr/bin/env bats

@test "Substring is a literal" {
  string='My long string'
  [[ $string == *"My long"* ]]
}

@test "Substring is variable" {
  string='My long string'
  substring='long'
  [[ $string == *"${substring}"* ]]
}

@test "Case insensitive search" {
  skip "Doesn't work. Fix it"
  string='My long string'
  substring='LONG'
  [[ $string == *"${substring}"* ]]
}
