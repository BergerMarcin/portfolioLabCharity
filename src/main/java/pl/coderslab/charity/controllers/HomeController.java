package pl.coderslab.charity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.domain.entities.Donation;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.DonationRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.services.DonationService;
import pl.coderslab.charity.services.InstitutionService;

import java.time.LocalDate;
import java.util.List;


@Controller
public class HomeController {

    private InstitutionService institutionService;
    private DonationService donationService;

    public HomeController(InstitutionService institutionService, DonationService donationService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
    }

    @GetMapping({"/", "/home", "/index", ""})
    public String homeAction(Model model){
        //institutions list, bagsCount and supportedOrganizationsCount for/@ index.jsp
        model.addAttribute("institutions",
                institutionService.allInstitutionList());
        model.addAttribute("bagsCount",
                donationService.bagsCountBeforeDate(LocalDate.now()));
        model.addAttribute("supportedOrganizationsCount",
                donationService.supportedOrganizationsCountBeforeDate(LocalDate.now()));

        return "index";
    }


}
