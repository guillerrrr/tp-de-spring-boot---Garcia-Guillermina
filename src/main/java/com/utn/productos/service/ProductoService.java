package com.utn.productos.service;

import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.repository.ProductoRepository;
import com.utn.productos.exception.ProductoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repository;

    // Create
    public ProductoResponseDTO crearProducto(ProductoDTO dto) {
        Producto p = fromDTO(dto);
        Producto saved = repository.save(p);
        return toResponseDTO(saved);
    }

    // Read all
    public List<ProductoResponseDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).toList();
    }

    // Read by id
    public Optional<Producto> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    // Read by category
    public List<ProductoResponseDTO> obtenerPorCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria).stream().map(this::toResponseDTO).toList();
    }

    // Update full
    public ProductoResponseDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto existente = repository.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));
        existente.setNombre(dto.nombre());
        existente.setDescripcion(dto.descripcion());
        existente.setPrecio(dto.precio());
        existente.setStock(dto.stock());
        existente.setCategoria(dto.categoria());
        Producto actualizado = repository.save(existente);
        return toResponseDTO(actualizado);
    }

    // Update stock only
    public ProductoResponseDTO actualizarStock(Long id, Integer nuevoStock) {
        Producto existente = repository.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));
        existente.setStock(nuevoStock);
        return toResponseDTO(repository.save(existente));
    }

    // Delete
    public void eliminarProducto(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // Mappers
    private ProductoResponseDTO toResponseDTO(Producto p) {
        return new ProductoResponseDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria()
        );
    }

    private Producto fromDTO(ProductoDTO dto) {
        return Producto.builder()
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .precio(dto.precio())
                .stock(dto.stock())
                .categoria(dto.categoria())
                .build();
    }
}
