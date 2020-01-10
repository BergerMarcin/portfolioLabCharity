package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.charity.services.CurrentUser;
import pl.coderslab.charity.services.DonationService;
import pl.coderslab.charity.services.InstitutionService;

import java.time.LocalDate;


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
            log.debug("currentUser FULL BASIC: {}", currentUser.toString());
            log.debug("currentUser FULL DETAILS: {}", currentUser.getCurrentUserDataDTO().toString());
        }
        return "index";
    }


}
