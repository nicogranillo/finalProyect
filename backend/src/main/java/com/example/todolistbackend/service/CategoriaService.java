package com.example.todolistbackend.repository;

import com.example.todolistbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNombre(String nombre); // Ejemplo de método de consulta personalizado
}