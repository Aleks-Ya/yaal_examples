#!/usr/bin/env bats

@test "more" {
    a=1
    b=2
    if [[ "${b}" > "${a}" ]]; then
        return 0
    else
        return 1
    fi
}

@test "more or equals" {
    a=2
    b=2
    if (( "${b}" >= "${a}" )); then
        return 0
    else
        return 1
    fi
}