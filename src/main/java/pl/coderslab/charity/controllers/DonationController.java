package pl.coderslab.charity.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.dtos.DonationDataDTO;
import pl.coderslab.charity.services.DonationService;

import javax.validation.Valid;

@Controller

public class DonationController {

    private DonationService donationService;
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping("donation/form")
    public String getDonationPage (Model model) {
        model.addAttribute("donationData", new DonationDataDTO());
        return "donation/form";
    }

    @PostMapping("donation/form")
    public String postDonationPage (@ModelAttribute @Valid DonationDataDTO donationDataDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "donation/form";
        }

        donationService.saveDonation(donationDataDTO);
        return "redirect:/";
    }
}
