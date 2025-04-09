package com.example.todolistbackend.repository;

import com.example.todolistbackend.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    List<Tarea> findByUsuario_Nombre(String nombreUsuario);
    List<Tarea> findByCategoria_Nombre(String nombreCategoria);
    List<Tarea> findByEstado(Boolean estado);
    List<Tarea> findAllByOrderByFechaDeInicioAsc();
}