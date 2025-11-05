package com.milsabores.backend.controller;

import com.milsabores.backend.model.Usuario;
import com.milsabores.backend.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Api(value = "Usuario Management System")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @ApiOperation(value = "Lista de los usuarios", response = List.class)
    public List<Usuario>getAllUsuarios(){
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener usario por el id")
    public Usuario getUsuarioById(@PathVariable Long id){
        return usuarioService.getUsuarioById(id);

    }

    @PostMapping
    @ApiOperation(value = "Añadir un usuario")
    public Usuario saveUsuario(@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Actualizar un usuario existente")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario existingUsuario = usuarioService.getUsuarioById(id);

        if (existingUsuario!= null){
            existingUsuario.setNombre(usuario.getNombre());
            existingUsuario.setCorreo(usuario.getCorreo());
            existingUsuario.setContrasena(usuario.getContrasena());
            existingUsuario.setDireccion(usuario.getDireccion());
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Eliminar un usuario")
    public void deleteUsuario(@PathVariable Long id){
        usuarioService.deleteUsuario(id);
    }

}
