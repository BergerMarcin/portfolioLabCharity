package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.DonationRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.dtos.InstitutionAddDTO;
import pl.coderslab.charity.dtos.InstitutionDTO;
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
    public List<InstitutionDTO> allInstitutionDTOList() {
        List<Institution> institutionList = institutionRepository.findAllByNameIsNotNullOrderByName();
        Mapper<Institution, InstitutionDTO> mapper = new Mapper<>();
        return mapper.mapList(institutionList, new InstitutionDTO(),"STANDARD");
    }

    @Override
    public List<InstitutionDTO> ifTrustedInstitutionDTOList(Boolean trusted) {
        List<Institution> institutionList = institutionRepository.findAllByNameIsNotNullOrderByName();
        Mapper<Institution, InstitutionDTO> mapper = new Mapper<>();
        return mapper.mapList(institutionList, new InstitutionDTO(),"STANDARD");
    }

    @Override
    public InstitutionDTO institutionById(Long id) {
        Institution institution = institutionRepository.findAllById(id);
        if (institution==null){
            return null;
        }
        Mapper<Institution, InstitutionDTO> mapper = new Mapper<>();
        return mapper.mapObj(institution, new InstitutionDTO(),"STANDARD");
    }

    @Override
    public void saveNewInstitution(InstitutionAddDTO institutionAddDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveInstitution !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.saveInstitution institutionDTO to be mapped to Institution: {}", institutionAddDTO.toString());
        Mapper<InstitutionAddDTO, Institution> mapper = new Mapper<>();
        Institution institution = mapper.mapObj(institutionAddDTO, new Institution(),"STANDARD");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.saveInstitution institution (from institutionDTO) after simple mapping: {}", institution.toString());

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
    public void updateInstitution(Long idProtected, InstitutionDTO institutionDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! updateInstitution !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.updateInstitution institutionDTO to be mapped to Institution: {}", institutionDTO.toString());
        Mapper<InstitutionDTO, Institution> mapper = new Mapper<>();
        Institution institution = mapper.mapObj(institutionDTO, new Institution(),"STANDARD");
        institution.setCreatedOn(institutionRepository.findAllById(idProtected).getCreatedOn());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.updateInstitution institution (from institutionDTO) after simple mapping: {}", institution.toString());

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
    public void deleteInstitution(Long idProtected, InstitutionDTO institutionDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.deleteInstitution START !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.deleteInstitution institutionDTO to be mapped to Institution: {}", institutionDTO.toString());
        Mapper<InstitutionDTO, Institution> mapper = new Mapper<>();
        Institution institution = mapper.mapObj(institutionDTO, new Institution(),"STANDARD");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! InstitutionServiceImpl.deleteInstitution institution (from institutionDTO) after simple mapping: {}", institution.toString());

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
