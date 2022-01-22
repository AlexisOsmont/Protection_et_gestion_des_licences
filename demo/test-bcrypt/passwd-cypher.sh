#!/bin/bash

cost=10
user=louka
password=Password1234@

passwd_hash=$(htpasswd -nbBC $cost $user $password)

# hash : username:$method$cost$randomsalthash
# method : 2y -> len($2y$) = 4
# len(randomsalt) = 22
# len(hash) = 31
start_idx=$((${#user} + 2))
end_idx=$(($start_idx + 3 + ${#cost} + 1 + 22))

salt=$(echo $passwd_hash | cut -c $start_idx-$end_idx)

echo "Salt : $salt"
echo "Hash : $passwd_hash"

