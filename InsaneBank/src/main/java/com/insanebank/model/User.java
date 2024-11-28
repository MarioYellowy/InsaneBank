package com.insanebank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioID;

    @Column(name = "usuario_nombre")
    private String usuarioNombre;

    @Column(name = "usuario_email")
    private String email;

    @Column(name = "usuario_password")
    private String password;
}
