Protection et Gestion des Licences DLL


	------ Les Sources ------


Ce sont les sources des projets VisualStudio2022 générés sur ma machine.
Il est possible lorsque vous les ouvrez sur votre machine que les liens vers
les bilbiothèques soient cassés et/ou que les package nécessaires 
ne soient pas installés.

Ci dessous je définis les différentes dépendances à respecter en même temps 
que la description des dossiers.


--- MachineHardware ---

MachineHardware est une application console, donnant l'identifiant de la machine en
prenant en compte les composants définis.
Un "usage" a été ajouté, lancer l'exécutable avec -h ou --help.

Le chemin vers l'exécutable est 

Dans ce projet il faut installer le packages nugget:
System.Management


--- ProLicence ---

ProLicence est le dossier des sources de la DLL.
Le chemin vers le fichier de DLL est : ProLicence\bin\Debug\net6.0-windows\ProLicence.dll

Dans ce projet il faut installer les packages nugget:
starkbank-ecdsa
System.Management


--- ProLogiciel ---

ProLogiciel est une application console, affichant "Hello World" si le fichier de licence
dont le chemin est spécifié dans ProLogiciel.cs est valide.

Dans ce projet il faut installer les packages nugget:
starkbank-ecdsa
System.Management

Il faut inclure la dll ProLicence.dll.


--- LicenceMaker ---

LicenceMaker est un programme permettant de manière intéractive de créer 
un nouveau fichier de licence. En prenant compte d'une date de validité, 
il calcule l'identifiant machine et créer la licence dans un fichier licence.txt.

Dans ce projet il faut installer les packages nugget:
starkbank-ecdsa
System.Management
system.IO

Le package System.Security.Cryptography étant plus complexe à utiliser 
nous avons préférer garder starkbank-ecdsa.


	------ L'exécution ------


--- Création du fichier de licence

Lancez LicenceMaker.exe Définissez la date de validité que vous souhaitez
en respectant la syntaxe (exemple 25/10/2022) Attention aucune vérification
de format n'a été mis en place.
Le fichier de licence a maintenant été crée. Il est visible dans l'ouput
mais vous trouverez le fichier licence.txt dans le dossier courant.


--- Utilisation de la DLL

Dans votre projet logiciel, utilisez la DLL comme dans l'exemple 
ProLogiciel.cs fournit.

Dans un premier temps il faut créer une instance de LicenceChecker en 
indiquant le chemin vers le fichier de licence précédement crée.

Puis indiquer à LicenceChecker quels composants prendre en compte lors 
de la vérification de l'identifiant de la machine.

licenceChecker.setHardwareHashComposent(true, true, true, true, true);
Dans l'ordre : Interfaces réseaux, Carte Mère, Disques, Bios, Processeur.

Par défaut si l'instruction précédente n'est pas définie, tous les 
composants seront pris en compte.

Il suffira ensuite d'appeler la méthode isValid() sur l'instance de LicenceChecker afin de 
vérifier l'integrité du fichier de licence et la validité de la licence.


--- Notes ----

Les fichiers de clé publique, clé privée sont générés par les développeurs de la DLL
et non par les clients. Ainsi la clé publique de vérification est inscrite dans les 
sources de la DLL et la clé privée ne sera pas distribué aux client. 
Ici les deux fichiers sont fournit afin de pouvoir générer des licences. 

Il vous est possible de générer une pair de clés asymétrique avec les commandes 
suivantes Sous linux en utilisant openssl :

openssl ecparam -name secp256k1 -genkey -out private.pem
openssl ec -in private.pem -pubout -out public.pem














