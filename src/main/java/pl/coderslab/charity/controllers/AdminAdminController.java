package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dtos.UserDataDTO;
import pl.coderslab.charity.services.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/admin")
@Slf4j
public class AdminAdminController {

    private UserService userService;

    public AdminAdminController(UserService userService) {
        this.userService = userService;
    }

    // ADMIN ADMINS LIST-START PAGE
    @GetMapping
    public String getAdminAdminsPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDataDTOList", userService.findAllByRoleName("ROLE_ADMIN"));
        return "admin/admin-admin";
    }


    // ADMIN ADMINS ADD PAGE
    @GetMapping("/add")
    public String getAdminAdminsAddPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDataDTO", new UserDataDTO());
        return "admin/admin-admin-add";
    }

    @PostMapping("/add")
    public String postAdminAdminsAddPage(@ModelAttribute @Valid UserDataDTO userDataDTO,
                                               BindingResult result, Model model,
                                               @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsAddPage userDataDTO: {}", userDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsAddPage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/admin";
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
            return "admin/admin-admin-add";
        }

        if (formButtonChoice == 1) {
            // Mapping & saving data at method saveUser (+ exception catch of both operation)
//            try {
//                userService.saveUser(userDataDTO);
//            } catch (EntityToDataBaseException e) {
//                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
//                errorsMessageMap.put("Błąd ogólny", e.getMessage());
//                model.addAttribute("errorsMessageMap", errorsMessageMap);
//                return "admin/admin-admin-add";
//            }
        }

        return "redirect:/admin/admin";
    }


    // ADMIN ADMINS UPDATE PAGE
    @GetMapping("/update")
    public String getAdminAdminsUpdatePage(Long id, @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDataDTO", userService.findAllById(id));
        return "admin/admin-admin-update";
    }
    @PostMapping("/update")
    public String postAdminAdminsUpdatePage(@ModelAttribute @Valid UserDataDTO userDataDTO,
                                                  BindingResult result, Model model,
                                                  @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsUpdatePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsUpdatePage userDataDTO: {}", userDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsUpdatePage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/admin";
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
            return "admin/admin-admin-update";
        }

        if (formButtonChoice == 1) {
            // Mapping & update data at method saveUpdateUser (+ exception catch of both operation)
//            try {
//                userService.updateUser(userDataDTO);
//            } catch (EntityToDataBaseException e) {
//                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
//                errorsMessageMap.put("Błąd ogólny", e.getMessage());
//                model.addAttribute("errorsMessageMap", errorsMessageMap);
//                return "admin/admin-admin-update";
//            }
        }

        return "redirect:/admin/admin";
    }


    // ADMIN ADMINS DELETE PAGE
    @GetMapping("/delete")
    public String getAdminAdminsDeletePage(Long id, @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDataDTO", userService.findAllById(id));
        return "admin/admin-admin-delete";
    }
    @PostMapping("/delete")
    public String postAdminAdminsDeletePage(@ModelAttribute @Valid UserDataDTO userDataDTO,
                                                  BindingResult result, Model model,
                                                  @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsDeletePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsDeletePage userDataDTO: {}", userDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsDeletePage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/admin";
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
            return "admin/admin-admin-delete";
        }

        if (formButtonChoice == 1) {
//            userService.deleteUser(userDataDTO);
        }

        return "redirect:/admin/admin";
    }

}