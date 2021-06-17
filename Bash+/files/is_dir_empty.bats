#!/usr/bin/env bats

@test "Not empty directory" {
	dir=.
	if [ -z "$(ls -A $dir)" ]; then
	   empty=true
	else
	   empty=false
	fi
	[[ "$empty" == false ]]
}

@test "Empty directory" {
	dir=$(mktemp -d)
	if [ -z "$(ls -A $dir)" ]; then
	   empty=true
	else
	   empty=false
	fi
	[[ "$empty" == true ]]
}
