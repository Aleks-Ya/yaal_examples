#!/usr/bin/env bats

# Install jq: sudo apt install jq
@test "Extract value of a JSON property" {
  result=$(echo '{"count":2}' | jq -r '.count')
  [ "$result" -eq 2 ]
}
