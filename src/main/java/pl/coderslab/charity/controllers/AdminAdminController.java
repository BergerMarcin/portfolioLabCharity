package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dtos.RoleDTO;
import pl.coderslab.charity.dtos.UserDTO;
import pl.coderslab.charity.dtos.UserPassDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;
import pl.coderslab.charity.services.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    private DonationService donationService;
    private CommonForControllers commonForControllers;

    public AdminAdminController(UserService userService, RoleService roleService,
                                DonationService donationService, CommonForControllers commonForControllers) {
        this.userService = userService;
        this.roleService = roleService;
        this.donationService = donationService;
        this.commonForControllers = commonForControllers;
    }

    private static Long getIdProtected() {return idProtected;}
    private static void setIdProtected(Long idProtected) {AdminAdminController.idProtected = idProtected;}

    // Common roleDTOList library for all controlled views
    // ROLE_SUPERADMIN is hidden here so unavailable for update admin servlet (at adding new admin servlet is on
    //   default ROLE_ADMIN and might be extended with ROLE_USER by dedicated checkbox at view)
    // Beside at method save of UserServiceImpl ROLE_SUPERADMIN is always being removed
    @ModelAttribute("roleDTOList")
    public List<RoleDTO> roleDTOList() {
        List<RoleDTO> roleDTOList = roleService.findAll();
        List<RoleDTO> roleDTOListForAdminPanel = new ArrayList<>();
        for (RoleDTO roleDTO : roleDTOList) {
            if (!roleDTO.getName().toUpperCase().contains("SUPERADMIN")) {roleDTOListForAdminPanel.add(roleDTO);}
        }
        return roleDTOListForAdminPanel;
    }


    /** ADMIN ADMINS LIST-START PAGE
     * Only SUPERADMIN see all the admin: active and inactive (inactive means deleted)
     * @param currentUser
     * @param model
     * @return
     */
    @GetMapping
    public String getAdminAdminsPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDTOList", userService.findAllByRoleNameAccAuthorisedRole("ROLE_ADMIN"));
        return "admin/admin-admin";
    }


    /** ADMIN ADMINS ADD PAGE
     * Might be done only by SUPERADMIN. Password is set for new admin
     * @param currentUser
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String getAdminAdminAddPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        UserDTO userDTO = new UserDTO();
        userDTO.setActive(Boolean.TRUE);
        userDTO.setTermsAcceptance(Boolean.FALSE);
        model.addAttribute("userDTO", userDTO);
        return "admin/admin-admin-add";
    }

    @PostMapping("/add")
    public String postAdminAdminAddPage(@ModelAttribute @Valid UserDTO userDTO,
                                         BindingResult result, Model model,
                                         @RequestParam Boolean ifRoleUser,
                                         @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminAddPage userDTO: {}", userDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminAddPage ifRoleUser: {}", ifRoleUser);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminAddPage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/admin";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            // TODO: reset password & rePassword probably via FieldError of BindingResult. The same currentUser
            return "admin/admin-admin-add";
        }

        if (formButtonChoice == 1) {
            // Mapping & saving new Admin (+ exception catch of both operation)
            try {
                userService.saveNewAdmin(userDTO, ifRoleUser);
            } catch (EntityToDataBaseException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                // TODO: reset password & rePassword probably via FieldError of BindingResult. The same currentUser
                return "admin/admin-admin-add";
            }
        }

        return "redirect:/admin/admin";
    }


    /** ADMIN ADMINS UPDATE PAGE
     * Demand authorisation of updated admin's password or log-in SUPERADMIN's password
     * @param id
     * @param em
     * @param currentUser
     * @param model
     * @return
     */
    @GetMapping("/update")
    public String getAdminAdminUpdatePage(Long id, String em,
                                           @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        // preparing userDTO for viewer
        UserDTO userDTO = userService.findById(id);
        // additional validation (double check) of data from GET-request by checking email of user of id from request
        if (userDTO == null || em == null || !em.equals(userDTO.getEmail())) {
            return "redirect:/admin/admin";
        }
        userDTO.setEmail("x@x.xxx");
        userDTO.setPassword("");
        userDTO.setRePassword("");
        userDTO.setTermsAcceptance(Boolean.TRUE);
        model.addAttribute("userDTO", userDTO);

        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        AdminAdminController.setIdProtected(id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminUpdatePage idProtected: {}", AdminAdminController.getIdProtected());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminUpdatePage userDTO: {}", userDTO);
        return "admin/admin-admin-update";
    }

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
            // TODO: reset password & rePassword probably via FieldError of BindingResult. The same currentUser
            return "admin/admin-admin-update";
        }

        if (formButtonChoice == 1) {
            // Mapping & update data & emailing to previous email at method saveUpdateUser (+ exception catch of both operation)
            try {
                userService.updateAdmin(AdminAdminController.getIdProtected(), userDTO);
            } catch (EntityToDataBaseException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                // TODO: reset password & rePassword probably via FieldError of BindingResult. The same currentUser
                return "admin/admin-admin-update";
            }
        }

        return "redirect:/admin/admin";
    }



    // TODO:    - change email and password - probably new views


    /** ADMIN ADMINS DELETE PAGE
     * Only SUPERADMIN may delete ADMIN (BTW: ROLE_SUPERADMIN is added only from database console)
     * DELETE demands authorisation of SUPERADMIN password
     * @param id
     * @param em
     * @param currentUser
     * @param model
     * @return
     */
    @GetMapping("/delete")
    public String getAdminAdminDeletePage(Long id, String em,
                                          @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminDeletePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminDeletePage id: {}", id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminDeletePage em(email): {}", em);
        // Additional checking access - deleting possible only by SUPERADMIN
        if (!currentUser.getAuthorities().toString().contains("SUPERADMIN")) {
            return "redirect:/admin/admin";
        }
        // preparing userDTO for viewer
        UserDTO userDTO = userService.findById(id);
        userDTO.setPassword("");
        userDTO.setRePassword("");
        // additional validation (double check) of data from GET-request by checking email of user of id from request
        if (userDTO == null || em == null || !em.equals(userDTO.getEmail())) {
            return "redirect:/admin/admin";
        }
        userDTO.setEmail("x@x.xxx");
        userDTO.setTermsAcceptance(Boolean.TRUE);
        model.addAttribute("userDTO", userDTO);
        AdminAdminController.setIdProtected(id);

        // TODO: add user field to Donation entity & then uncomment below
        // Get related donations
//        model.addAttribute("donationDTOList", donationService.donationListByUserId(id));

        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminDeletePage idProtected: {}", AdminAdminController.getIdProtected());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminDeletePage userDTO: {}", userDTO);
        return "admin/admin-admin-delete";
    }

    @PostMapping("/delete")
    public String postAdminAdminDeletePage(@AuthenticationPrincipal CurrentUser currentUser,
                                           @ModelAttribute @Valid UserDTO userDTO,
                                           BindingResult result, Model model,
                                           @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminDeletePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminDeletePage userDTO: {}", userDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminDeletePage formButtonChoice: {}", formButtonChoice);
        // Additional checking access - deleting possible only by SUPERADMIN
        if (!currentUser.getAuthorities().toString().contains("SUPERADMIN")) {
            // TODO: reset password & rePassword probably via FieldError of BindingResult. The same currentUser
            return "redirect:/admin/admin";
        }
        if (formButtonChoice == 0 || formButtonChoice == null) {
            // TODO: reset password & rePassword probably via FieldError of BindingResult. The same currentUser
            return "redirect:/admin/admin";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            // TODO: reset password & rePassword probably via FieldError of BindingResult. The same currentUser
            return "admin/admin-admin-delete";
        }

        if (formButtonChoice == 1) {
            // Delete admin & emailing to previous email at method deleteUser (+ exception catch of both operation)
            try {
                userService.deleteAdmin(AdminAdminController.getIdProtected(), userDTO);
            } catch (EntityToDataBaseException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                // TODO: reset password & rePassword probably via FieldError of BindingResult. The same currentUser
                return "admin/admin-admin-delete";
            }
        }

        return "redirect:/admin/admin";
    }

}