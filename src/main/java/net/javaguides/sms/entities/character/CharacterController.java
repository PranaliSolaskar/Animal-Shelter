package net.javaguides.sms.entities.character;

import net.javaguides.sms.entities.user.User;
import net.javaguides.sms.entities.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/character")
public class CharacterController {

    private static final String CHARACTER = "redirect:/character/";
    private static final String CHARACTERFORM = "views/resources/character/characterForm";
    private static final String CHARACTERLIST = "views/resources/character/characterList";

    
    private CharacterService characterService;

    
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(CharacterController.class);

    @GetMapping("/")
    @Secured("ROLE_ADMINISTRATOR")
    public String type(Model model) {
        model.addAttribute("list", characterService.findAll());
        return CHARACTERLIST;
    }

    @GetMapping("/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String form(Model model, Character character) {
        return CHARACTERFORM;
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Authentication authentication, @Valid Character character, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return CHARACTERFORM;
            } else {
                String username = authentication.getName();
                Optional<User> user = userService.findByEmail(username);
                if (user.isPresent()) {
                    character.setStatus(true);
                    characterService.save(character, user.get());
                    redirectAttributes.addFlashAttribute("msg_success", "Carácter guardado exitosamente");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return CHARACTER;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        var character = characterService.findOne(id).orElse(null);
        if (character == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Carácter no encontrado");
            return CHARACTER;
        }
        model.addAttribute("character", character);
        return CHARACTERFORM;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            if (Boolean.TRUE.equals(characterService.delete(id, user.get()))) {
                redirectAttributes.addFlashAttribute("msg_success", "Carácter eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Carácter no eliminado");
            }
        }
        return CHARACTER;
    }

}
