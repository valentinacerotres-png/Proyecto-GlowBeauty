package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.UsuarioRepository;
import model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoRepository productoRepo;

    public ProductoController(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(productoRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoRepo.findById(id);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoRepo.findByNombreContainingIgnoreCase(nombre));
    }

    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@RequestParam String categoria) {
        return ResponseEntity.ok(productoRepo.findByCategoriaIgnoreCase(categoria));
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto,
                                          @RequestParam Long usuarioId) {
        Optional<Usuario> usuarioOpt = UsuarioRepository.findById(usuarioId);

        if (usuarioOpt.isEmpty() || usuarioOpt.get().getRol() != Usuario.Rol.ADMINISTRADOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return new ResponseEntity<>(productoRepo.save(producto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto datosProducto) {
        return productoRepo.findById(id).map(productoExistente -> {
            productoExistente.setNombre(datosProducto.getNombre());
            productoExistente.setCategoria(datosProducto.getCategoria());
            productoExistente.setMarca(datosProducto.getMarca());
            productoExistente.setPrecio(datosProducto.getPrecio());
            productoExistente.setStock(datosProducto.getStock());
            return ResponseEntity.ok(productoRepo.save(productoExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoRepo.existsById(id)) {
            productoRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}