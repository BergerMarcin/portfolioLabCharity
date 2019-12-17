package pl.coderslab.charity.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import java.util.List;

@Controller
@RequestMapping("/donation")
public class DonationController {

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
        model.addAttribute("donationDataDTO", new DonationDataDTO());
        return "form-test-DTO";
        //return "form";
    }

    @PostMapping("/form")
    public String postDonationPage (@ModelAttribute @Valid DonationDataDTO donationDataDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "form-test-DTO";
            //return "form";
        }

        donationService.saveDonation(donationDataDTO);
        return "redirect:/";
    }
}
