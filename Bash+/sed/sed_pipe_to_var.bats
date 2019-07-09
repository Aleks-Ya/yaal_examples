#!/usr/bin/env bats

# Pipe ("echo") to var 

@test "Pipe to var" {
  text="Hello, Sed Sed world!"
  replaced=$(echo $text | sed 's/Sed/Nano/')
  [ "${replaced}" = "Hello, Nano Sed world!" ]
}
