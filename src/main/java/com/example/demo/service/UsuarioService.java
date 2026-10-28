package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return usuarioRepo.findByCorreo(correo)
                .filter(u -> u.getContrasena().equals(contrasena));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepo.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepo.findById(id);
    }
}