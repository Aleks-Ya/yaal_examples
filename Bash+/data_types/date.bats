#!/usr/bin/env bats

@test "Get current date (timestamp)" {
  timestamp=$(date -d @1353294612 "+%Y-%m-%d %T")
  [[ "$timestamp" = "2012-11-19 07:10:12" ]]
  current_timestamp=$(date "+%Y-%m-%d %T")
  echo "Current timestamp: $current_timestamp"
}
