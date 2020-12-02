#!/usr/bin/env bats

@test "Append line to a file (with tail empty line)" {
  file='/tmp/append_line_to_file.txt'
  echo "Line A" > $file
  echo "Appended line B" >> $file
  echo >> $file
  appended=$(<$file)
  expected=$(echo -e 'Line A\nAppended line B\n')
  [ "${appended}" = "${expected}" ]
}
