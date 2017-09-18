#!/usr/bin/env bats

@test "Print string to stdout" {
  cities=('Moscow' 'Spb');
  echo "cities"
  #echo "cities=$cities"
#  echo $cities | readarray arr
#  echo "arr=$arr"
  exit 1
}

@test "Split by lines (using 'readarray')" {
  x=$'Some\nOne'
  #readarray -t arr <<< "$x"
  echo "$x" | readarray -t arr
  [ "${arr[0]}" = "Some" ]
  [ "${arr[1]}" = "One" ]
}
