#!/usr/bin/env bats

@test "Start with" {
	str="home/test/blah/blah"
	start="home"
	[[ $str == ${start}* ]] 
}