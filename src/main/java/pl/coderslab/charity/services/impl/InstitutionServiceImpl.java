package pl.coderslab.charity.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.dtos.InstitutionDataDTO;
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

    @Override
    public InstitutionDataDTO institutionById(Long id) {
        Institution institution = institutionRepository.findAllById(id);
        if (institution==null){
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        InstitutionDataDTO institutionDataDTO = modelMapper.map(institution, InstitutionDataDTO.class);
        return  institutionDataDTO;
    }
}
