#!/usr/bin/env bats

# Double parentheses "(())" declare an arithmetic expansion.

@test "Init ZERO numeric variable" {
  n1=$((0))
  (( $n1 == 0 ))
}

@test "Init numeric variable" {
  n1=$((1))
  ((n2=1))
  let n3=1
  (( $n1 == 1 ))
  (( $n2 == 1 ))
  (( $n3 == 1 ))
}

@test "Increment from ZERO" {
  n=$((0))
  ((n=n+1))
  ((n++))
  ((n+=1))
  (( $n == 3 ))
}

@test "Increment from NOT ZERO" {
  n=$((1))
  ((n=n+1))
  ((n++))
  ((n+=1))
  (( $n == 4 ))
}
