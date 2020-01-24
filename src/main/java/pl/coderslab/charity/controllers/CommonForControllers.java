package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class of common methods for Controllers
 */
@Component
@Slf4j
public class CommonForControllers {

    /**
     * Transform list of errors (class BindingResult) into map
     * That map of error messages is easier to present in View (then object of class BindingResult)
     * @param result    - errors class (class BindingResult) from Spring Validation
     * @return          - map: key - field name, value - error message
     */
    public Map<String, String> errorsMessageToMap (BindingResult result) {
        log.debug("!!!!!!!!!!!!!!!!!! CommonForControllers. errorsMessageToMap result: {}", result.getFieldErrors());
        List<FieldError> fieldErrorList = result.getFieldErrors();
        Map<String, String> errorMessagesMap = new LinkedHashMap<>();
        for (FieldError fieldError : fieldErrorList) {
            errorMessagesMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.debug("!!!!!!!!!!!!!!!!!! CommonForControllers. errorsMessageToMap errorMessagesMap: {}", errorMessagesMap.toString());
        return errorMessagesMap;
    }

}
