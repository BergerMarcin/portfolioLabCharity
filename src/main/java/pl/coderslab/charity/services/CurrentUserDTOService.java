package pl.coderslab.charity.services;

import pl.coderslab.charity.dtos.CurrentUserDTO;

public interface CurrentUserDTOService {

    CurrentUserDTO readFromDB(String email);

    String getPasswordFromDB (String email);



}
