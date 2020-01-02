package pl.coderslab.charity.services;

import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.dtos.RegistrationDataDTO;

public interface RegistrationService {

    void register(RegistrationDataDTO registrationDataDTO) throws SavingDataException;

}
