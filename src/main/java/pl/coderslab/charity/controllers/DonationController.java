package pl.coderslab.charity.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dtos.CategoryDTO;
import pl.coderslab.charity.dtos.DonationDTO;
import pl.coderslab.charity.dtos.InstitutionDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;
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
    private CommonForControllers commonForControllers;

    public DonationController(DonationService donationService, CategoryService categoryService,
                              InstitutionService institutionService, CommonForControllers commonForControllers) {
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.institutionService = institutionService;
        this.commonForControllers = commonForControllers;
    }

    // Library categories based on CategoryDTO (available non-stop in model as "categories" at @RequestMapping("/donation")
    @ModelAttribute("categoryDTOList")
    public List<CategoryDTO> categoryDTOList() {
        return categoryService.allCategoryDTOList();
    }

    // Library institutions based on InstitutionDTO (available non-stop in model as "institutionDTOList" at @RequestMapping("/donation")
    @ModelAttribute("institutionDTOList")
    public List<InstitutionDTO> institutionDTOList() {
        return institutionService.ifTrustedInstitutionDTOList(Boolean.TRUE);
    }


    // form GET & POST
// TODO : CONTINUE
    @GetMapping("/form")
    public String getDonationPage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! GET FORM start !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        DonationDTO donationDTO = new DonationDTO();
        model.addAttribute("donationDTO", donationDTO);
        model.addAttribute("errorsMessageMap", null);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            log.debug("currentUser FULL BASIC: {}", currentUser.toString());
            log.debug("currentUser FULL DETAILS: {}", currentUser.getCurrentUserDTO().toString());
        }
        return "form";
    }
    @PostMapping("/form")
    public String postDonationPage (@ModelAttribute @Valid DonationDTO donationDTO,
                                    BindingResult result, Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! POST FORM proceed !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("DonationController. donationDTO from FORM: {}", donationDTO.toString());
        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "form";
        }

        // before summary-form reset parameters if back-to-form or save
        model.addAttribute("donationDTO", donationDTO);
        return "form-summary";
    }


    // form-summary POST
    // (no GET as applied instead direct "jump" into form-summary.jsp from postDonationPage i.e. from POST of form.jsp)
    @PostMapping("/form-summary")
    public String postDonationSummaryPage (@ModelAttribute @Valid DonationDTO donationDTO,
                                           BindingResult result, Model model,
                                           @RequestParam Integer ifBackToForm) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! POST SUMMARY proceed !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("DonationController. result from SUMMARY: {}", result.getFieldErrors());
        log.debug("DonationController. donationDTO from SUMMARY: {}", donationDTO.toString());
        log.debug("DonationController. ifBackToForm from SUMMARY: {}", ifBackToForm);
        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "form";
        }
        // In case user ASK TO REWRITE DATA
        if (ifBackToForm != null && ifBackToForm == 1) {
            return "form";
        }

        // In case user CONFIRMATION DATA
        // Mapping & saving data at method saveDonation (+ exception catch of both operation)
        try {
            donationService.saveNewDonation(donationDTO);
        } catch (EntityToDataBaseException e) {
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            errorsMessageMap.put("Błąd ogólny operacji. ", e.getMessage());
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "form";
        }
        return "index";
    }
}