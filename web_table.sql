create user tomcat identified by 'tomcatpasswd';
create database web;
use web;

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
    SoftwareDesc varchar(200)    
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
    
