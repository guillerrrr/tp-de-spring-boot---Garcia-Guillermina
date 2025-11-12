package com.utn.productos.controller;

import com.utn.productos.dto.ActualizarStockDTO;
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.exception.ProductoNotFoundException;
import com.utn.productos.model.Categoria;
import com.utn.productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Endpoints para gestionar productos")
@Validated
public class ProductoController {

    private final ProductoService service;

    @Operation(summary = "Listar todos los productos")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> getAll() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Obtener un producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> getById(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(p -> ResponseEntity.ok(new ProductoResponseDTO(
                        p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getStock(), p.getCategoria()
                )))
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    @Operation(summary = "Filtrar productos por categoría")
    @ApiResponse(responseCode = "200", description = "Listado por categoría obtenido")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> getByCategoria(@PathVariable Categoria categoria) {
        return ResponseEntity.ok(service.obtenerPorCategoria(categoria));
    }

    @Operation(summary = "Crear un producto nuevo")
    @ApiResponse(responseCode = "201", description = "Producto creado",
            content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class)))
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoDTO dto) {
        ProductoResponseDTO created = service.crearProducto(dto);
        return ResponseEntity.created(URI.create("/api/productos/" + created.id())).body(created);
    }

    @Operation(summary = "Actualizar un producto completo (PUT)")
    @ApiResponse(responseCode = "200", description = "Producto actualizado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(service.actualizarProducto(id, dto));
    }

    @Operation(summary = "Actualizar solamente el stock (PATCH)")
    @ApiResponse(responseCode = "200", description = "Stock actualizado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> updateStock(@PathVariable Long id, @Valid @RequestBody ActualizarStockDTO dto) {
        return ResponseEntity.ok(service.actualizarStock(id, dto.stock()));
    }

    @Operation(summary = "Eliminar un producto por ID")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
