#!/bin/bash

echo -e "\n===== Generating (Private key, Public key) ====="
openssl genrsa -out private.pem 1024   #generate private key file
#openssl rsa -in private.pem -text      #view info in the private key file
openssl rsa -in private.pem -pubout -out public.pem  #extract public key to file
#openssl rsa -in public.pem -pubin -text  #view info in the public key file

cat /proc/sys/kernel/random/uuid > msg.txt
echo -e "\n===== File to cipher ====="
cat msg.txt

openssl rsautl -encrypt -inkey public.pem -pubin -in msg.txt -out encrypt.data
base64 encrypt.data > encrypted.txt

echo -e "\n===== Ciphered file (Encoded in B64) ====="
cat encrypted.txt

base64 -d encrypted.txt > encrypt.data
openssl rsautl -decrypt -inkey private.pem -in encrypt.data -out msg2.txt 

echo -e "\n===== Deciphered File ====="
cat msg2.txt
echo ""

diff -s msg.txt msg2.txt

echo -e "\n===== Cleaning ====="
rm *.pem *.txt *.data
echo -e "done" 
