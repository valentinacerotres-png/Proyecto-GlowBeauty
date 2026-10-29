package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public Usuario registrar(Usuario usuario) {
        if (usuarioRepo.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        return usuarioRepo.save(usuario);
    }
    public Optional<Usuario> iniciarSesion(String correo, String contrasena) {
        Usuario usuario = usuarioRepo.findByCorreo(correo);

        if (usuario == null) {
            return Optional.empty();
        }

        String passUsuario = usuario.getContrasena();
        if (passUsuario == null) {
            return Optional.empty();
        }

        if (passUsuario.equals(contrasena)) {
            return Optional.of(usuario);
        }

        return Optional.empty();
    }

    public List<Usuario> findAll() {
        return usuarioRepo.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepo.findById(id);
    }

    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepo.findByNombreContainingIgnoreCase(nombre);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public Usuario update(Long id, Usuario datos) {
        return usuarioRepo.findById(id).map(existente -> {
            datos.setId(id);
            return usuarioRepo.save(datos);
        }).orElse(null);
    }

    public Usuario patch(Long id, Map<String, Object> cambios) {
        return usuarioRepo.findById(id).map(usuario -> {
            cambios.forEach((clave, valor) -> {
                switch (clave) {
                    case "nombre":
                        usuario.setNombre((String) valor);
                        break;
                    case "correo":
                        usuario.setCorreo((String) valor);
                        break;
                    case "contrasena":
                        usuario.setContrasena((String) valor);
                        break;
                }
            });
            return usuarioRepo.save(usuario);
        }).orElse(null);
    }

    public boolean deleteById(Long id) {
        if (usuarioRepo.existsById(id)) {
            usuarioRepo.deleteById(id);
            return true;
        }
        return false;
    }
}