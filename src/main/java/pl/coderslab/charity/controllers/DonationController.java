package pl.coderslab.charity.controllers;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dtos.CategoryDataDTO;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.dtos.InstitutionDataDTO;
import pl.coderslab.charity.services.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/donation")
@Slf4j
public class DonationController {

    private DonationService donationService;
    private CategoryService categoryService;
    private InstitutionService institutionService;

    public DonationController(DonationService donationService, CategoryService categoryService, InstitutionService institutionService) {
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.institutionService = institutionService;
    }

    // Library categories based on CategoryDTO (available non-stop in model as "categories" at @RequestMapping("/donation")
    @ModelAttribute("categoryDataDTOList")
    public List<CategoryDataDTO> categoryDataDTOList() {
        return categoryService.allCategoryDataDTOList();
    }

    // Library institutions based on InstitutionDTO (available non-stop in model as "institutionDataDTOList" at @RequestMapping("/donation")
    @ModelAttribute("institutionDataDTOList")
    public List<InstitutionDataDTO> institutionDataDTOList() {
        return institutionService.ifTrustedInstitutionDataDTOList(Boolean.TRUE);
    }


    // form GET & POST
// TODO : CONTINUE
    @GetMapping("/form")
    public String getDonationPage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! GET FORM start !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        DonationDataDTO donationDataDTO = new DonationDataDTO();
        model.addAttribute("donationDataDTO", donationDataDTO);
        model.addAttribute("errorsMessageMap", null);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            log.debug("currentUser FULL BASIC: {}", currentUser.toString());
            log.debug("currentUser FULL DETAILS: {}", currentUser.getCurrentUserDataDTO().toString());
        }
//        return "form-test-DTO";
        return "form";
    }
    @PostMapping("/form")
    public String postDonationPage (@ModelAttribute @Valid DonationDataDTO donationDataDTO,
                                    BindingResult result, Model model) {
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
    @PostMapping("/form-summary")
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