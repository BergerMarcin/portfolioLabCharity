package pl.coderslab.charity.services;


import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

import java.time.LocalDate;
import java.util.List;

public interface DonationService {

    Long bagsCountBeforeDate (LocalDate localDate);

    Long supportedOrganizationsCountBeforeDate (LocalDate localDate);

    List<DonationDataDTO> donationListByInstitutionId (Long institutionId);

    void saveNewDonation(DonationDataDTO donationDataDTO) throws EntityToDataBaseException;

}
