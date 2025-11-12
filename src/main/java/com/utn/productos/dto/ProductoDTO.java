package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO para creación/actualización de productos")
public record ProductoDTO(
        @NotBlank
        @Size(min = 3, max = 100)
        @Schema(description = "Nombre del producto", example = "Auriculares Bluetooth")
        String nombre,

        @Size(max = 500)
        @Schema(description = "Descripción del producto", example = "Auriculares con cancelación de ruido")
        String descripcion,

        @NotNull
        @DecimalMin(value = "0.01", inclusive = true)
        @Schema(description = "Precio del producto", example = "19999.99")
        Double precio,

        @NotNull
        @Min(0)
        @Schema(description = "Stock disponible", example = "50")
        Integer stock,

        @NotNull
        @Schema(description = "Categoría del producto", example = "ELECTRONICA")
        Categoria categoria
) {}
