#! /bin/bash

PRIVATE_KEY="private.pem"

echo "Génération d'un fichier de licence interactif."
echo -e "\n--------\n"

# Demande la date de validité
echo "Date de validité (exemple 31/12/2999) : "
read validityDate
echo

# Demande la date d'expiration de licence
echo "Hardware Hash (Generated with MachineHardware) :"
read hardwareHash
echo

echo "Licence content : "
licenceContent=\
"{
	\"hardwareid\":\"$hardwareHash\",
	\"validity\":\"$validityDate\"
}"
echo -e "${licenceContent}"

licb64=$(echo -n $licenceContent | base64 -w0)
echo -e "base64 :\n${licb64}\n"

signb64=$(echo -n $licb64 | openssl dgst -sha256 -sign $PRIVATE_KEY | base64 -w0)
echo -e "signature base64 :\n${signb64}\n"

licence=\
"###licence#
$licb64
###signature#
$signb64
###eof#" 

echo "Le fichier de licence a été généré -> licence.txt"
echo -e "\n--------\n"

echo -e "${licence}" > licence.txt
echo -e "${licence}"
