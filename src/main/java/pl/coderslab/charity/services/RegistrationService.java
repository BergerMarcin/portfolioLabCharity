package pl.coderslab.charity.services;

import pl.coderslab.charity.dtos.RegistrationDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

public interface RegistrationService {

    void register(RegistrationDTO registrationDTO) throws EntityToDataBaseException;

}
