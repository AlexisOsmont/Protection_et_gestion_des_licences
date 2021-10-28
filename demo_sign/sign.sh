#!/bin/bash

echo -e "\n===== Generating (Private key, Public key) ====="
openssl genrsa -out private.pem 1024   #generate private key file
#openssl rsa -in private.pem -text      #view info in the private key file
openssl rsa -in private.pem -pubout -out public.pem  #extract public key to file
#openssl rsa -in public.pem -pubin -text  #view info in the public key file

cat /proc/sys/kernel/random/uuid > msg.txt
echo -e "\n===== File to sign ====="
cat msg.txt

openssl dgst -sha256 -sign private.pem -out sign.sha256 msg.txt
base64 sign.sha256 > sign.txt

echo -e "\n===== Signature (Encoded in B64) ====="
cat sign.txt

base64 -d sign.txt > sign.sha256
echo -e "\n===== Verification ====="
openssl dgst -sha256 -verify public.pem -signature sign.sha256 msg.txt

echo -e "\n===== Changing Content of Msg.txt ====="
cat /proc/sys/kernel/random/uuid > msg.txt
cat msg.txt

echo -e "\n===== Verification ====="
openssl dgst -sha256 -verify public.pem -signature sign.sha256 msg.txt

echo -e "\n===== Cleaning ====="
rm *.pem *.txt *.sha256
echo -e "done" 
