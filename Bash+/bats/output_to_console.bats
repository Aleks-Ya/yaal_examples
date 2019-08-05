#!/usr/bin/env bats

# Run with '--tap' option: "./output_to_console.bats -t"

@test "Success test" {
  echo "Output from bats test" >&3
}
