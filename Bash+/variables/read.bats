#!/usr/bin/env bats

@test "Split text by lines" {
  string=$'Some\nthing'
  arr=()
  read -r line <<< "$string"
  echo "line=$line"
  [ "$line" = "Some" ]
}
