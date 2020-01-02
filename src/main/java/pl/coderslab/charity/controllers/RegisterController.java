package pl.coderslab.charity.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.dtos.RegistrationDataDTO;
import pl.coderslab.charity.services.RegistrationService;
import pl.coderslab.charity.services.SavingDataException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/registration")
@Slf4j
public class RegisterController {

    private RegistrationService registrationService;

    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/form")
    public String getRegistrationPage(Model model){
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! GET REGISTRATION start !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        RegistrationDataDTO registrationDataDTO = new RegistrationDataDTO();
        model.addAttribute("registrationDataDTO", registrationDataDTO);
        model.addAttribute("errorsMessageMap", null);
        return "register";
    }

    @PostMapping("/form")
    public String postRegistrationPage(@ModelAttribute @Valid RegistrationDataDTO registrationDataDTO,
                                       BindingResult result, Model model){
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! POST REGISTRATION proceed !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("RegisterController. result from REGISTRATION: {}", result.getFieldErrors());
        log.debug("RegisterController. registrationDataDTO from REGISTRATION: {}", registrationDataDTO.toString());
        if (result.hasErrors()) {
            // Taking field errors from result and creating errorsMessageMap
            // (errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            for (FieldError fieldError: fieldErrorList) {
                errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "register";
        }

        // Mapping & saving data at method register (+ exception catch of both operation)
        try {
            registrationService.register(registrationDataDTO);
        } catch (SavingDataException e) {
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            errorsMessageMap.put("Błąd ogólny", e.getMessage());
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "register";
        }
        return "/index";
    }

    // TODO:
    //  - add summary&confirmation post page method
    //  - add log-in just after success register

}
