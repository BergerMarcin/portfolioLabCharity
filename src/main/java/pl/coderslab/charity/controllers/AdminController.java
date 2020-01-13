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
        model.addAttribute("institutions", institutionService.allInstitutionList());
        return "admin/admin-institutions";
    }

    // ADMIN INSTITUTIONS ADD PAGE
    @GetMapping("/institution/add")
    public String getAdminInstitutionsAddPage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionAddDataDTO", new InstitutionAddDataDTO());
//        model.addAttribute("ifConfirmCancel", Boolean.FALSE);
        return "admin/admin-institution-add";
    }
    @PostMapping("/institution/add")
    public String postAdminInstitutionsAddPage (@ModelAttribute @Valid InstitutionAddDataDTO institutionAddDataDTO,
                                                BindingResult result, Model model,
                                                @RequestParam Boolean ifConfirmCancel) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage institutionAddDataDTO: {}", institutionAddDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsAddPage ifConfirmCancel: {}", ifConfirmCancel);
        if (!ifConfirmCancel) {
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
        if (ifConfirmCancel) {
            // TODO: add record
            // institutionService.save(institutionAddDataDTO);
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



/*
    // PAGE/SERVLET of CHOOSING ROLE in case ADMIN has ROLES USER & ADMIN
    @GetMapping("/start/role_choice")
    public String getAdminStartRoleChoicePage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! GET ADMIN start role choice !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");


        model.addAttribute("errorsMessageMap", null);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            log.debug("currentUser FULL BASIC: {}", currentUser.toString());
            log.debug("currentUser FULL DETAILS: {}", currentUser.getCurrentUserDataDTO().toString());
        }
        return "admin-start-role_choice";
    }
    @PostMapping("/start/role_choice")
    public String postAdminStartRoleChoicePage (@AuthenticationPrincipal CurrentUser currentUser,
                                                @ModelAttribute @Valid AdminRoleChoiceDataDTO adminRoleChoiceDataDTO,
                                                BindingResult result, Model model,
                                                @RequestParam String chosenRoleName) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! POST ADMIN start role choicd !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("DonationController. result from POST: {}", result.getFieldErrors());
        log.debug("DonationController. donationDataDTO from POST: {}", adminRoleChoiceDataDTO.toString());
        log.debug("DonationController. chosenRoleName from POST: {}", chosenRoleName);
        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError: fieldErrorList) {
                errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "admin-start-role_choice";
        }
        // In case admin CHOSEN ROLE
        if (chosenRoleName.equals("ROLE_ADMIN")) {
            currentUser.getCurrentUserDataDTO().setChosenRole(chosenRoleName);
            return "redirect:/admin/start";
        }
        if (chosenRoleName.equals("ROLE_USER")) {
            currentUser.getCurrentUserDataDTO().setChosenRole(chosenRoleName);
            return "redirect:/";
        }
        // In case nothing chosen
        if (chosenRoleName == null) {
            return "admin-start-role_choice";
        }
        return "index";
    }
*/

}
