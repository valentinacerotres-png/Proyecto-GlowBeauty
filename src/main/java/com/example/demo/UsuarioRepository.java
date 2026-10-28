package com.example.demo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
@Repository
public class UsuarioRepository {

    private final Map<String, Usuario> baseDeDatos = new HashMap<>();

    public Usuario save(Usuario usuario) {
        baseDeDatos.put(usuario.getId(), usuario);
        return usuario;
    }

    public Usuario findById(String id) {
        return baseDeDatos.get(id);
    }

    public List<Usuario> findAll() {
        return new ArrayList<>(baseDeDatos.values());
    }

    public List<Usuario> findByNombreContaining(String nombre) {
        return baseDeDatos.values().stream()
                .filter(usuario ->
                        usuario.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        baseDeDatos.remove(id);
    }

    public Usuario update(Usuario usuario) {
        if (baseDeDatos.containsKey(usuario.getId())) {
            baseDeDatos.put(usuario.getId(), usuario);
            return usuario;
        }
        return null;
    }
}