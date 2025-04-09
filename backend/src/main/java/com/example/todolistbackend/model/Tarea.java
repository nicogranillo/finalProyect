package com.example.todolistbackend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tarea")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tareaID;

    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private Date fechaDeInicio;

    private Date fechaDeFinalizacion;

    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "categoriaID")
    private Categoria categoria; // Relación con la tabla Categoria

    @ManyToOne
    @JoinColumn(name = "usuarioID")
    private Usuario usuario;   // Relación con la tabla Usuario

    // Constructores (sin argumentos, con argumentos)
    public Tarea() {}

    public Tarea(String nombre, String descripcion, Date fechaDeInicio, Date fechaDeFinalizacion, Boolean estado, Categoria categoria, Usuario usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaDeInicio = fechaDeInicio;
        this.fechaDeFinalizacion = fechaDeFinalizacion;
        this.estado = estado;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    // Getters y setters
    // (Generados por tu IDE o escritos manualmente)
}