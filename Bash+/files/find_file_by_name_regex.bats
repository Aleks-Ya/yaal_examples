#!/bin/bash

@test "Find file by regex" {
    file=$(find . -name 'find_file_by_name_regex-*.txt')
  [[ "${file}" == "./find_file_by_name_regex-1.2.3-SNAPHOT.txt" ]]
}