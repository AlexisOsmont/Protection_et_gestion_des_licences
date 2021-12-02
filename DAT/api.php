<?php

// Interface permettant de manipuler les licences
interface License
{
    public static function getLicense(int $clientID, int $softwareID): object;

    public function __construct(int $clientID, int $softwareID);

    public function create(int $status, ?string $dateValidity,
            ?int $trialNumber): int;

    public function revoke(): int;

    public function getStatus(): int;
 
    public function setStatus(int $status): int;

    public function getValidity(): string;
    
    public function setValidity(string $dateValidity);
        
    public function getTrialNumber(): int;

    public function setTrialNumber(int $trialNumber): int;
}

// Interface permettant de manipuler les utilisateurs
interface Client
{
    public static function getClient(int $clientID): object;
    
    public function __construct();

    public function create(string $clientMail, string $clientPassword,
            ?string $clientName): int;

    public function delete(): int;

    public function getID(): int;

    public function getMail(): string;

    public function getPassword(): string;

    public function getName(): string;

    public function setMail(string $newMail): int;

    public function setPassword(string $newPassword): int;

    public function setName(string $newClientName): int;
}

// Interface permettant de manipuler les utilisateurs
interface Admin
{
    public static function getAdmin(int $adminID): object;

    public function __construct();

    public function create(string $adminMail, string $adminPassword,
            ?string $adminName): int;

    public function delete(): int;

    public function getID(): int;

    public function getMail(): string;

    public function getPassword(): string;

    public function getName(): string;

    public function setMail(string $newMail): int;

    public function setPassword(string $newPassword): int;

    public function setName(string $newAdminName): int;
}


// Interface permettant de manipuler les logiciels
interface Software
{
    public static function getSoftware(int $softwareID): object;

    public function __construct();

    public function create(string $softwareName, string $softwareDesc): int;

    public function delete(): int;

    public function getID(): int;

    public function getName(): string;

    public function getDesc(): string;

    public function setName(string $newName): int;

    public function setDesc(string $newDesc): int;
}
?>
