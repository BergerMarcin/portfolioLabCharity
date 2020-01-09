package pl.coderslab.charity.services;

import pl.coderslab.charity.dtos.CurrentUserDataDTO;

public interface CurrentUserDataDTOService {

    CurrentUserDataDTO readFromDB(String email);

    String getPasswordFromDB (String email);

}
