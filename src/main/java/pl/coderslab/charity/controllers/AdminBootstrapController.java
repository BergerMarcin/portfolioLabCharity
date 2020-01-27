package pl.coderslab.charity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.services.CurrentUser;
import pl.coderslab.charity.services.UserService;


@Controller
@RequestMapping("/admin/bootstrap")
@Slf4j
public class AdminBootstrapController {

    private UserService userService;

    public AdminBootstrapController(UserService userService) {
        this.userService = userService;
    }

    // ADMIN START PAGE/SERVLET
    @GetMapping("")
    public String getAdminStartPage (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! getAdminStartPage START !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        model.addAttribute("errorsMessageMap", null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userDTOList", userService.findAllByRoleNameAccAuthorisedRole("ROLE_ADMIN"));
        return "admin/admin-table";
    }

}
