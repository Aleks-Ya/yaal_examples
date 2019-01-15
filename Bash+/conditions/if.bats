#!/usr/bin/env bats

@test "if clause" {
  value=1

  if [[ "$value" < 0 ]]; then
    result="negative"
  elif [[ "$value" > 0 ]]; then
    result="positive"
  else
    result="zero"
  fi

  [[ ${result} = "positive" ]]
}
