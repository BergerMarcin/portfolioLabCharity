package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.entities.Donation;
import pl.coderslab.charity.domain.repositories.CategoryRepository;
import pl.coderslab.charity.domain.repositories.DonationRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.services.DonationService;
import pl.coderslab.charity.services.SavingDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class DonationServiceImpl implements DonationService {

    private DonationRepository donationRepository;
    private CategoryRepository categoryRepository;
    private InstitutionRepository institutionRepository;

    public DonationServiceImpl(DonationRepository donationRepository, CategoryRepository categoryRepository, InstitutionRepository institutionRepository) {
        this.donationRepository = donationRepository;
        this.categoryRepository = categoryRepository;
        this.institutionRepository = institutionRepository;
    }

    @Override
    public void saveDonation(DonationDataDTO donationDataDTO) throws SavingDataException {

        //TODO: check if categories.name & institution.name was mapped (not only their id's)

        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveDonation !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donationDataDTO to be mapped to donation: {}", donationDataDTO);

        // Mapping donation (categories & institution is not mapped here, id is wrongly mapped from institutionId)
        ModelMapper modelMapper = new ModelMapper();
        Donation donation = new Donation();
        donation = modelMapper.map(donationDataDTO, Donation.class);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donation (from donationDataDTO) after simple mapping: {}", donation);

        List<Category> categories = donationDataDTO.getCategoryIds()
                .stream()
                .sorted()
                .map(id -> categoryRepository.findAllById(id))
                .collect(Collectors.toList());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. categories: {}", categories);
        donation.setCategories(categories);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donation (from donationDataDTO) after categories set: {}", donation);
        if (donation.getCategories().get(0) == null || donation.getCategories().get(0).getName().length() == 0) {
            throw new SavingDataException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }

        //Mapping institution
        try {
            donation.setInstitution(institutionRepository.findAllById(donationDataDTO.getInstitutionId()));
        } catch (Throwable e) {
            throw new SavingDataException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donation (from donationDataDTO) after institution set: {}", donation);
        if (donation.getInstitution().getName() == null) {
            throw new SavingDataException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }

        //Reset id
        donation.setId(null);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donation (from donationDataDTO) after id reset, to be saved: {}", donation);

        // Final saving
        Donation donationSaved = donationRepository.save(donation);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donationSaved saved: {}", donationSaved);
        if (donationSaved == null) {
            throw new SavingDataException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }
    }

}
