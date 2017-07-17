#!/usr/bin/env bash

@test "If true" {
  v=true
  if ${v}
  then
    return 0
  else
    return 1
  fi
}

@test "If false" {
  v=false
  if ! ${v}
  then
      return 0
  else
      return 1
  fi
}
