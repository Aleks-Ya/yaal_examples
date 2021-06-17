#!/usr/bin/env bats

@test "Create temporary directory" {
	dir=$(mktemp -d)
	[[ -d "$dir" ]]
}

@test "Create temporary file" {
	file=$(mktemp)
	[[ -f "$file" ]]
}
