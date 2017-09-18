#!/usr/bin/env bats

setup() {
  str="Feature/Test-8"
}

@test "Replace 8 with 9" {
  [ "${str/8/9}" = "Feature/Test-9" ]
}

@test "Replace '/' with '-'" {
  [ "${str////-}" = "Feature-Test-8" ]
}

@test "Interval from 8 char to the end" {
  [ "${str:8}" = "Test-8" ]
}

@test "To uppercase" {
  [ "${str^^}" = "FEATURE/TEST-8" ]
}

@test "Split by lines (using 'readarray')" {
  x=$'Some\nOne'
  readarray -t arr <<< "$x"
  [ "${arr[0]}" = "Some" ]
  [ "${arr[1]}" = "One" ]
}

@test "Split text by lines (using 'read')" {
  string=$'Some\nthing'
  arr=()
  while read -r line; do
     arr+=("$line")
  done <<< "$string"
  [ "${arr[0]}" = "Some" ]
  [ "${arr[1]}" = "thing" ]
}
