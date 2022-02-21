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

-- Donnons les droits à tomcat sur users
GRANT ALL PRIVILEGES ON auth.users TO tomcat;
FLUSH PRIVILEGES;
