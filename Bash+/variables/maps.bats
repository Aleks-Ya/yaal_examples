#!/usr/bin/env bats

@test "Put and get by key" {
  declare -A tableLimits #make NAMEs associative array
  tableLimits['Pharma']='100'
  tableLimits['Vitro']='300'
  [ ${tableLimits['Pharma']} == '100' ]
  [ ${tableLimits['Vitro']} == '300' ]
}
