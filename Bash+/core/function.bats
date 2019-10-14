#!/usr/bin/env bats

@test "Error in function" {
  skip "Finish me"
  function func {
      exit 1
  }
  func
}

@test "Invoke function" {
  v=false
  function func {
      v=true
  }
  func
  $v
}

@test "Invoke function with parameters" {
  param1=false
  param2=abc
  function func() {
      param1=$1
      param2=$2
  }
  func hi bye
  [[ "${param1}" == "hi" ]] && [[ "${param2}" == "bye" ]]
}

@test "Iterate parameters of function" {
  function func() {
    for var in "$@"
    do
        echo "$var"
    done
  }
  func hi bye
}
