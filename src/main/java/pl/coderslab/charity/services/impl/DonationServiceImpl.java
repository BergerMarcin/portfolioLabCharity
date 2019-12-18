package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Donation;
import pl.coderslab.charity.domain.repositories.DonationRepository;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.services.DonationService;


@Service
@Transactional
@Slf4j
public class DonationServiceImpl implements DonationService {

    private DonationRepository donationRepository;
    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Override
    public void saveDonation(DonationDataDTO donationDataDTO) {
        log.debug("DonationServiceImpl. DonationDataDTO: {}", donationDataDTO);
        ModelMapper modelMapper = new ModelMapper();
        Donation donation = modelMapper.map(donationDataDTO, Donation.class);
        log.debug("DonationServiceImpl. Donation (from DonationDataDTO): {}", donation);
        donationRepository.save(donation);
    }

}
