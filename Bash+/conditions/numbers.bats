#!/usr/bin/env bats

# "()" is an arithmetic context

@test "greater (variables)" {
    a=1
    b=2
    (( $b > $a ))
    [[ "${b}" > "${a}" ]]
    [[ "${b}" -gt "${a}" ]]
}

@test "greater (literal)" {
    a=1
    (( $a > 0 ))
    [[ "${a}" > 0 ]]
}

@test "greater (negative)" {
    a=-1
    (( $a > -2 ))
    [[ "${a}" -gt -2 ]]
}

@test "greater or equal" {
    a=2
    b=2
    (( "${b}" >= 2 ))
}
