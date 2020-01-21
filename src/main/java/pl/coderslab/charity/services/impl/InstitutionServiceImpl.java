package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.DonationRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.dtos.InstitutionAddDataDTO;
import pl.coderslab.charity.dtos.InstitutionDataDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;
import pl.coderslab.charity.services.InstitutionService;
import pl.coderslab.charity.services.Mapper;

import java.util.List;

@Service
@Transactional
@Slf4j
public class InstitutionServiceImpl implements InstitutionService {

    private InstitutionRepository institutionRepository;
    private DonationRepository donationRepository;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository, DonationRepository donationRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
    }

    @Override
    public List<InstitutionDataDTO> allInstitutionDataDTOList() {
        List<Institution> institutionList = institutionRepository.findAllByNameIsNotNullOrderByName();
//        List<InstitutionDataDTO> institutionDataDTOList = new ArrayList<>();
//        for (Institution institution : institutionList) {
//            ModelMapper modelMapper = new ModelMapper();
//            InstitutionDataDTO institutionDataDTO = modelMapper.map(institution, InstitutionDataDTO.class);
//            institutionDataDTOList.add(institutionDataDTO);
//        }
//        return institutionDataDTOList;
        Mapper<Institution, InstitutionDataDTO> mapper = new Mapper<>();
        return mapper.mapList(institutionList, new InstitutionDataDTO(),"STANDARD");
    }

    @Override
    public List<InstitutionDataDTO> ifTrustedInstitutionDataDTOList(Boolean trusted) {
        List<Institution> institutionList = institutionRepository.findAllByNameIsNotNullOrderByName();
//        List<InstitutionDataDTO> institutionDataDTOList = new ArrayList<>();
//        for (Institution institution : institutionList) {
//            if (institution.getTrusted() == trusted) {
//                ModelMapper modelMapper = new ModelMapper();
//                InstitutionDataDTO institutionDataDTO = modelMapper.map(institution, InstitutionDataDTO.class);
//                institutionDataDTOList.add(institutionDataDTO);
//            }
//        }
//        return institutionDataDTOList;
        Mapper<Institution, InstitutionDataDTO> mapper = new Mapper<>();
        return mapper.mapList(institutionList, new InstitutionDataDTO(),"STANDARD");
    }

    @Override
    public InstitutionDataDTO institutionById(Long id) {
        Institution institution = institutionRepository.findAllById(id);
        if (institution==null){
            return null;
        }
//        ModelMapper modelMapper = new ModelMapper();
//        InstitutionDataDTO institutionDataDTO = modelMapper.map(institution, InstitutionDataDTO.class);
//        return  institutionDataDTO;
        Mapper<Institution, InstitutionDataDTO> mapper = new Mapper<>();
        return mapper.mapObj(institution, new InstitutionDataDTO(),"STANDARD");
    }

    @Override
    public void saveNewInstitution(InstitutionAddDataDTO institutionAddDataDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveInstitution !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.saveInstitution institutionDataDTO to be mapped to Institution: {}", institutionAddDataDTO.toString());

//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
//        Institution institution = modelMapper.map(institutionAddDataDTO, Institution.class);
        Mapper<InstitutionAddDataDTO, Institution> mapper = new Mapper<>();
        Institution institution = mapper.mapObj(institutionAddDataDTO, new Institution(),"STANDARD");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.saveInstitution institution (from institutionDataDTO) after simple mapping: {}", institution.toString());

        // Protection against unauthorised in fact update (instead of save=add new record/line)
        if (institution.getId() != null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        // Final saving institution
        Institution institutionSaved = institutionRepository.save(institution);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.saveInstitution institutionSaved saved: {}", institutionSaved.toString());
        // check if saved
        if (institutionSaved == null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }
    }

    @Override
    public void updateInstitution(Long idProtected, InstitutionDataDTO institutionDataDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! updateInstitution !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.updateInstitution institutionDataDTO to be mapped to Institution: {}", institutionDataDTO.toString());

//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
//        Institution institution = modelMapper.map(institutionDataDTO, Institution.class);
        Mapper<InstitutionDataDTO, Institution> mapper = new Mapper<>();
        Institution institution = mapper.mapObj(institutionDataDTO, new Institution(),"STANDARD");
        institution.setCreatedOn(institutionRepository.findAllById(idProtected).getCreatedOn());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.updateInstitution institution (from institutionDataDTO) after simple mapping: {}", institution.toString());

        // Protection against unauthorised in fact update another record/line (instead of update the right one record/line)
        if (institution.getId() == null || institution.getId() != idProtected) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        // Final update institution
        Institution institutionSaved = institutionRepository.save(institution);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.updateInstitution institutionSaved saved: {}", institutionSaved.toString());
        // check if update succeed
        if (institutionSaved == null || institution.getId() != institutionSaved.getId()) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }
    }

    @Override
    public void deleteInstitution(Long idProtected, InstitutionDataDTO institutionDataDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.deleteInstitution START !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.deleteInstitution institutionDataDTO to be mapped to Institution: {}", institutionDataDTO.toString());

//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
//        Institution institution = modelMapper.map(institutionDataDTO, Institution.class);
        Mapper<InstitutionDataDTO, Institution> mapper = new Mapper<>();
        Institution institution = mapper.mapObj(institutionDataDTO, new Institution(),"STANDARD");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.deleteInstitution institution (from institutionDataDTO) after simple mapping: {}", institution.toString());

        // Protection against unauthorised in fact delete another record/line (instead of delete the right one record/line)
        if (institution.getId() != idProtected) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        // Deleting all related donations
        donationRepository.deleteAll(donationRepository.findAllWithCategoriesByInstitutionOrderByInstitution(institution));

        // Final deleting institution
        institutionRepository.delete(institution);
    }


}
