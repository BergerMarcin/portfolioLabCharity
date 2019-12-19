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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/donation")
@Slf4j
public class DonationController {

    private DonationDataDTO donationDataDTO = new DonationDataDTO();

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
        model.addAttribute("errorsMessage", "Errors view TEST");
        model.addAttribute("donationDataDTO", this.donationDataDTO);
//        return "form-test-DTO";
        return "form";
    }

    @PostMapping("/form")
    public String postDonationPage (@ModelAttribute @Valid DonationDataDTO donationDataDTOTemp, BindingResult result, Model model) {
        System.out.println("DonationController. POST procedure");
        System.out.println("DonationController. result: " + result.getFieldErrors());
        System.out.println("DonationController. result TYPE: " + result.getFieldErrors().get(0).getClass());
        System.out.println("DonationController. result ARRAY: " + result.getFieldErrors().toArray());
        System.out.println("DonationController. result ARRAY [0]: " + result.getFieldErrors().toArray()[0]);
        System.out.println("DonationController. result ARRAY [0] TYPE: " + result.getFieldErrors().toArray()[0].getClass());
        result.getFieldErrors().stream().forEach(System.out::println);
        List<FieldError> fieldErrorList = result.getFieldErrors();
        System.out.println();
        System.out.println();
        System.out.println("Error list:");
        for (FieldError fieldError: fieldErrorList) {
            System.out.println(fieldError.getField() + ":   " + fieldError.getCode() + "   " + fieldError.getDefaultMessage());
        }
        log.debug("DonationController. result: {}", result.getFieldErrors());
        if (result.hasErrors()) {
            model.addAttribute("errorsMessage", result.getFieldErrors());
//            return "form-test-DTO";
            return "redirect:/donation/form/#data-step-1";
        }
        System.out.println("DonationController. . DonationDataDTO: " + this.donationDataDTO);
        model.addAttribute("errorsMessage", "");
        this.donationDataDTO = donationDataDTOTemp;
        log.debug("DonationController. DonationDataDTO: {}", this.donationDataDTO);
        return "redirect:/donation/form-confirmation";
    }


    @GetMapping("/form-confirmation")
    public String getDonationSummaryPage (Model model) {
        model.addAttribute("donationDataDTO", this.donationDataDTO);
        return "form-confirmation";
    }

    @PostMapping("/form-confirmation")
    public String postDonationSummaryPage (@ModelAttribute @Valid DonationDataDTO donationDataDTOTemp, BindingResult result, Model model) {
        log.debug("DonationController. result: {}", result.getFieldErrors());
        if (result.hasErrors()) {
            return "redirect:/donation/form-confirmation";
        }
        this.donationDataDTO = donationDataDTOTemp;
        log.debug("DonationController. DonationDataDTO: {}", this.donationDataDTO);
        donationService.saveDonation(this.donationDataDTO);

        // After positive fill-in data, confirmation & saving reset general object donationDataDTO
        this.donationDataDTO = new DonationDataDTO();
        return "redirect:/";
    }
}
