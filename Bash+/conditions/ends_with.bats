#!/usr/bin/env bats

@test "Ends with" {
	str="home/test/blah/blah"
	end="blah"
	[[ $str == */${end} ]] 
}
