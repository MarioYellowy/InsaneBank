CREATE database insaneBank;
USE insaneBank;

CREATE TABLE usuarios (
    usuario_id int PRIMARY KEY NOT NULL auto_increment,
    usuario_nombre VARCHAR(50),
    usuario_email VARCHAR(50),
    usuario_password VARCHAR(60)
);