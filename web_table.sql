-- drop object if they already exists
drop user if exists tomcat;
drop database if exists web;

-- create user and database
create user tomcat identified by 'tomcatpasswd';
create database web;
use web;

-- create table
create table Admin (
    AdminId int primary key not null auto_increment,
    AdminUsername varchar(50),
    AdminEmail varchar(50) not null
);

create table Client (
    ClientId int primary key not null auto_increment,
    ClientUsername varchar(50),
    ClientEmail varchar(50) not null
);

create table Software (
    SoftwareId int primary key not null auto_increment,
    SoftwareName varchar(50) not null,
    SoftwareDesc varchar(200),
    SoftwarePrice int not null,
    SoftwarePriceMultiplier int not null,
    SoftwareImg blob 
);

create table Licence (
    LicenceId int primary key not null auto_increment,
    LicenceHardwareId varchar(100),
    LicenceStatus int not null,
    LicenceValidy date,
    ClientId int not null,
    SoftwareId int not null,
    constraint FK_LicenceClient foreign key (ClientId)
    references Client(ClientId),
    constraint FK_LicenceSoftware foreign key (SoftwareId)
    references Software(SoftwareId)
);

-- grant privileges to tomcat on table
grant all privileges on web.Admin to tomcat;
grant all privileges on web.Client to tomcat;
grant all privileges on web.Software to tomcat;
grant all privileges on web.Licence to tomcat;    
flush privileges;

-- insert values

-- Software
insert into web.Software values (1, 'Logiciel de Comptabilité', 'Logiciel permettant de gérer des fiches de payes pour vos employés', 100, 1, NULL);
insert into web.Software values (2, 'Super-ERP', 'Super logiciel de gestion de ressource pour votre entreprise', 200, 2, NULL);
insert into web.Software values (3, 'Healt-Care.exe', 'Logiciel permettant de gérer les feuilles de soin des patients', 600, 1, NULL);
insert into web.Software values (4, 'HeadInTheCloud', 'Solution de gestion de cloud', 150, 4, NULL);
insert into web.Software values (5, 'BlockAndChain', 'Logiciel permettant de miner du dogecoin', 800, 1, NULL);

-- Client
insert into web.Client values (1, 'client', 'client@client.fr');

-- Admin
insert into web.Admin values (1, 'admin', 'admin@admin.fr');

