package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.entities.Donation;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.CategoryRepository;
import pl.coderslab.charity.domain.repositories.DonationRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;
import pl.coderslab.charity.services.DonationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    public Long bagsCountBeforeDate(LocalDate localDate) {
        List<Donation> donations = donationRepository.findAllByPickUpDateIsBefore(localDate);
        Long bagsCount = donations.stream().mapToLong(d -> d.getQuantity()).sum();
        return bagsCount;
    }

    @Override
    public Long supportedOrganizationsCountBeforeDate(LocalDate localDate) {
        return donationRepository.supportedOrganizationsCountBeforeDate(localDate);
    }

    @Override
    public List<DonationDataDTO> donationListByInstitutionId(Long institutionId) {
        Institution institution = institutionRepository.findAllById(institutionId);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl.donationListByInstitutionId institution: {}", institution.toString());
        List<Donation> donationList = donationRepository.findAllWithCategoriesByInstitutionOrderByInstitution(institution);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl.donationListByInstitutionId donationList: {}", donationList.toString());
        List<DonationDataDTO> donationDataDTOList = new ArrayList<>();
        for (Donation donation: donationList) {
            ModelMapper modelMapper = new ModelMapper();
            DonationDataDTO donationDataDTO = modelMapper.map(donation, DonationDataDTO.class);
            donationDataDTOList.add(donationDataDTO);
        }
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl.donationListByInstitutionId donationDataDTOList: {}", donationDataDTOList.toString());
        return donationDataDTOList;
    }

    @Override
    public void saveDonation(DonationDataDTO donationDataDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveDonation !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donationDataDTO to be mapped to donation: {}", donationDataDTO.toString());

        // STRICT mapping donation
        // (categories & institution is not mapped here;
        //  in case STANDARD mapping id is wrongly mapped from institutionId)
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Donation donation = modelMapper.map(donationDataDTO, Donation.class);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donation (from donationDataDTO) after simple mapping: {}", donation.toString());
        // InstitutionId need to be copied directly (due to STRICK matching strategy of mapping; STRICT matching requirred for id of donationDataDTO)
        Institution institution = new Institution();
        institution.setId(donationDataDTO.getInstitutionId());
        donation.setInstitution(institution);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donation (from donationDataDTO) after simple mapping + add institution: {}", donation.toString());

        // Fill-in category detail-data of donation
        List<Category> categories = categoryRepository.findAllByIdIn(donationDataDTO.getCategoryIds());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. categories: {}", categories.toString());
        donation.setCategories(categories);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donation (from donationDataDTO) after categories set: {}", donation.toString());
        if (donation.getCategories().get(0) == null || donation.getCategories().get(0).getName().length() == 0) {
            throw new EntityToDataBaseException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }

        //Fill-in institution detail-data of donation
        try {
            donation.setInstitution(institutionRepository.findAllById(donationDataDTO.getInstitutionId()));
        } catch (Throwable e) {
            throw new EntityToDataBaseException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donation (from donationDataDTO) after institution set: {}", donation.toString());
        if (donation.getInstitution().getName() == null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }

        // Final saving donation
        Donation donationSaved = donationRepository.save(donation);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DonationServiceImpl. donationSaved saved: {}", donationSaved.toString());
        if (donationSaved == null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }
    }

}
