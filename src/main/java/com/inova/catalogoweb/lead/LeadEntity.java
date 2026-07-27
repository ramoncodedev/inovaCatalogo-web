package com.inova.catalogoweb.lead;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "leads")
public class LeadEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_client")
    private String nameClient;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "email", nullable = false,  unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    private BusinessSegment segment;

    @Column(name = "privacy_policy_accepted", nullable = false)
    private Boolean privacyPolicyAccepted;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}
