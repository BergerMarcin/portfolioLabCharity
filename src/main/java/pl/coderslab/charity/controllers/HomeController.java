package pl.coderslab.charity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.domain.entities.Donation;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.repositories.DonationRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;

import java.time.LocalDate;
import java.util.List;


@Controller
public class HomeController {

    private InstitutionRepository institutionRepository;
    private DonationRepository donationRepository;
    public HomeController(InstitutionRepository institutionRepository, DonationRepository donationRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
    }

    @GetMapping({"/", "/home", "/index", ""})
    public String homeAction(Model model){
        //institutions list @ index.jsp
//TODO change to Service
        List<Institution> institutions = institutionRepository.findAllByNameIsNotNullOrderByName();
        model.addAttribute("institutions", institutions);

//TODO change to Service, move counting into Service
        //bagsCount @ index.jsp
        List<Donation> donations = donationRepository.findAllByPickUpDateIsBefore(LocalDate.now());
        Long bagsCount = donations.stream().mapToLong(d -> d.getQuantity()).sum();
        model.addAttribute("bagsCount", bagsCount);

        //supportedOrganizationsCount @ index.jsp
        Long supportedOrganizationsCount = donationRepository.supportedOrganizationsCount(LocalDate.now());
        model.addAttribute("supportedOrganizationsCount", supportedOrganizationsCount);

        return "index";
    }


}
