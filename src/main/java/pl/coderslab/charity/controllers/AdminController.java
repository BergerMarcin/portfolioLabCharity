package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.services.CurrentUser;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @GetMapping("")
    public String getAdminPage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! GET ADMIN start !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        DonationDataDTO donationDataDTO = new DonationDataDTO();
        model.addAttribute("donationDataDTO", donationDataDTO);
        model.addAttribute("errorsMessageMap", null);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            log.debug("currentUser FULL BASIC: {}", currentUser.toString());
            log.debug("currentUser FULL DETAILS: {}", currentUser.getCurrentUserDataDTO().toString());
        }
        return "admin";
    }

//    @PostMapping("")
//    public String postAdminPage (@ModelAttribute @Valid DonationDataDTO donationDataDTO,
//                                    BindingResult result, Model model) {
//        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! POST ADMIN proceed !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
//        log.debug("AdminController. result from FORM: {}", result.getFieldErrors());
//        log.debug("AdminController. donationDataDTO from FORM: {}", donationDataDTO.toString());
//        if (result.hasErrors()) {
//            // Taking field errors from result and creating errorsMessageMap
//            // (errorsMessageMap - a map of errors (key - field name, value - error message)
//            List<FieldError> fieldErrorList = result.getFieldErrors();
//            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
//            for (FieldError fieldError : fieldErrorList) {
//                errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
//            }
//            model.addAttribute("errorsMessageMap", errorsMessageMap);
//            return "admin";
//        }
//        // before summary-form reset parameters if back-to-form or save
//        model.addAttribute("donationDataDTO", donationDataDTO);
//        return "admin";
//    }
}
