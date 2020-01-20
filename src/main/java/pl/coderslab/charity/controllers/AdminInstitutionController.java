package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dtos.InstitutionAddDataDTO;
import pl.coderslab.charity.dtos.InstitutionDataDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;
import pl.coderslab.charity.services.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin/institution")
@Slf4j
public class AdminInstitutionController {

    // ID protects against unauthorised ID change when editing/update record
    private static Long idProtected = 0L;

    private InstitutionService institutionService;
    private DonationService donationService;
    private CommonForControllers commonForControllers;

    public AdminInstitutionController(InstitutionService institutionService, DonationService donationService,
                                      CommonForControllers commonForControllers) {
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.commonForControllers = commonForControllers;
    }

    private static Long getIdProtected() {return idProtected;}
    private static void setIdProtected(Long idProtected) {AdminInstitutionController.idProtected = idProtected;}


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
        model.addAttribute("institutionAddDataDTO", new InstitutionAddDataDTO());;
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
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "admin/admin-institution-add";
        }

        if (formButtonChoice == 1) {
            // Mapping & saving data at method saveInstitution (+ exception catch of both operation)
            try {
                institutionService.saveInstitution(institutionAddDataDTO);
            } catch (EntityToDataBaseException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny operacji. ", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                return "admin/admin-institution-add";
            }
        }

        return "redirect:/admin/institution";
    }


    // ADMIN INSTITUTIONS UPDATE PAGE
    @GetMapping("/update")
    public String getAdminInstitutionsUpdatePage(@AuthenticationPrincipal CurrentUser currentUser, Model model,
                                                 @RequestParam Long id) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionDataDTO", institutionService.institutionById(id));
        AdminInstitutionController.setIdProtected(id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminInstitutionsUpdatePage idProtected: {}", AdminInstitutionController.getIdProtected());
        return "admin/admin-institution-update";
    }

    @PostMapping("/update")
    public String postAdminInstitutionsUpdatePage(@ModelAttribute @Valid InstitutionDataDTO institutionDataDTO,
                                                  BindingResult result, Model model,
                                                  @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsUpdatePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsUpdatePage institutionDataDTO: {}", institutionDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsUpdatePage formButtonChoice: {}", formButtonChoice);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsUpdatePage idProtected: {}", AdminInstitutionController.getIdProtected());
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/institution";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "admin/admin-institution-update";
        }

        if (formButtonChoice == 1) {
            // Mapping & update data at method updateInstitution (+ exception catch of both operation)
            try {
                institutionService.updateInstitution(AdminInstitutionController.getIdProtected(), institutionDataDTO);
            } catch (EntityToDataBaseException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny operacji. ", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                return "admin/admin-institution-update";
            }
        }

        return "redirect:/admin/institution";
    }


    // ADMIN INSTITUTIONS DELETE PAGE
    @GetMapping("/delete")
    public String getAdminInstitutionsDeletePage(@AuthenticationPrincipal CurrentUser currentUser, Model model,
                                                 @RequestParam Long id) {
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("institutionDataDTO", institutionService.institutionById(id));
        model.addAttribute("donationDataDTOList", donationService.donationListByInstitutionId(id));
        AdminInstitutionController.setIdProtected(id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminInstitutionsDeletePage idProtected: {}", AdminInstitutionController.getIdProtected());
        return "admin/admin-institution-delete";
    }

    @PostMapping("/delete")
    public String postAdminInstitutionsDeletePage(@ModelAttribute @Valid InstitutionDataDTO institutionDataDTO,
                                                  BindingResult result, Model model,
                                                  @RequestParam Integer formButtonChoice) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsDeletePage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsDeletePage institutionDataDTO: {}", institutionDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsDeletePage formButtonChoice: {}", formButtonChoice);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! postAdminInstitutionsDeletePage idProtected: {}", AdminInstitutionController.getIdProtected());
        if (formButtonChoice == 0 || formButtonChoice == null) {
            return "redirect:/admin/institution";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "admin/admin-institution-delete";
        }

        if (formButtonChoice == 1) {
            // Mapping & delete data at method deleteInstitution (+ exception catch of both operation)
            try {
                institutionService.deleteInstitution(AdminInstitutionController.getIdProtected(), institutionDataDTO);
            } catch (EntityToDataBaseException e) {
                Map<String, String> errorsMessageMap = new LinkedHashMap<>();
                errorsMessageMap.put("Błąd ogólny operacji. ", e.getMessage());
                model.addAttribute("errorsMessageMap", errorsMessageMap);
                return "admin/admin-institution-delete";
            }
        }

        return "redirect:/admin/institution";
    }

}