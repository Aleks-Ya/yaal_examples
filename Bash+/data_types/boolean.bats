#!/usr/bin/env bats

@test "Boolean literal" {
  yes=true
  no=false
  [[ "$yes" = true ]] && [[ "$no" == false ]]
}
