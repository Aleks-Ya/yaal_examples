#!/usr/bin/env bats

# "Command substitution" is "$()"

@test "One-line" {
  echo $(date)
}

@test "Multi-line" {
  echo $(
    echo First line
    echo Second line
  )
}
