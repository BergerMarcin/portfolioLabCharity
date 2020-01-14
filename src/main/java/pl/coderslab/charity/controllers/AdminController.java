package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dtos.InstitutionAddDataDTO;
import pl.coderslab.charity.dtos.InstitutionDataDTO;
import pl.coderslab.charity.services.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private InstitutionService institutionService;
    private CategoryService categoryService;
    private DonationService donationService;
    private UserService userService;

    public AdminController(InstitutionService institutionService, CategoryService categoryService,
                           DonationService donationService, UserService userService) {
        this.institutionService = institutionService;
        this.categoryService = categoryService;
        this.donationService = donationService;
        this.userService = userService;
    }

    // ADMIN START PAGE/SERVLET
    @GetMapping("")
    public String getAdminStartPage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminStartPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        return "admin/admin";
    }

    // ADMIN INSTITUTIONS LIST-START PAGE
    @GetMapping("/institutions")
    public String getAdminInstitutionsPage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutions", institutionService.allInstitutionDataDTOList());
        return "admin/admin-institutions";
    }


    // ADMIN INSTITUTIONS ADD PAGE
    @GetMapping("/institution/add")
    public String getAdminInstitutionsAddPage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionAddDataDTO", new InstitutionAddDataDTO());
        return "admin/admin-institution-add";
    }
    @PostMapping("/institution/add")
    public String postAdminInstitutionsAddPage (@ModelAttribute @Valid InstitutionAddDataDTO institutionAddDataDTO,
                                                BindingResult result, Model model,
                                                @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage institutionAddDataDTO: {}", institutionAddDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/institutions";
        }

        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError: fieldErrorList) {
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
                return "admin/admin-institution-update";
            }
        }

        return "redirect:/admin/institutions";
    }


    // ADMIN INSTITUTIONS UPDATE PAGE
    @GetMapping("/institution/update")
    public String getAdminInstitutionsUpdatePage (Long id, @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionDataDTO", institutionService.institutionById(id));
        return "admin/admin-institution-update";
    }
    @PostMapping("/institution/update")
    public String postAdminInstitutionsUpdatePage (@ModelAttribute @Valid InstitutionDataDTO institutionDataDTO,
                                                         BindingResult result, Model model,
                                                         @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage institutionDataDTO: {}", institutionDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/institutions";
        }

        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError: fieldErrorList) {
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

        return "redirect:/admin/institutions";
    }


    // ADMIN INSTITUTIONS DELETE PAGE
    @GetMapping("/institution/delete")
    public String getAdminInstitutionsDeletePage (Long id, @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionDataDTO", institutionService.institutionById(id));
        return "admin/admin-institution-delete";
    }
    @PostMapping("/institution/delete")
    public String postAdminInstitutionsDeletePage (@ModelAttribute @Valid InstitutionDataDTO institutionDataDTO,
                                                   BindingResult result, Model model,
                                                   @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage institutionDataDTO: {}", institutionDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/institutions";
        }

        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError: fieldErrorList) {
                errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "admin/admin-institution-delete";
        }

        if (formButtonChoice == 1) {
            institutionService.deleteInstitution(institutionDataDTO);
        }

        return "redirect:/admin/institutions";
    }


}
