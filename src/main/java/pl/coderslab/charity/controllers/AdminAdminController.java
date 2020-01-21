package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dtos.RoleDTO;
import pl.coderslab.charity.dtos.UserDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;
import pl.coderslab.charity.services.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/admin")
@Slf4j
public class AdminAdminController {

    // ID & email protected against unauthorised ID change when editing/update record
    private static Long idProtected = 0L;

    private UserService userService;
    private RoleService roleService;
    private CommonForControllers commonForControllers;

    public AdminAdminController(UserService userService, RoleService roleService,
                                CommonForControllers commonForControllers) {
        this.userService = userService;
        this.roleService = roleService;
        this.commonForControllers = commonForControllers;
    }

    private static Long getIdProtected() {return idProtected;}
    private static void setIdProtected(Long idProtected) {AdminAdminController.idProtected = idProtected;}

    // Common roleDTOList library for all controlled views
    @ModelAttribute("roleDTOList")
    public List<RoleDTO> roleDTOList() { return roleService.findAll(); }


    // ADMIN ADMINS LIST-START PAGE
    @GetMapping
    public String getAdminAdminsPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDTOList", userService.findAllByRoleName("ROLE_ADMIN"));
        return "admin/admin-admin";
    }


    // ADMIN ADMINS ADD PAGE
    @GetMapping("/add")
    public String getAdminAdminAddPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDTO", new UserDTO());
        return "admin/admin-admin-add";
    }

    @PostMapping("/add")
    public String postAdminAdminAddPage(@ModelAttribute @Valid UserDTO userDTO,
                                         BindingResult result, Model model,
                                         @RequestParam Boolean roleUser,
                                         @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminAddPage userDTO: {}", userDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminAddPage roleUser: {}", roleUser);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminAddPage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/admin";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            // TODO: reset password & rePassword probably via FieldError of BindingResult
            return "admin/admin-admin-add";
        }

        if (formButtonChoice == 1) {
            // Mapping & saving new Admin (+ exception catch of both operation)
            try {
                userService.saveNewAdmin(userDTO, roleUser);
            } catch (EntityToDataBaseException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny operacji. ", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                // TODO: reset password & rePassword probably via FieldError of BindingResult
                return "admin/admin-admin-add";
            }
        }

        return "redirect:/admin/admin";
    }


    // ADMIN ADMINS UPDATE PAGE
    @GetMapping("/update")
    public String getAdminAdminUpdatePage(Long id, String em,
                                           @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        UserDTO userDTO = userService.findById(id);
        // additional validation (double check) of data from GET-request by checking email of user of id from request
        if (userDTO == null || em == null || !em.equals(userDTO.getEmail())) {
            return "redirect:/admin/admin";
        }
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        userDTO.setEmail("x@x.xxx");
        userDTO.setPassword("");
        userDTO.setRePassword("");
        userDTO.setTermsAcceptance(Boolean.TRUE);
        model.addAttribute("userDTO", userDTO);
        AdminAdminController.setIdProtected(id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminUpdatePage idProtected: {}", AdminAdminController.getIdProtected());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminUpdatePage userDTO: {}", userDTO);
        return "admin/admin-admin-update";
    }

    // TODO:    - veryfing password
    //          - issue with saving - not saving (probably again due to failed email verification)
    //          - change email and password - probably new views
    //          - check issue with email existing
    @PostMapping("/update")
    public String postAdminAdminUpdatePage(@ModelAttribute @Valid UserDTO userDTO,
                                            BindingResult result, Model model,
                                            @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminUpdatePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminUpdatePage userDTO: {}", userDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminUpdatePage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/admin";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            // TODO: reset password & rePassword probably via FieldError of BindingResult
            return "admin/admin-admin-update";
        }

        if (formButtonChoice == 1) {
            // Mapping & update data & emailing to previous email at method saveUpdateUser (+ exception catch of both operation)
            try {
                userService.updateAdmin(AdminAdminController.getIdProtected(), userDTO);
            } catch (EntityToDataBaseException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny operacji. ", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                // TODO: reset password & rePassword probably via FieldError of BindingResult
                return "admin/admin-admin-update";
            }
        }

        return "redirect:/admin/admin";
    }


    // ADMIN ADMINS DELETE PAGE
    @GetMapping("/delete")
    public String getAdminAdminsDeletePage(Long id, @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDTO", userService.findById(id));
        return "admin/admin-admin-delete";
    }
    @PostMapping("/delete")
    public String postAdminAdminsDeletePage(@ModelAttribute @Valid UserDTO userDTO,
                                                  BindingResult result, Model model,
                                                  @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsDeletePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsDeletePage userDTO: {}", userDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsDeletePage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/admin";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "admin/admin-admin-delete";
        }

        if (formButtonChoice == 1) {
//            userService.deleteUser(userDTO);
        }

        return "redirect:/admin/admin";
    }

}