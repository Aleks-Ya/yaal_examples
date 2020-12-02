#!/usr/bin/env bats

# Replace substring from a file and store result in a variable.

@test "Replace first match" {
  file='/tmp/replace_substring_in_file.txt'
  echo "Hello, world!" > $file
  echo "Bye!" >> $file
  replaced=$(<$file)
  expected=$(echo -e 'Hello, world!\nBye!')
  [ "${replaced}" = "${expected}" ]
}
