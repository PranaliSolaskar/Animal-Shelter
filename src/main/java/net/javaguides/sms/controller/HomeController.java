package net.javaguides.sms.controller;

import net.javaguides.sms.entities.donation.DonationService;
import net.javaguides.sms.entities.log.LogService;
import net.javaguides.sms.entities.news.NewsService;
import net.javaguides.sms.entities.pet.services.PetAdoptedService;
import net.javaguides.sms.entities.pet.services.PetService;
import net.javaguides.sms.entities.profile.Profile;
import net.javaguides.sms.entities.request.RequestService;
import net.javaguides.sms.entities.user.User;
import net.javaguides.sms.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller()
public class HomeController {

    @Autowired
    private NewsService newsService;
    
    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private PetService petService;

    @Autowired
    private PetAdoptedService petAdoptedService;

    @Autowired
    private LogService logService;
    
    @GetMapping("/pstudents")
	public String listStudents(Model model) {
    	System.out.println("uuuuu");
		model.addAttribute("students", petService.findAll());
		return "index";
	}
    
    @GetMapping("/ppp")
    public String index(Model model) {
    	System.out.println("ppppwww");
        model.addAttribute("fix", "fix");
        model.addAttribute("mainNews", newsService.findMainNews());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, Profile profile, RedirectAttributes redirectAttributes) {
        model.addAttribute("profile", profile);
        System.out.println("ppppwww");
        return "views/authentication/login";
    }

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model, Profile profile) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "views/authentication/login";
    }

    @GetMapping("/about")
    public String about() {
        return "views/about";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication, HttpSession httpSession) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        user.ifPresent(value -> httpSession.setAttribute("user", value));
        if (user.isPresent()) {
            boolean isAdopted = userService.isAdopter(username);
            if (isAdopted) {
                model.addAttribute("myPetsFavorites", user.get().getFavoitesPets().size());
                model.addAttribute("myAcceptedRequests", petAdoptedService.countByUserRequestAccepted(user.get().getId()));
                model.addAttribute("myPendingRequests", petAdoptedService.countByUserRequestPending(user.get().getId()));
                model.addAttribute("myCancelledRequests", petAdoptedService.countByUserRequestCanceled(user.get().getId()));
                model.addAttribute("myDonationQuantity", donationService.sumCuantitybyUserId(user.get().getId()));
            } else {
                model.addAttribute("petsCount", petService.countTotal());
                model.addAttribute("petsActive", petService.coutnByIsActive(true));
                model.addAttribute("petsDeactivate", petService.coutnByIsActive(false));
                model.addAttribute("petsAdopted", petService.coutnByIsAdopted(true));
                model.addAttribute("petsTop", petService.findTopFive());
                model.addAttribute("usersCount", userService.countTotal());
                model.addAttribute("usersVolunteer", userService.countVolunteer());
                model.addAttribute("usersAdopter", userService.countAdopter());
                model.addAttribute("usersRequest", requestService.findPending());
                model.addAttribute("blogCount", newsService.countNews());
                model.addAttribute("blogMain", newsService.countMainNews());
                model.addAttribute("blogPublished", newsService.countPublishedNews());
                model.addAttribute("donationCuantity", donationService.sumCuantity());
                model.addAttribute("donationTop5", donationService.findTop5());
            }
        }
        return "views/dashboard";
    }

    @GetMapping("/noscript")
    public String noscript() {
        return "views/errorpages/noscript";
    }

    @GetMapping("/logs")
    public String logs(Model model) {
        model.addAttribute("list", logService.findAll());
        return "views/logs";
    }
}
