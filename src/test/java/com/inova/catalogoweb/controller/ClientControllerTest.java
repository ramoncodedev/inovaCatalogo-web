package com.inova.catalogoweb.controller;


import com.inova.catalogoweb.lead.BusinessSegment;
import com.inova.catalogoweb.lead.LeadEntity;
import com.inova.catalogoweb.lead.LeadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LeadRepository leadRepository;

    @Test
    void deveCadastrarClienteComSucesso() throws Exception {

        String json = """
                {
                  "nameClient": "Luan",
                  "companyName": "Paladar gourmed",
                  "email": "luan@email.com",
                  "phone": "71999998888",
                  "segment": "RESTAURANTE",
                  "privacyPolicyAccepted": true
                }
                """;

        mockMvc.perform(post("/lead")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nameClient").value("Luan"))
                .andExpect(jsonPath("$.companyName").value("Paladar gourmed"))
                .andExpect(jsonPath("$.email").value("luan@email.com"))
                .andExpect(jsonPath("$.phone").value("71999998888"))
                .andExpect(jsonPath("$.segment").value("RESTAURANTE"))
                .andExpect(jsonPath("$.privacyPolicyAccepted").value(true));

        LeadEntity persisted = leadRepository.findByEmail("luan@email.com").orElseThrow();

        assertThat(persisted.getId()).isNotNull();
        assertThat(persisted.getNameClient()).isEqualTo("Luan");
        assertThat(persisted.getCompanyName()).isEqualTo("Paladar gourmed");
        assertThat(persisted.getEmail()).isEqualTo("luan@email.com");
        assertThat(persisted.getPhone()).isEqualTo("71999998888");
        assertThat(persisted.getSegment()).isEqualTo(BusinessSegment.RESTAURANTE);
        assertThat(persisted.getPrivacyPolicyAccepted()).isTrue();
        assertThat(persisted.getCreatedAt()).isNotNull();
    }
}