package pl.coderslab.charity.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.dtos.DonationDataDTO;

public interface DonationService {

    void saveDonation (DonationDataDTO donationDataDTO);

}
