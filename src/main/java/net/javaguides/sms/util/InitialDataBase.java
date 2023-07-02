package net.javaguides.sms.util;

import net.javaguides.sms.entities.character.CharacterService;
import net.javaguides.sms.entities.color.ColorService;
import net.javaguides.sms.entities.role.RoleService;
import net.javaguides.sms.entities.size.SizeService;
import net.javaguides.sms.entities.tag.TagService;
import net.javaguides.sms.entities.type.TypeService;
import net.javaguides.sms.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialDataBase implements CommandLineRunner {
   private ColorService colorService;

   private CharacterService characterService;

   private RoleService roleService;

   private TagService tagService;

   private UserService userService;

   private SizeService sizeService;

   private TypeService typeService;

    @Override
    public void run(String... args) throws Exception {
        colorService.fillInitialData();
        characterService.fillInitialData();
        typeService.fillInitialData();
        roleService.fillInitialData();
        sizeService.fillInitialData();
        tagService.fillInitialData();
        userService.fillInitialData();
    }
}
