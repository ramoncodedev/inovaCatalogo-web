package com.inova.catalogoweb.repository;


import com.inova.catalogoweb.lead.BusinessSegment;
import com.inova.catalogoweb.lead.LeadEntity;
import com.inova.catalogoweb.lead.LeadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ClientRepositoryTest {

    @Autowired
    private LeadRepository leadRepository;

    @Test
    void deveSalvarClienteComSucesso(){

        LeadEntity lead = LeadEntity.builder()
                .nameClient("ronaldo")
                .email("ronaldo@gmail.com")
                .companyName("MegaLanches")
                .phone("7981885953")
                .segment(BusinessSegment.RESTAURANTE)
                .privacyPolicyAccepted(true)
                .build();

        LeadEntity saved = leadRepository.save(lead);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNameClient()).isEqualTo("ronaldo");
        assertThat(saved.getEmail()).isEqualTo("ronaldo@gmail.com");
        assertThat(saved.getCompanyName()).isEqualTo("MegaLanches");
        assertThat(saved.getPhone()).isEqualTo("7981885953");
        assertThat(saved.getSegment()).isEqualTo(BusinessSegment.RESTAURANTE);
        assertThat(saved.getPrivacyPolicyAccepted()).isEqualTo(true);
    }

}
