#!/bin/bash

set -e
create_user() {
	group=$1
	user=$2
	echo "Creating user '$group:$user'..."
	if id "$user" &>/dev/null; then
		echo "User $group:$user already exists"
	else
	    useradd -m -g $group $user
	    echo "User $group:$user is created"
	fi	
}