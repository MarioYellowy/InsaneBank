CREATE database insane_bank;
USE insane_bank;

CREATE TABLE usuarios (
    usuario_id int PRIMARY KEY NOT NULL auto_increment,
    nombre_usuario VARCHAR(50),
    mail VARCHAR(50),
    contraseña VARCHAR(60)
);