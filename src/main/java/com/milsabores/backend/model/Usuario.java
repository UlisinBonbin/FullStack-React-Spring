package com.milsabores.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String nombre;

    @Column (nullable = false)
    private String apellido;

    @Column (nullable = false, unique = true)
    private String correo;

    @Column (nullable = false)
    private String contrasena;

    @Column (nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String rol; // valores: "USER" o "ADMIN"

}
