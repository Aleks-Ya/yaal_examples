#!/usr/bin/env bats

# Replace substring from a file and store result in a variable.

@test "Replace first match" {
  file='/tmp/sed_first.txt'
  echo "Hello, Sed Sed world!" > $file
  replaced=$(sed 's/Sed/Nano/' $file)
  [ "${replaced}" = "Hello, Nano Sed world!" ]
}

@test "Replace all matches" {
  file='/tmp/sed_all.txt'
  echo "Hello, Sed Sed world!" > $file
  replaced=$(sed 's/Sed/Nano/g' $file)
  [ "${replaced}" = "Hello, Nano Nano world!" ]
}

@test "Another delimiter" {
  file='/tmp/sed_delimiter.txt'
  echo "Hello, /opt/hadoop /opt/hadoop world!" > $file
  replaced=$(sed 's@/opt/hadoop@Nano@g' $file)
  [ "${replaced}" = "Hello, Nano Nano world!" ]
}


