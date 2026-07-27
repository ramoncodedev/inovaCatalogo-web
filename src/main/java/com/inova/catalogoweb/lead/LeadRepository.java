package com.inova.catalogoweb.lead;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends JpaRepository<LeadEntity, Long> {

    boolean existsByEmail(String email);

}
