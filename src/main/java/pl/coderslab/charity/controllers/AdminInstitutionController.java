package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.dtos.InstitutionAddDataDTO;
import pl.coderslab.charity.dtos.InstitutionDataDTO;
import pl.coderslab.charity.services.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/institution")
@Slf4j
public class AdminInstitutionController {

    private InstitutionService institutionService;
    private DonationService donationService;

    public AdminInstitutionController(InstitutionService institutionService, DonationService donationService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
    }

    // ADMIN INSTITUTIONS LIST-START PAGE
    @GetMapping
    public String getAdminInstitutionsPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionDataDTOList", institutionService.allInstitutionDataDTOList());
        return "admin/admin-institution";
    }


    // ADMIN INSTITUTIONS ADD PAGE
    @GetMapping("/add")
    public String getAdminInstitutionsAddPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionAddDataDTO", new InstitutionAddDataDTO());
        return "admin/admin-institution-add";
    }

    @PostMapping("/add")
    public String postAdminInstitutionsAddPage(@ModelAttribute @Valid InstitutionAddDataDTO institutionAddDataDTO,
                                               BindingResult result, Model model,
                                               @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage institutionAddDataDTO: {}", institutionAddDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/institution";
        }

        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError : fieldErrorList) {
                errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "admin/admin-institution-add";
        }

        if (formButtonChoice == 1) {
            // Mapping & saving data at method saveInstitution (+ exception catch of both operation)
            try {
                institutionService.saveInstitution(institutionAddDataDTO);
            } catch (SavingDataException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                return "admin/admin-institution-add";
            }
        }

        return "redirect:/admin/institution";
    }


    // ADMIN INSTITUTIONS UPDATE PAGE
    @GetMapping("/update")
    public String getAdminInstitutionsUpdatePage(Long id, @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionDataDTO", institutionService.institutionById(id));
        return "admin/admin-institution-update";
    }

    @PostMapping("/update")
    public String postAdminInstitutionsUpdatePage(@ModelAttribute @Valid InstitutionDataDTO institutionDataDTO,
                                                  BindingResult result, Model model,
                                                  @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsUpdatePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsUpdatePage institutionDataDTO: {}", institutionDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsUpdatePage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/institution";
        }

        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError : fieldErrorList) {
                errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "admin/admin-institution-update";
        }

        if (formButtonChoice == 1) {
            // Mapping & update data at method saveUpdateInstitution (+ exception catch of both operation)
            try {
                institutionService.updateInstitution(institutionDataDTO);
            } catch (SavingDataException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                return "admin/admin-institution-update";
            }
        }

        return "redirect:/admin/institution";
    }


    // ADMIN INSTITUTIONS DELETE PAGE
    @GetMapping("/delete")
    public String getAdminInstitutionsDeletePage(Long id, @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionDataDTO", institutionService.institutionById(id));
        model.addAttribute("donationDataDTOList", donationService.donationListByInstitutionId(id));
        return "admin/admin-institution-delete";
    }
    @PostMapping("/delete")
    public String postAdminInstitutionsDeletePage(@ModelAttribute @Valid InstitutionDataDTO institutionDataDTO,
                                                  BindingResult result, Model model,
                                                  @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsDeletePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsDeletePage institutionDataDTO: {}", institutionDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsDeletePage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/institution";
        }

        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError : fieldErrorList) {
                errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "admin/admin-institution-delete";
        }

        if (formButtonChoice == 1) {
            institutionService.deleteInstitution(institutionDataDTO);
        }

        return "redirect:/admin/institution";
    }

}