package pl.coderslab.charity.services;


import pl.coderslab.charity.dtos.DonationDataDTO;

import java.time.LocalDate;
import java.util.List;

public interface DonationService {

    Long bagsCountBeforeDate (LocalDate localDate);

    Long supportedOrganizationsCountBeforeDate (LocalDate localDate);

    List<DonationDataDTO> donationListByInstitutionId (Long institutionId);

    void saveDonation (DonationDataDTO donationDataDTO) throws SavingDataException;

}
