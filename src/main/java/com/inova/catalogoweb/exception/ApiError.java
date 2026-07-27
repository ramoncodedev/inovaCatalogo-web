package com.inova.catalogoweb.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Schema(description = "Estrutura padrão de resposta de erro da API")
public record ApiError(

        @Schema(description = "Data e hora do erro", example = "27-07-2026 10:15:30")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,

        @Schema(description = "Código HTTP do erro", example = "400")
        Integer code,

        @Schema(description = "Nome do status HTTP", example = "BAD_REQUEST")
        String status,

        @Schema(description = "Lista de mensagens descrevendo o(s) erro(s)")
        List<String> errors
) {
}