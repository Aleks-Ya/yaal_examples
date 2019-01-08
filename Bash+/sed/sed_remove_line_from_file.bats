#!/usr/bin/env bats

# Remove a line from a file

@test "Remove line from file" {
  file='/tmp/sed_remove_line_from_file.txt'
  echo -e "Hello, Sed world!\nBye" > $file
  sed -i '/Sed/d' $file
  content=$(<$file)
  [ "${content}" = "Bye" ]
}
