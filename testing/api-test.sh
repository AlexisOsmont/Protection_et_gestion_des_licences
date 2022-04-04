#!/bin/bash

# request licence tests
url=https://srv-dpi-proj-gestlic-web.univ-rouen.fr:8443
method=POST
path=/api/v1/Licence/requestLicence

headers="Content-Type: application/json"

#--- CORRECT REQUEST -----

body="[{\"UserMail\": \"client@client.fr\", \"UserPassword\": \"clientpasswd\", \"SoftwareId\": 1, \"HardwareHash\": \"SBAYIDIFHBUZEFYGCJE\"}]"

expected=200
result=$(curl -o /dev/null -w "%{http_code}" --insecure -X "$method" -H "$headers" -d "$body" $url$path 2> /dev/null)

echo "Correct request :"
echo "Expected: $expected, Obtained: $result"
if [[ $expected == $result ]]; then
    echo "OK"
else
    echo "NOT OK"
fi
echo

#--- INVALID CREDENTIALS -----

body="[{\"UserMail\": \"client@client.fr\", \"UserPassword\": \"clientpassw\", \"SoftwareId\": 1, \"HardwareHash\": \"SBAYIDIFHBUZEFYGCJE\"}]"

expected=401
result=$(curl -o /dev/null -w "%{http_code}" --insecure -X "$method" -H "$headers" -d "$body" $url$path 2> /dev/null)

echo "Request with invalid credentials :"
echo "Expected: $expected, Obtained: $result"
if [[ $expected == $result ]]; then
    echo "OK"
else
    echo "NOT OK"
fi
echo

#--- INVALID JSON -----

body="[{\"UserMail\": \"client@client.fr\", \UserPassword\": \"clientpasswd\", \"SoftwareId\": 1, \"HardwareHash\": \"SBAYIDIFHBUZEFYGCJE\"}]"

expected=400
result=$(curl -o /dev/null -w "%{http_code}" --insecure -X "$method" -H "$headers" -d "$body" $url$path 2> /dev/null)

echo "Request with invalid body (JSON syntax) :"
echo "Expected: $expected, Obtained: $result"
if [[ $expected == $result ]]; then
    echo "OK"
else
    echo "NOT OK"
fi
echo

#--- INVALID JSON ATTRIBUTES -----

body="[{\"UserMail\": \"client@client.fr\", \"Userpassword\": \"clientpasswd\", \"SoftwareId\": 1, \"HardwareHash\": \"SBAYIDIFHBUZEFYGCJE\"}]"

expected=500
result=$(curl -o /dev/null -w "%{http_code}" --insecure -X "$method" -H "$headers" -d "$body" $url$path 2> /dev/null)

echo "Request with invalid body (JSON attributes) :"
echo "Expected: $expected, Obtained: $result"
if [[ $expected == $result ]]; then
    echo "OK"
else
    echo "NOT OK"
fi
echo

#--- INVALID JSON -----

body="{\"UserMail\": \"client@client.fr\", \"UserPassword\": \"clientpasswd\", \"SoftwareId\": 1, \"HardwareHash\": \"SBAYIDIFHBUZEFYGCJE\"}"

expected=500
result=$(curl -o /dev/null -w "%{http_code}" --insecure -X "$method" -H "$headers" -d "$body" $url$path 2> /dev/null)

echo "Request with invalid body (Not a JSON array) :"
echo "Expected: $expected, Obtained: $result"
if [[ $expected == $result ]]; then
    echo "OK"
else
    echo "NOT OK"
fi
echo

