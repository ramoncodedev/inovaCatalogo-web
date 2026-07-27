package com.inova.catalogoweb.lead;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "Lead cadastrado com sucesso na plataforma")
public record LeadResponse(

        @Schema(description = "Identificador único do lead", example = "42")
        Long id,

        @Schema(description = "Nome do responsável", example = "Ana Souza")
        String nameClient,

        @Schema(description = "Nome da empresa", example = "Padaria Estrela")
        String companyName,

        @Schema(description = "E-mail de contato", example = "ana@padariaestrela.com.br")
        String email,

        @Schema(description = "Telefone de contato", example = "11987654321")
        String phone,

        @Schema(description = "Segmento de negócio", example = "PADARIA")
        BusinessSegment segment,

        @Schema(description = "Aceite da política de privacidade", example = "true")
        Boolean privacyPolicyAccepted,

        @Schema(description = "Data e hora do cadastro", example = "2026-07-27T10:15:30")
        LocalDateTime createdAt
) {
}