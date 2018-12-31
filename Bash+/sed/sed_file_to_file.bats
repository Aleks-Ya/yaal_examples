#!/usr/bin/env bats

# Replace substring from a file and store result in another file ("-i" option).

@test "Replace all matches" {
  file='/tmp/sed_file.txt'
  echo "Hello, Sed Sed world!" > $file
  sed -i 's/Sed/Nano/g' $file
  replaced=$(<$file)
  [ "${replaced}" = "Hello, Nano Nano world!" ]
}
