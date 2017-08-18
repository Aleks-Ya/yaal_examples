#!/usr/bin/env bats

@test "Extract branch name" {
  function extractBranch {
    regex="(?<=>>>)\S*"
      echo "$(echo $1 | grep -oP $regex)"
  }
  [ "$(extractBranch "Commit >>>master aa")" = "master" ]
  [ "$(extractBranch "Commit >>>master")" = "master" ]
  [ "$(extractBranch ">>>master abc")" = "master" ]
  [ "$(extractBranch ">>>master")" = "master" ]
}
