package com.example.todolistbackend.controller;

import com.example.todolistbackend.model.Usuario;
import com.example.todolistbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping