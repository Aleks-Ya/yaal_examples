#!/usr/bin/env bats

@test "Get current date (timestamp)" {
  timestamp=$(date -d @1353294612 "+%Y-%m-%d %T")
  current_timestamp=$(date "+%Y-%m-%d %T")
  echo "Current timestamp: $current_timestamp"  >&3
}

@test "Get current date (with microseconds)" {
  timestamp=$(date -d @1353294612 "+%Y-%m-%d %T.%6N")
  current_timestamp=$(date "+%Y-%m-%d %T.%6N")
  echo "Current timestamp with microseconds: $current_timestamp"  >&3
}
