package com.example.todolistbackend.service;

import com.example.todolistbackend.model.Tarea;
import com.example.todolistbackend.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> obtenerTareaPorId(Integer id) {
        return tareaRepository.findById(id);
    }

    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void eliminarTarea(Integer id) {
        tareaRepository.deleteById(id);
    }

    public List<Tarea> obtenerTareasPorUsuario(String nombreUsuario) {
        return tareaRepository.findByUsuario_Nombre(nombreUsuario);
    }

    public List<Tarea> obtenerTareasPorCategoria(String nombreCategoria) {
        return tareaRepository.findByCategoria_Nombre(nombreCategoria);
    }

    public List<Tarea> obtenerTareasOrdenadasPorFechaInicio() {
        return tareaRepository.findAllByOrderByFechaDeInicioAsc();
    }

    public List<Tarea> obtenerTareasPorEstado(Boolean estado) {
        return tareaRepository.findByEstado(estado);
    }
}