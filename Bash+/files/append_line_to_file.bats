#!/usr/bin/env bats

# Replace substring from a file and store result in a variable.

@test "Replace first match" {
  file='/tmp/append_line_to_file.txt'
  echo "Hello, world!" > $file
  echo "Bye!" >> $file
  appended=$(<$file)
  expected=$(echo -e 'Hello, world!\nBye!')
  [ "${appended}" = "${expected}" ]
}
