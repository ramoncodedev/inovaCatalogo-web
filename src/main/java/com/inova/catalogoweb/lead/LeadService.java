package com.inova.catalogoweb.lead;


import com.inova.catalogoweb.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;


    public LeadResponse createLead(LeadRequest leadRequest) {

        if (leadRepository.existsByEmail(leadRequest.email())){
            throw new EmailAlreadyExistsException(leadRequest.email());
        }

        LeadEntity lead = LeadMapper.toEntity(leadRequest);
        LeadEntity savedLead = leadRepository.save(lead);
        return LeadMapper.toResponse(savedLead);
    }

}
