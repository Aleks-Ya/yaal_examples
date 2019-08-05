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

@test "Extract disk usage" {
  function extractDiskUsage {
    regex="([0-9]+)?(?=%)"
      echo "$(echo $1 | grep -oP $regex)"
  }
    [ "$(extractDiskUsage " 54%")" = "54" ]
    [ "$(extractDiskUsage " 100%")" = "100" ]
    [ "$(extractDiskUsage " 0%")" = "0" ]
}

@test "Extract groupId from MANIFEST.MF" {
  function extractGroupId {
    regex="Implementation-Vendor-Id: .*"
      echo "$(echo $1 | grep -oP $regex)"
  }
  mainfest_content="abcImplementation-Title: JCP"`
                  `"Release-Version: 2.0.39267"`
                  `"Implementation-Vendor-Id: ru.CryptoPro"

  result=`extractGroupId $mainfest_content`
  echo "res: $result"
  [ "$(extractGroupId "$mainfest_content")" = "ru.CryptoPro" ]
}
