package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para la gestión de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios",
            description = "Devuelve lista completa de usuarios registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar usuarios por nombre",
            description = "Filtra usuarios por coincidencia en el nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada con éxito")
    })
    public ResponseEntity<List<Usuario>> buscarPorNombre(
            @RequestParam
            @Parameter(description = "Nombre o parte del nombre a buscar") String nombre) {
        return ResponseEntity.ok(usuarioService.findByNombre(nombre));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID",
            description = "Retorna un usuario según su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> getUsuarioPorId(
            @PathVariable
            @Parameter(description = "Identificador único del usuario") Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo usuario",
            description = "Registra un usuario en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya registrado")
    })
    public ResponseEntity<Usuario> crearUsuario(
            @RequestBody
            @Parameter(description = "Datos del usuario a registrar") Usuario usuario) {
        Usuario nuevo = usuarioService.save(usuario);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario completo",
            description = "Reemplaza todos los datos de un usuario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable
            @Parameter(description = "ID del usuario a modificar") Long id,
            @RequestBody
            @Parameter(description = "Nuevos datos del usuario") Usuario usuario) {
        Usuario actualizado = usuarioService.update(id, usuario);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualización parcial",
            description = "Modifica solo algunos campos del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizarParcial(
            @PathVariable
            @Parameter(description = "ID del usuario") Long id,
            @RequestBody
            @Parameter(description = "Campos a modificar") Map<String, Object> cambios) {
        Usuario actualizado = usuarioService.patch(id, cambios);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario",
            description = "Remueve un usuario del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable
            @Parameter(description = "ID del usuario a eliminar") Long id) {
        if (usuarioService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}