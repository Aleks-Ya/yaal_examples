#!/usr/bin/env bats

@test "Get file name from full name" {
	fullname='/tmp/my_file.txt'
	filename=$(basename -- "$fullname")
	[ "${filename}" = "my_file.txt" ]
}

@test "Get file name without extension and extension" {
	file='/tmp/my_file.txt'
	filename=$(basename -- "$file")
	name="${filename%.*}"
	extension="${filename##*.}"


	[ "${filename}" = "my_file.txt" ]
	[ "${name}" = "my_file" ]
	[ "${extension}" = "txt" ]
}
