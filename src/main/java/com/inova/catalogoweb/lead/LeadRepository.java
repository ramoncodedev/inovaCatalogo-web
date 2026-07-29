package com.inova.catalogoweb.lead;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeadRepository extends JpaRepository<LeadEntity, Long> {

    boolean existsByEmail(String email);

    Optional<LeadEntity> findByEmail(String email);

}
