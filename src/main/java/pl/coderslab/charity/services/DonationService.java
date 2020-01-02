package pl.coderslab.charity.services;


import pl.coderslab.charity.dtos.DonationDataDTO;

import java.time.LocalDate;

public interface DonationService {

    Long bagsCountBeforeDate (LocalDate localDate);

    Long supportedOrganizationsCountBeforeDate (LocalDate localDate);

    void saveDonation (DonationDataDTO donationDataDTO) throws SavingDataException;

}
