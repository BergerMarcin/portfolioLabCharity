package pl.coderslab.charity.services;


import pl.coderslab.charity.dtos.DonationDataDTO;

public interface DonationService {

    void saveDonation (DonationDataDTO donationDataDTO) throws SavingDataException;

}
