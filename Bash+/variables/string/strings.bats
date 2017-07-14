#!/usr/bin/env bash

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
