#!/usr/bin/env bats

@test "Trim leading and trailing spaces" {
  str="  abc  "
  trimmed=`echo $str | xargs`
  [ "${trimmed}" = "abc" ]
}

@test "Delete trailing empty line" {
  skip "Not finished"
  str="abc \nxyz\n\n"
  echo -e "str=$str"
  trimmed=`echo $str | xargs`
  echo -e "trimmed='$trimmed'"
  [ "${trimmed}" = "abc \n xyz" ]
}
