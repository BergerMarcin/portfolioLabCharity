package pl.coderslab.charity.services;

import pl.coderslab.charity.dtos.InstitutionAddDTO;
import pl.coderslab.charity.dtos.InstitutionDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

import java.util.List;

public interface InstitutionService {

    List<InstitutionDTO> allInstitutionDTOList();

    List<InstitutionDTO> ifTrustedInstitutionDTOList(Boolean trusted);

    InstitutionDTO institutionById(Long id);

    void saveNewInstitution(InstitutionAddDTO institutionAddDTO) throws EntityToDataBaseException;

    void updateInstitution(Long idProtected, InstitutionDTO institutionDTO) throws EntityToDataBaseException;

    void deleteInstitution(Long idProtected, InstitutionDTO institutionDTO) throws EntityToDataBaseException;

}
