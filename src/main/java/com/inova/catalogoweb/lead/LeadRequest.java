package com.inova.catalogoweb.lead;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados enviados pelo formulário de captação de leads")
public record LeadRequest(
        @Schema(description = "Nome completo do responsável pelo negócio", example = "Ana Souza")
        @NotBlank(message = "O nome do usuário é obrigatório")
        String nameClient,

        @Schema(description = "Nome da empresa ou estabelecimento", example = "Padaria Estrela")
        @NotBlank(message = "O nome da empresa é obrigatório")
        String companyName,

        @Schema(description = "E-mail para contato — deve ser único", example = "ana@padariaestrela.com.br")
        @Email
        @NotBlank(message = "O email é obrigatório")
        String email,

        @Schema(description = "Telefone de contato (com DDD)", example = "11987654321")
        @NotBlank(message = "O telefone é obrigatório")
        String phone,

        @Schema(description = "Segmento de negócio do interessado", example = "PADARIA")
        @NotNull(message = "O segmento de negócio é obrigatório")
        BusinessSegment segment,

        @Schema(description = "Aceite da política de privacidade (LGPD) — deve ser true", example = "true")
        @NotNull(message = "É necessário informar o aceite da política de privacidade")
        @AssertTrue(message = "É necessário aceitar a política de privacidade")
        Boolean privacyPolicyAccepted
) {
}