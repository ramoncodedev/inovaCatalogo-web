package com.inova.catalogoweb.lead;


import com.inova.catalogoweb.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lead")
@RequiredArgsConstructor
@Tag(name = "Leads", description = "Captação de interessados na plataforma CatalogoWeb")
public class LeadController {

    private final LeadService service;

    @PostMapping
    @Operation(
            summary = "Cadastra um novo lead",
            description = "Registra um interessado a partir do formulário público da landing page."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Lead cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = LeadResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Payload inválido (campos obrigatórios ausentes ou formato incorreto)",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Já existe um lead cadastrado com este e-mail",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public ResponseEntity<LeadResponse> create(@Valid @RequestBody LeadRequest leadRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createLead(leadRequest));
    }

}