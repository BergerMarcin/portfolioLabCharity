package pl.coderslab.charity.services;

import pl.coderslab.charity.dtos.InstitutionAddDataDTO;
import pl.coderslab.charity.dtos.InstitutionDataDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

import java.util.List;

public interface InstitutionService {

    List<InstitutionDataDTO> allInstitutionDataDTOList();

    List<InstitutionDataDTO> ifTrustedInstitutionDataDTOList(Boolean trusted);

    InstitutionDataDTO institutionById(Long id);

    void saveNewInstitution(InstitutionAddDataDTO institutionAddDataDTO) throws EntityToDataBaseException;

    void updateInstitution(Long idProtected, InstitutionDataDTO institutionDataDTO) throws EntityToDataBaseException;

    void deleteInstitution(Long idProtected, InstitutionDataDTO institutionDataDTO) throws EntityToDataBaseException;

}
