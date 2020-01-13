package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.dtos.InstitutionDataDTO;

import java.util.List;

public interface InstitutionService {

    List<InstitutionDataDTO> allInstitutionDataDTOList();

    List<InstitutionDataDTO> ifTrustedInstitutionDataDTOList(Boolean trusted);

    InstitutionDataDTO institutionById(Long id);

}
