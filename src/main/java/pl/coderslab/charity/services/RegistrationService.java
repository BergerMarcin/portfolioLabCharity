package pl.coderslab.charity.services;

import pl.coderslab.charity.dtos.RegistrationDataDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

public interface RegistrationService {

    void register(RegistrationDataDTO registrationDataDTO) throws EntityToDataBaseException;

}
