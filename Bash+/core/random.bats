#!/usr/bin/env bats

@test "Generate single random number (between 0 and 32767)" {
  n=$RANDOM
  (( $n >= 0)) && (( $n <= 32767))
}

@test "Generate single random number (between MIN and MAX)" {
  MIN=5
  MAX=20
  DIV=$((MAX-MIN+1))
  n=$((MIN + RANDOM % DIV))
  (( $n >= $MIN)) && (( $n <= $MAX))
}

@test "Generate N random numbers (between MIN and MAX)" {
  MIN=5
  MAX=20
  DIV=$((MAX-MIN+1))
  for n in {1..5}; do
    n=$((MIN + RANDOM % DIV))
  	echo $n
  	(( $n >= $MIN)) && (( n <= $MAX))
  done
}

@test "Generate large stdout (fixed size)" {
  for n in {1..5}; do echo $RANDOM; done
  #return 1
}

@test "Generate large stdout (custom size)" {
  repeat=5
  for n in $(seq $repeat); do echo $RANDOM; done
  #return 1
}
