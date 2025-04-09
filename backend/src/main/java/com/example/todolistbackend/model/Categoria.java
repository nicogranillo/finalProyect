package com.example.todolistbackend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoriaID;

    private String nombre;

    @OneToMany(mappedBy = "categoria")
    private List<Tarea> tareas; // Relación con la tabla Tarea

    // Constructores (sin argumentos, con argumentos)
    public Categoria() {}

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public Integer getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(Integer categoriaID) {
        this.categoriaID = categoriaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
}