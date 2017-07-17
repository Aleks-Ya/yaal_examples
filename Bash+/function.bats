#!/bin/bash

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
