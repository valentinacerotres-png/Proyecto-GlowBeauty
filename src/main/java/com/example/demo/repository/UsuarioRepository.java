package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCorreo(String correo);
    Usuario findByCorreo(String correo);

    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}