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
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.CategoryRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.services.DonationService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/donation")
@Slf4j
public class DonationController {

    private DonationDataDTO donationDataDTOMain = new DonationDataDTO();
    private Map<String, String> errorsMessageMap = new HashMap<>();

    private DonationService donationService;
    private CategoryRepository categoryRepository;
    private InstitutionRepository institutionRepository;

    public DonationController(DonationService donationService,
                              CategoryRepository categoryRepository,
                              InstitutionRepository institutionRepository) {
        this.donationService = donationService;
        this.categoryRepository = categoryRepository;
        this.institutionRepository = institutionRepository;
    }

    // Library categories (available non-stop in model as "categories" at @RequestMapping("/donation")
    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryRepository.findAllNameNotEmptyOrderByName();
    }

    // Library categories (available non-stop in model as "categories" at @RequestMapping("/donation")
    @ModelAttribute("institutions")
    public List<Institution> institutions() {
        return institutionRepository.findAllNameNotEmptyOrderByName();
    }


    @GetMapping("/form")
    public String getDonationPage (Model model) {
        System.out.println("DonationController. START");
        if (this.errorsMessageMap.size() < 1 || this.errorsMessageMap == null) {
            this.errorsMessageMap.put("test", "Errors view TEST");
        }
        model.addAttribute("errorsMessageMap", this.errorsMessageMap);
        model.addAttribute("donationDataDTO", this.donationDataDTOMain);
//        return "form-test-DTO";
        return "form";
    }

    @PostMapping("/form")
    public String postDonationPage (@ModelAttribute @Valid DonationDataDTO donationDataDTO, BindingResult result, Model model) {
        System.out.println("DonationController. POST procedure");
//        System.out.println(donationDataDTO.getCategories().get(0));
        if (result.hasErrors()) {
/*          // Initial test of object result of BindingResult class
            log.debug("DonationController. result: {}", result.getFieldErrors());
            System.out.println("DonationController. result: " + result.getFieldErrors());
            System.out.println("DonationController. result TYPE: " + result.getFieldErrors().get(0).getClass()); // return FieldError class
            System.out.println("DonationController. result ARRAY: " + result.getFieldErrors().toArray());
            System.out.println("DonationController. result ARRAY [0]: " + result.getFieldErrors().toArray()[0]);
            System.out.println("DonationController. result ARRAY [0] TYPE: " + result.getFieldErrors().toArray()[0].getClass());
            result.getFieldErrors().stream().forEach(System.out::println);

            System.out.println();
            System.out.println();
            System.out.println("Error list:");
            List<FieldError> fieldErrorList = result.getFieldErrors();
            this.errorsMessageMap = new HashMap<>();
            for (FieldError fieldError: fieldErrorList) {
                this.errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                System.out.println(fieldError.getField() + ":   " + fieldError.getCode() + "   " + fieldError.getDefaultMessage());
            }

            System.out.println();
            System.out.println();
            System.out.println("Error list - map:");
            for (Map.Entry<String, String> map: this.errorsMessageMap.entrySet()) {
                System.out.println(map.getKey() + ":   " + map.getValue());
            }
*/

            // Taking field errors from result and creating errorsMessageMap
            //    errorsMessageMap - a map of errors (key - field name, value - error message)
            List<FieldError> fieldErrorList = result.getFieldErrors();
            this.errorsMessageMap = new HashMap<>();
            for (FieldError fieldError: fieldErrorList) {
                this.errorsMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errorsMessageMap", this.errorsMessageMap);
//            return "form-test-DTO";
            return "redirect:/donation/form/#data-step-1";
        }
        System.out.println("DonationController. donationDataDTOMain before write-in data from viewer form.jsp: " + this.donationDataDTOMain);
        model.addAttribute("errorsMessageMap", new HashMap<>());
        this.donationDataDTOMain = donationDataDTO;
        System.out.println("DonationController. donationDataDTOMain after write-in data from viewer form.jsp: " + this.donationDataDTOMain);
        log.debug("DonationController. donationDataDTOMain after write-in data from viewer form.jsp: {}", this.donationDataDTOMain);
        return "redirect:/donation/form-confirmation";
    }


    @GetMapping("/form-confirmation")
    public String getDonationSummaryPage (Model model) {
        model.addAttribute("donationDataDTO", this.donationDataDTOMain);
        return "form-confirmation";
    }

    @PostMapping("/form-confirmation")
    public String postDonationSummaryPage (@ModelAttribute @Valid DonationDataDTO donationDataDTO, BindingResult result, Model model) {
        log.debug("DonationController. result: {}", result.getFieldErrors());
        if (result.hasErrors()) {
            return "redirect:/donation/form-confirmation";
        }
        this.donationDataDTOMain = donationDataDTO;
        System.out.println("DonationController. donationDataDTOMain after write-in data from viewer form.jsp: " + this.donationDataDTOMain);
        log.debug("DonationController. donationDataDTOMain after write-in data from viewer form.jsp: {}", this.donationDataDTOMain);
        donationService.saveDonation(this.donationDataDTOMain);

        // After positive fill-in data, confirmation & saving reset general object donationDataDTO
        this.donationDataDTOMain = new DonationDataDTO();
        return "redirect:/";
    }
}
