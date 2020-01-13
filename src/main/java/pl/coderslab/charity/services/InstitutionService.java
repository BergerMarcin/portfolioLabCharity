package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.dtos.InstitutionDataDTO;

import java.util.List;

public interface InstitutionService {

    List<Institution> allInstitutionList ();

    InstitutionDataDTO institutionById(Long id);

}
