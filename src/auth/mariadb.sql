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
	username varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
	password varchar(200) NOT NULL
);

-- pass : adminpasswd
insert into users (username, email, password) values ('admin', 'admin@admin.fr', '$2a$10$9WGaMnjPnz9Jf5XgUU.PhevWL1sB4t5abL/6bmB6mR/NeaPk4M10K');

-- pass : clientpasswd
insert into users (username, email, password) values ('client', 'client@client.fr', '$2a$10$t0wAB0/syD19wqEfk6Nq0uZkXOroZ9IB98taPs55fNcj8CIzVvYXC');

-- Création de la table des tickets

CREATE TABLE tickets (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	hash varchar(30) NOT NULL,
	usr INT NOT NULL,
	epoch int(11) NOT NULL,
	FOREIGN KEY (usr) REFERENCES users(id) ON DELETE CASCADE
);

-- Donnons les droits à tomcat sur users
GRANT ALL PRIVILEGES ON auth.users TO tomcat;
GRANT ALL PRIVILEGES ON auth.tickets TO tomcat;
FLUSH PRIVILEGES;
