package net.javaguides.sms.entities.size;

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
@RequestMapping("/size")
public class SizeController {

    private static final String SIZE = "redirect:/size/";
    private static final String SIZEFORM = "views/resources/size/sizeForm";
    

    private SizeService sizeService;

 
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(SizeController.class);

    @GetMapping("/")
    @Secured("ROLE_ADMINISTRATOR")
    public String type(Model model) {
        model.addAttribute("list", sizeService.findAll());
        return "views/resources/size/sizeList";
    }

    @GetMapping("/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String form(Model model, Size size) {
        return SIZEFORM;
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Authentication authentication, @Valid Size size, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return SIZEFORM;
            } else {
                String username = authentication.getName();
                Optional<User> user = userService.findByEmail(username);
                if (user.isPresent()) {
                    size.setStatus(true);
                    sizeService.save(size, user.get());
                    redirectAttributes.addFlashAttribute("msg_success", "Tamaño guardado exitosamente");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return SIZE;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        var size = sizeService.findOne(id).orElse(null);
        if (size == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Tamaño no encontrado");
            return SIZE;
        }
        model.addAttribute("size", size);
        return SIZEFORM;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            if (Boolean.TRUE.equals(sizeService.delete(id, user.get()))) {
                redirectAttributes.addFlashAttribute("msg_success", "Tamaño eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Tamaño no eliminado");
            }
        }
        return SIZE;
    }
}
