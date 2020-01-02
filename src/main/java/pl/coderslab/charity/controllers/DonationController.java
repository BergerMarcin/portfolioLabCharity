package pl.coderslab.charity.controllers;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.CategoryRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.dtos.CategoryDTO;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.dtos.InstitutionDTO;
import pl.coderslab.charity.services.DonationService;
import pl.coderslab.charity.services.SavingDataException;

import javax.validation.Valid;
import java.util.*;

@Controller
//@RequestMapping("/donation")
@Slf4j
public class DonationController {

    private DonationService donationService;
//TODO: change from repositories into Services ??????????
    private CategoryRepository categoryRepository;
    private InstitutionRepository institutionRepository;

    public DonationController(DonationService donationService,
                              CategoryRepository categoryRepository,
                              InstitutionRepository institutionRepository) {
        this.donationService = donationService;
        this.categoryRepository = categoryRepository;
        this.institutionRepository = institutionRepository;
    }

    // Library categories based on CategoryDTO (available non-stop in model as "categories" at @RequestMapping("/donation")
    @ModelAttribute("categories")
    public List<CategoryDTO> categories() {
        List<Category> categoryList = categoryRepository.findAllByNameIsNotNullOrderByName();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category: categoryList) {
            ModelMapper modelMapper = new ModelMapper();
            CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    // Library institutions based on InstitutionDTO (available non-stop in model as "institutions" at @RequestMapping("/donation")
    @ModelAttribute("institutions")
    public List<InstitutionDTO> institutions() {
        List<Institution> institutionList = institutionRepository.findAllByNameIsNotNullOrderByName();
        List<InstitutionDTO> institutionDTOList = new ArrayList<>();
        for (Institution institution: institutionList) {
            ModelMapper modelMapper = new ModelMapper();
            InstitutionDTO institutionDTO = modelMapper.map(institution, InstitutionDTO.class);
            institutionDTOList.add(institutionDTO);
        }
        return institutionDTOList;
    }


    // form GET & POST
    @GetMapping("/donation/form")
    public String getDonationPage (Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! GET FORM start !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        DonationDataDTO donationDataDTO = new DonationDataDTO();
//        donationDataDTO.setIfSaveForm(0);
//        donationDataDTO.setIfBackToForm(0);
        model.addAttribute("donationDataDTO", donationDataDTO);
        model.addAttribute("errorsMessageMap", null);
//        return "form-test-DTO";
        return "form";
    }
    @PostMapping("/donation/form")
    public String postDonationPage (@ModelAttribute @Valid DonationDataDTO donationDataDTO, BindingResult result, Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! POST FORM proceed !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("DonationController. result from FORM: {}", result.getFieldErrors());
        log.debug("DonationController. donationDataDTO from FORM: {}", donationDataDTO.toString());
        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            // (errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError: fieldErrorList) {
                errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", errorsMessageMap);
//            return "form-test-DTO";
            return "form";
        }

        // before summary-form reset parameters if back-to-form or save
        model.addAttribute("donationDataDTO", donationDataDTO);
//        return "redirect:/donation/form-summary";
        return "form-summary";
    }


    // form-summary POST
    // (no GET as applied instead direct "jump" into form-summary.jsp from postDonationPage i.e. from POST of form.jsp)
    @PostMapping("/donation/form-summary")
    public String postDonationSummaryPage (@ModelAttribute @Valid DonationDataDTO donationDataDTO,
                                           BindingResult result, Model model,
                                           @RequestParam Integer ifBackToForm) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! POST SUMMARY proceed !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("DonationController. result from SUMMARY: {}", result.getFieldErrors());
        log.debug("DonationController. donationDataDTO from SUMMARY: {}", donationDataDTO.toString());
        log.debug("DonationController. ifBackToForm from SUMMARY: {}", ifBackToForm);
        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError: fieldErrorList) {
                errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "form";
        }
        // In case user ASK TO REWRITE DATA
        if (ifBackToForm != null && ifBackToForm == 1) {
            return "form";
        }

        // In case user CONFIRMATION DATA
        // Mapping & saving data at method saveDonation (+ exception catch of both operation)
        try {
            donationService.saveDonation(donationDataDTO);
        } catch (SavingDataException e) {
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            errorsMessageMap.put("Błąd ogólny", e.getMessage());
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "form";
        }
        return "index";
    }
}