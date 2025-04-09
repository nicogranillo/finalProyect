package com.example.todolistbackend.controller;

import com.example.todolistbackend.model.Tarea;
import com.example.todolistbackend.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTodasLasTareas() {
        return ResponseEntity.ok(tareaService.obtenerTodasLasTareas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Tarea>> obtenerTareaPorId(@PathVariable Integer id) {
        Optional<Tarea> tarea = tareaService.obtenerTareaPorId(id);
        return tarea.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Tarea nuevaTarea = tareaService.guardarTarea(tarea);
        return new ResponseEntity<>(nuevaTarea, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Integer id, @RequestBody Tarea tareaActualizada) {
        Optional<Tarea> tareaExistente = tareaService.obtenerTareaPorId(id);
        if (tareaExistente.isPresent()) {
            tareaActualizada.setTareaID(id);
            Tarea tareaGuardada = tareaService.guardarTarea(tareaActualizada);
            return ResponseEntity.ok(tareaGuardada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Integer id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{nombreUsuario}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorUsuario(@PathVariable String nombreUsuario) {
        return ResponseEntity.ok(tareaService.obtenerTareasPorUsuario(nombreUsuario));
    }

    @GetMapping("/categoria/{nombreCategoria}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorCategoria(@PathVariable String nombreCategoria) {
        return ResponseEntity.ok(tareaService.obtenerTareasPorCategoria(nombreCategoria));
    }

    @GetMapping("/ordenadas")
    public ResponseEntity<List<Tarea>> obtenerTareasOrdenadasPorFechaInicio() {
        return ResponseEntity.ok(tareaService.obtenerTareasOrdenadasPorFechaInicio());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorEstado(@PathVariable Boolean estado) {
        return ResponseEntity.ok(tareaService.obtenerTareasPorEstado(estado));
    }
}