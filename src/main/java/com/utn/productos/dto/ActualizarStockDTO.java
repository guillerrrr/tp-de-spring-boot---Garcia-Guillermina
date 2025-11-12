package com.utn.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para actualizar únicamente el stock")
public record ActualizarStockDTO(
        @NotNull
        @Min(0)
        @Schema(description = "Nuevo stock", example = "120")
        Integer stock
) {}
