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
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.CategoryRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.dtos.CategoryDTO;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.dtos.InstitutionDTO;
import pl.coderslab.charity.services.DonationService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/donation")
@Slf4j
public class DonationController {

    private DonationDataDTO donationDataDTOMain = new DonationDataDTO();
    private Map<String, String> errorsMessageMap = new HashMap<>();

    private DonationService donationService;
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
        if (this.errorsMessageMap.size() < 1 || this.errorsMessageMap == null) {
            this.errorsMessageMap.put("test", "Errors view TEST");
        }
        model.addAttribute("errorsMessageMap", this.errorsMessageMap);
        model.addAttribute("donationDataDTO", this.donationDataDTOMain);
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
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            this.errorsMessageMap = new HashMap<>();
            for (FieldError fieldError: fieldErrorList) {
                this.errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", this.errorsMessageMap);
            log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!!  donationDataDTOMain errors detected: {}", this.donationDataDTOMain.toString());
//            return "form-test-DTO";
            return "redirect:/donation/form/#data-step-1";
        }
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!!  donationDataDTOMain before taking data from the FORM: {}", this.donationDataDTOMain.toString());
        model.addAttribute("errorsMessageMap", new HashMap<>());
        this.donationDataDTOMain = donationDataDTO;
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!!  donationDataDTOMain after taking data from the FORM: {}", this.donationDataDTOMain.toString());
        return "redirect:/donation/form-summary";
    }


    // form-summary GET & POST
    @GetMapping("/donation/form-summary")
    public String getDonationSummaryPage (Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! GET SUMMARY proceed !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        model.addAttribute("donationDataDTO", this.donationDataDTOMain);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!!  donationDataDTOMain to SUMMARY {}", this.donationDataDTOMain.toString());
        return "form-summary";
    }

    @PostMapping("/donation/form-summary")
    public String postDonationSummaryPage (@ModelAttribute @Valid DonationDataDTO donationDataDTO, BindingResult result, Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! POST SUMMARY proceed !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("DonationController. result from SUMMARY: {}", result.getFieldErrors());
        if (result.hasErrors()) {
            return "redirect:/donation/#data-step-1";
        }
        this.donationDataDTOMain = donationDataDTO;
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!!  donationDataDTOMain FINAL: {}", this.donationDataDTOMain);
        donationService.saveDonation(this.donationDataDTOMain);

        // After positive fill-in data, confirmation & saving reset general object donationDataDTO
        this.donationDataDTOMain = new DonationDataDTO();
        return "redirect:/";
    }
}