<?php
// Pour chacune des tables de la base de données, une classe de modèle permet de
// créer, consulter et modifier des objets correspondant à un élément de la
// table, cela correspond au classe : License, Client, Admin et Software.
//
// Ensuite pour chacune de ces classes il y a une classe correspondante qui
// permet d'effectuer les interactions avec la base de données MySQL. Ces
// classes possèdent toutes 4 fonctions pour récupérer, insérer, supprimer et
// modifier les éléments de la table.
// Les ID étant crées dans la base de données à l'insertion, les fonctions
// permettant de récupérer les objets depuis la table prennent en paramètres
// un autre attribut permettant de les identifier (email pour les client et les
// administrateurs, nom pour les logiciels).

interface License
{
    public function __construct(int $clientID, int$softwareID, int $status,
            ?string $dateValidity, ?int $trialNumber); 

    public function getClientID(): int;

    public function getSoftwareID(): int;

    public function getStatus(): int;
 
    public function setStatus(int $status): int;

    public function getValidity(): string;
    
    public function setValidity(string $dateValidity);
        
    public function getTrialNumber(): int;

    public function setTrialNumber(int $trialNumber): int;
}

interface LicenseTable
{
    public function getLicense(int $clientID, int $softwareID): License;

    public function insert(License $license): int;

    public function revoke(License $license): int;

    public function update(License $license): int;
}

interface Client
{
    public function __construct(string $clientMail, string $clientPassword,
            ?string $clientName);

    public function getID(): int;

    public function getMail(): string;

    public function getPassword(): string;

    public function getName(): string;

    public function setMail(string $newMail): int;

    public function setPassword(string $newPassword): int;

    public function setName(string $newClientName): int;
}

interface ClientTable
{
    public function getClient(int $clientMail): Client;

    public function insert(Client $client): int;

    public function delete(Client $client): int;

    public function update(Client $client): int;
}

interface Admin
{
    public function __construct(string $adminMail, string $adminPassword,
            ?string $adminName);

    public function getID(): int;

    public function getMail(): string;

    public function getPassword(): string;

    public function getName(): string;

    public function setMail(string $newMail): int;

    public function setPassword(string $newPassword): int;

    public function setName(string $newAdminName): int;
}

interface AdminTable
{
    public function getAdmin(int $adminMail): Admin;

    public function insert(Admin $admin): int;

    public function delete(Admin $admin): int;

    public function update(Admin $admin): int;
}

interface Software
{
    public function __construct(string $softwareName,
            string $softwareDesc): int;

    public function getID(): int;

    public function getName(): string;

    public function getDesc(): string;

    public function setName(string $newName): int;

    public function setDesc(string $newDesc): int;
}

interface SoftwareTable
{
    public function getSoftware(int $softwareName): Software;

    public function insert(Software $software): int;

    public function delete(Software $software): int;

    public function update(Software $software): int;
}
?>
