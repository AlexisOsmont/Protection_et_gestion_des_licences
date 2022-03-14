-- Voir les utilisateurs
-- select user, password from mysql.user;

-- Création de l'utilisateur tomcat
CREATE USER tomcat IDENTIFIED BY 'tomcatpasswd';

-- Création de la base de donnée auth
CREATE DATABASE auth;
use auht;

-- Création de la table utilisateurs auth
CREATE TABLE users (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	username varchar(50),
	email varchar(50),
	password varchar(50)
);

insert into users (username, email, password) values ('admin', 'admin@admin.fr', 'adminpasswd');
insert into users (username, email, password) values ('client', 'client@client.fr', 'clientpasswd');

-- Création de la table des tickets

CREATE TABLE tickets (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	hash varchar(30),
	usr INT,
	FOREIGN KEY (usr) REFERENCES users(id) ON DELETE CASCADE
);

-- Donnons les droits à tomcat sur users
GRANT ALL PRIVILEGES ON auth.users TO tomcat;
GRANT ALL PRIVILEGES ON auth.tickets TO tomcat;
FLUSH PRIVILEGES;
