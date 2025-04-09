package com.example.todolistbackend.repository;

import com.example.todolistbackend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Categoria findByNombre(String nombre); // Ejemplo de método de consulta personalizado
}