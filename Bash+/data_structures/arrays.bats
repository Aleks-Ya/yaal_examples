#!/usr/bin/env bats

@test "Initialize an array by element" {
  names[0]='John'
  names[1]='Mary'
}

@test "Initialize an array once" {
  cities=('Moscow' 'Spb');
}

@test "Iterate an array" {
  cities=('Moscow' 'Spb');
  for city in "${cities[@]}"
  do
    [[ "$city" == 'Moscow' ]] || [[ "$city" == 'Spb' ]]
  done
}

@test "Get array's element by number" {
  cities=('Moscow' 'Spb');
  [ ${cities[0]} == 'Moscow' ]
  [ ${cities[1]} == 'Spb' ]
}

@test "Get all array's element as string" {
  cities=('Moscow' 'Spb');
  [[ ${cities[*]} == 'Moscow Spb' ]]
}

@test "Replace an array's element" {
  cities=('Moscow' 'Spb');
  [ ${cities[0]} == 'Moscow' ]
  cities[0]=London
  [ ${cities[0]} == 'London' ]
}

@test "Delete an array's element" {
  cities=('Moscow' 'Spb');
  [ ${#cities[@]} -eq 2 ]
  unset cities[0]
  [ ${#cities[@]} -eq 1 ]
  #Index 0 points to empty element
  [[ -z ${cities[0]} ]]
  [ ${cities[1]} == 'Spb' ]
}

@test "Delete whole array's element" {
  cities=('Moscow' 'Spb');
  [[ ! -z ${cities} ]]
  unset cities
  [[ -z ${cities[*]} ]]
}

@test "Array's size" {
  cities=('Moscow' 'Spb');
  [ ${#cities[@]} -eq 2 ]
}

@test "Add an element to an exist array" {
  girls=('Mary' 'Nina');
  girls=("${girls[@]}" 'Laura' 'Gil');
  [ ${#girls[@]} -eq 4 ]
}

@test "Does array contains the element?" {
  girls=('Mary' 'Nina');
  exist_name='Nina'
  not_exist_name='Ann'
  [[ " ${girsl[@]} " =~ " ${exist_name} " ]]
  [[ ! " ${girls[@]} " =~ " ${not_exist_name} " ]]
}

