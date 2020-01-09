package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.charity.services.CurrentUser;
import pl.coderslab.charity.services.DonationService;
import pl.coderslab.charity.services.InstitutionService;

import java.time.LocalDate;
import java.util.List;


@Controller
@Slf4j
public class HomeController {

    private InstitutionService institutionService;
    private DonationService donationService;

    public HomeController(InstitutionService institutionService, DonationService donationService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
    }

    @GetMapping({"/", "/home", "/index", ""})
    public String homeAction(@AuthenticationPrincipal CurrentUser currentUser, Model model){

        //institutions list, bagsCount, supportedOrganizationsCount & currentUser for/@ index.jsp
        model.addAttribute("institutions",
                institutionService.allInstitutionList());
        model.addAttribute("bagsCount",
                donationService.bagsCountBeforeDate(LocalDate.now()));
        model.addAttribute("supportedOrganizationsCount",
                donationService.supportedOrganizationsCountBeforeDate(LocalDate.now()));
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            log.debug("Username: {}", currentUser.getUsername());
            log.debug("Password: {}", currentUser.getPassword());
            log.debug("Authorities: {}", currentUser.getAuthorities());
            log.debug("Class: {}", currentUser.getClass());
            log.debug("currentUser FULL BASIC: {}", currentUser.toString());
            log.debug("currentUser FULL DETAILS: {}", currentUser.getCurrentUserDataDTO().toString());
            log.debug("currentUser name: {},  {}", currentUser.getCurrentUserDataDTO().getFirstName(), currentUser.getCurrentUserDataDTO().getLastName());
            log.debug("currentUser email, city: {},  {}", currentUser.getCurrentUserDataDTO().getEmail(), currentUser.getCurrentUserDataDTO().getCity());
        }
        return "index";
    }


}
