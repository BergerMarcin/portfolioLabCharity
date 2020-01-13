package pl.coderslab.charity.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.dtos.InstitutionDataDTO;
import pl.coderslab.charity.services.InstitutionService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InstitutionServiceImpl implements InstitutionService {

    private InstitutionRepository institutionRepository;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Override
    public List<InstitutionDataDTO> allInstitutionDataDTOList() {
        List<Institution> institutionList = institutionRepository.findAllByNameIsNotNullOrderByName();
        List<InstitutionDataDTO> institutionDataDTOList = new ArrayList<>();
        for (Institution institution : institutionList) {
            ModelMapper modelMapper = new ModelMapper();
            InstitutionDataDTO institutionDataDTO = modelMapper.map(institution, InstitutionDataDTO.class);
            institutionDataDTOList.add(institutionDataDTO);
        }
        return institutionDataDTOList;
    }

    @Override
    public List<InstitutionDataDTO> ifTrustedInstitutionDataDTOList(Boolean trusted) {
        List<Institution> institutionList = institutionRepository.findAllByNameIsNotNullOrderByName();
        List<InstitutionDataDTO> institutionDataDTOList = new ArrayList<>();
        for (Institution institution : institutionList) {
            if (institution.getTrusted() == trusted) {
                ModelMapper modelMapper = new ModelMapper();
                InstitutionDataDTO institutionDataDTO = modelMapper.map(institution, InstitutionDataDTO.class);
                institutionDataDTOList.add(institutionDataDTO);
            }
        }
        return institutionDataDTOList;
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
