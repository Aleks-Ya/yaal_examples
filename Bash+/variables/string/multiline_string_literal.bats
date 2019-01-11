#!/usr/bin/env bats

@test "By array" {
  lines=(abc
      def
      ghi)
  IFS=''
  long_line="${lines[*]}"
  [ "${long_line}" = "abcdefghi" ]
}

@test "By quotes" {
  long_line="abc"`
             `"def"`
             `"ghi"
  [ "${long_line}" = "abcdefghi" ]
}
