package com.milsabores.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Producto {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String nombre;

    @Column (nullable = false)
    private Double precio;

    @Column (nullable = false)
    private String imagenUrl;

    @OneToMany(mappedBy = "producto")
    private List<DetalleCompra> detalles;

}
