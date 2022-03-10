#!/usr/bin/bash

#---------- Create key pair -------------

# Create private key
openssl ecparam -genkey -name secp521r1 -out private.pem -noout
# Convert to PKCS8 format (to be readable with Java)
openssl pkcs8 -topk8 -nocrypt -in private.pem -out pkcs8-private.pem

# Create public key
openssl ec -in pkcs8-private.pem -pubout -out public.pem

#--------------- Test -------------------

# Sign
# openssl dgst -sha256 -sign private.pem < testfile.txt > file.sign

# Verify
# openssl dgst -sha256 -verify public.pem -signature file.sign < testfile.txt
