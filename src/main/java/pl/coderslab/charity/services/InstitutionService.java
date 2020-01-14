package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.dtos.InstitutionAddDataDTO;
import pl.coderslab.charity.dtos.InstitutionDataDTO;

import java.util.List;

public interface InstitutionService {

    List<InstitutionDataDTO> allInstitutionDataDTOList();

    List<InstitutionDataDTO> ifTrustedInstitutionDataDTOList(Boolean trusted);

    InstitutionDataDTO institutionById(Long id);

    void saveInstitution(InstitutionAddDataDTO institutionAddDataDTO) throws SavingDataException;

    void updateInstitution(InstitutionDataDTO institutionDataDTO) throws SavingDataException;

    void deleteInstitution(InstitutionDataDTO institutionDataDTO);

}
