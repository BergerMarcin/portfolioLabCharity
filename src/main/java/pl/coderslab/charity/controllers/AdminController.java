package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.services.CurrentUser;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {


    // ADMIN START PAGE/SERVLET
    @GetMapping("")
    public String getAdminStartPage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        return "admin/admin";
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
