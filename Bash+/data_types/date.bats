#!/usr/bin/env bats

@test "Get current date (timestamp)" {

  timestamp=$(date -d @1353294612 "+%Y-%m-%d %T")
  [[ "$timestamp" = "2012-11-19 07:10:12" ]]

  current_timestamp=$(date "+%Y-%m-%d %T")
  echo "Current timestamp: $current_timestamp"

}

@test "Get current date (with microseconds)" {

  timestamp=$(date -d @1353294612 "+%Y-%m-%d %T.%6N")
  [[ "$timestamp" = "2012-11-19 07:10:12.000000" ]]

  current_timestamp=$(date "+%Y-%m-%d %T.%6N")
  echo "Current timestamp with microseconds: $current_timestamp"

}
