#!/usr/bin/env bats

setup() {
  str="Feature/Test-8"
}

@test "Replace first 8 with 9" {
  [ "${str/8/9}" = "Feature/Test-9" ]
}

@test "Replace all e with E" {
  [ "${str//e/E}" = "FEaturE/TEst-8" ]
}

@test "Replace all . with /" {
  str="com.java.io"
  [ "${str//.//}" = "com/java/io" ]
}

@test "Replace '/' with '-'" {
  [ "${str////-}" = "Feature-Test-8" ]
}

@test "Interval from beginning to 5th char" {
  [ "${str:0:5}" = "Featu" ]
}

@test "Interval from beginning to out of string length" {
  [ "${str:0:50}" = $str ]
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


@test "String length (not empty)" {
  s="abcd"
  l=${#s}
  (( $l == 4 ))
}

@test "String length (empty)" {
  s=""
  l=${#s}
  (( $l == 0 ))
}

@test "String length (not defined)" {
  l=${#not_exists}
  (( $l == 0 ))
}