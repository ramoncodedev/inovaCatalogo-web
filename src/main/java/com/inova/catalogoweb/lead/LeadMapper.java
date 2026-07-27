package com.inova.catalogoweb.lead;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LeadMapper {

    public LeadEntity toEntity(LeadRequest leadRequest){
        return LeadEntity.builder()
                .nameClient(leadRequest.nameClient())
                .companyName(leadRequest.companyName())
                .email(leadRequest.email())
                .phone(leadRequest.phone())
                .segment(leadRequest.segment())
                .privacyPolicyAccepted(leadRequest.privacyPolicyAccepted())
                .build();
    }

    public LeadResponse toResponse(LeadEntity lead){
        return LeadResponse.builder()
                .id(lead.getId())
                .nameClient(lead.getNameClient())
                .companyName(lead.getCompanyName())
                .email(lead.getEmail())
                .phone(lead.getPhone())
                .segment(lead.getSegment())
                .privacyPolicyAccepted(lead.getPrivacyPolicyAccepted())
                .createdAt(lead.getCreatedAt())
                .build();
    }


}
