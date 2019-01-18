#!/usr/bin/env bats

@test "Starts with" {
  #comment="81095d5 test commit >>>release/yaal-test3"
  comment="81095d5 test commit "
  currentBranch="yaal-test4"
  destinationBranch=$(echo ${comment} | grep -oP '(?<=>>>)\S*')

  echo "Last comment: '$comment'"
  echo "Bamboo branch: '$currentBranch'"
  echo "Destination branch: '$destinationBranch'"

  [ "$currentBranch" != 'master' ] && [ "$currentBranch" != release/* ] && [ "$comment" != *"Nonsnapshot Plugin:"* ] && [ ! -z ${destinationBranch} ]
}
