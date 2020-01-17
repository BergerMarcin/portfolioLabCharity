package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.dtos.RoleDataDTO;
import pl.coderslab.charity.dtos.UserDataDTO;
import pl.coderslab.charity.dtos.UserInfoDataDTO;
import pl.coderslab.charity.services.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


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
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminsAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDataDTO", new UserDataDTO());
        return "admin/admin-admin-add";
    }

    @PostMapping("/add")
    public String postAdminAdminsAddPage(@ModelAttribute @Valid UserDataDTO userDataDTO,
                                         BindingResult result, Model model,
                                         @RequestParam Boolean roleUser,
                                         @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsAddPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsAddPage userDataDTO: {}", userDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsAddPage formButtonChoice: {}", formButtonChoice);
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/admin";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "admin/admin-admin-add";
        }

        if (formButtonChoice == 1) {
            // Set userDataDTO.roleDataDTOList
            List<Role> roleList = new ArrayList<>();
            roleList.add(roleService.findAllByName("ROLE_ADMIN"));
            if (roleUser) {
                roleList.add(roleService.findAllByName("ROLE_USER"));
            }
            List<RoleDataDTO> roleDataDTOList = new ArrayList();
            for (Role role:roleList) {
                ModelMapper modelMapper = new ModelMapper();
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
                RoleDataDTO roleDataDTO = modelMapper.map(role, RoleDataDTO.class);
                roleDataDTOList.add(roleDataDTO);
            }
            userDataDTO.setRoleDataDTOList(roleDataDTOList);
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminAdminsAddPage userDataDTO.roleDataDTOList: {}", userDataDTO.getRoleDataDTOList().toString());

            // Mapping & saving data at method saveUser (+ exception catch of both operation)
//            try {
//                adminService.saveAdmin(userDataDTO, roleUser);
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
        AdminAdminController.setIdProtected(id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminAdminUpdatePage idProtected: {}", AdminAdminController.getIdProtected());
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
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "admin/admin-admin-update";
        }

        if (formButtonChoice == 1) {
            // Mapping & update data & emailing to previous email at method saveUpdateUser (+ exception catch of both operation)
//            try {
//                userService.updateUser(AdminAdminController.getIdProtected(), userDataDTO, roleUser);
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
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "admin/admin-admin-delete";
        }

        if (formButtonChoice == 1) {
//            userService.deleteUser(userDataDTO);
        }

        return "redirect:/admin/admin";
    }

}