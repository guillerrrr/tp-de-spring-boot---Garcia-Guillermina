package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de respuesta de producto")
public record ProductoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        Categoria categoria
) {}
