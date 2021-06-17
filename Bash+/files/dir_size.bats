#!/usr/bin/env bats

@test "Show directory size in bytes" {
	dir=.
	echo "Dir: $dir" >&3
	size=$(du -sb $dir | cut -f1)
	echo "Dir size: $size" >&3
	[[ "$size" > 0 ]]
}
