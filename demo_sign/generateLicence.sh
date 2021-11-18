#!/bin/bash

rights="until:19/11/2021"
hardware="$(cat /proc/sys/kernel/random/uuid)"
softwareID="123456"

echo -e "\n===== Generating (Private key, Public key) ====="
openssl genrsa -out private.pem 1024 2> /dev/null								 # generate private key file
openssl rsa -in private.pem -pubout -out public.pem 2> /dev/null # extract public key to file

echo -e "\n===== Exemple User's Input ====="
echo "rights:         $rights"
echo "Hash(hardware): $hardware"
echo "softwareID:     $softwareID"

echo -e "\n===== Creating 'X' (hardware:softwareID:rights)  ====="
toSign=$(echo -n "$hardware:$softwareID:$rights" | openssl dgst -binary -sha256 | openssl base64) 
echo $toSign

echo -e "\n===== Signing X (Encoded in B64) ====="
signature=$(echo $toSign | openssl dgst -sha256 -sign private.pem | openssl base64)
echo $signature

echo -e "\n===== Creating Licence File ====="
echo -e "$signature\n$rights"

echo -e "\n===== Cleaning ====="
rm *.pem 
echo -e "done" 
