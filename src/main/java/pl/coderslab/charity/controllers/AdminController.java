package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.dtos.AdminRoleChoiceDataDTO;
import pl.coderslab.charity.services.CurrentUser;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    // PAGE/SERVLET of CHOOSING ROLE in case ADMIN has ROLES USER & ADMIN
    @GetMapping("/start/role_choice")
    public String getAdminStartRoleChoicePage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! GET ADMIN start role choice !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");

        if (currentUser.getCurrentUserDataDTO().getRoles().size()<2) {
            for (Role role: currentUser.getCurrentUserDataDTO().getRoles()) {
                if (role.getName().equals("ROLE_ADMIN")) {
                    currentUser.getCurrentUserDataDTO().setChosenRole(role.getName());
                }
            }
            if (currentUser.getCurrentUserDataDTO().getChosenRole().equals("ROLE_ADMIN")) {
                return "redirect:/admin/start";
            } else {
                return "redirect:/";
            }
        }
        AdminRoleChoiceDataDTO adminRoleChoiceDataDTO = new AdminRoleChoiceDataDTO();
        adminRoleChoiceDataDTO.setRoles(currentUser.getCurrentUserDataDTO().getRoles());
        model.addAttribute("adminRoleChoiceDataDTO", adminRoleChoiceDataDTO);

        model.addAttribute("errorsMessageMap", null);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            log.debug("currentUser FULL BASIC: {}", currentUser.toString());
            log.debug("currentUser FULL DETAILS: {}", currentUser.getCurrentUserDataDTO().toString());
            return "index";
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

    // ADMIN START PAGE/SERVLET
    @GetMapping("/start")
    public String getAdminStartPage (@AuthenticationPrincipal CurrentUser currentUser) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! GET ADMIN start !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        return "admin-start";
    }
}
