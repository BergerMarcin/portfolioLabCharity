package pl.coderslab.charity.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.services.InstitutionService;

import java.util.List;

@Service
@Transactional
public class InstitutionServiceImpl implements InstitutionService {

    private InstitutionRepository institutionRepository;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Override
    public List<Institution> allInstitutionList() {
        return institutionRepository.findAllByNameIsNotNullOrderByName();
    }
}
