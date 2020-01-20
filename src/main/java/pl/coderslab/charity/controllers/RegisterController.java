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
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/registration")
@Slf4j
public class RegisterController {

    private RegistrationService registrationService;
    private CommonForControllers commonForControllers;

    public RegisterController(RegistrationService registrationService, CommonForControllers commonForControllers) {
        this.registrationService = registrationService;
        this.commonForControllers = commonForControllers;
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
            model.addAttribute("errorsMessageMap", commonForControllers.errorsMessageToMap(result));
            return "register";
        }

        // Mapping & saving data at method register (+ exception catch of both operation)
        try {
            registrationService.register(registrationDataDTO);
        } catch (EntityToDataBaseException e) {
            Map<String, String> errorsMessageMap = new LinkedHashMap<>();
            errorsMessageMap.put("Błąd ogólny operacji. ", e.getMessage());
            model.addAttribute("errorsMessageMap", errorsMessageMap);
            return "register";
        }
        return "/login";
    }

    // TODO:
    //  - add summary&confirmation post page method

}
