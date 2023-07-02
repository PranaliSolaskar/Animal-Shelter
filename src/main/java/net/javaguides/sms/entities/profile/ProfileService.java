package net.javaguides.sms.entities.profile;

import net.javaguides.sms.entities.address.Address;
import net.javaguides.sms.entities.address.AddressService;
import net.javaguides.sms.entities.log.LogService;
import net.javaguides.sms.entities.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    
    private ProfileRepository profileRepository;

    
    private PasswordEncoder passwordEncoder;

    
    private LogService logService;

    @Autowired
    private AddressService addressService;

    private Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Profile> findOne(Integer id) {
        return profileRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Profile> findByUser(User user) {
        return profileRepository.findByUser(user);
    }

    @Transactional
    public Optional<Profile> save(Profile entity, User user) {
        var action = "Actualizar";
        if (entity.getId() == null) {
            entity.setImage("https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/662/3e95009c-7c93-4580-a764-5a32f1648a0d.jpg");
            action = "Crear";
        }
        logService.saveProfile(action, entity, user);
        return Optional.of(profileRepository.save(entity));
    }

    @Transactional
    public Optional<Profile> addProfile(Profile profile, User user) {
        Optional<Address> address = addressService.addAddress();
        if (address.isPresent()) {
            entityManager.createNativeQuery("INSERT INTO tbl_profiles (name,last_name,second_name,phone,address_id,user_id)VALUES (?,?,?,?,?,?);")
                    .setParameter(1, profile.getName())
                    .setParameter(2, profile.getLastName())
                    .setParameter(3, profile.getSecondName())
                    .setParameter(4, profile.getPhone())
                    .setParameter(5, address.get().getId())
                    .setParameter(6, profile.getUser().getId())
                    .executeUpdate();
            Optional<Profile> savedProfile = findByUser(user);
            savedProfile.ifPresent(p ->
                    logService.saveProfile("Crear", p, user));
            return savedProfile;
        }
        return Optional.empty();
    }

    @Transactional
    public Profile findAndSetPerfil(Profile entity) {
        try {
            Optional<Profile> updateEntity = findOne(entity.getId());
            if (updateEntity.isPresent()) {
                updateEntity.get().setName(entity.getName());
                updateEntity.get().setLastName(entity.getLastName());
                updateEntity.get().setSecondName(entity.getSecondName());
                updateEntity.get().setPhone(entity.getPhone());
                updateEntity.get().setImage(entity.getImage());
                updateEntity.get().getAddress().setStreet(entity.getAddress().getStreet());
                updateEntity.get().getAddress().setInternalNumber(entity.getAddress().getInternalNumber());
                updateEntity.get().getAddress().setExternalNumber(entity.getAddress().getExternalNumber());
                updateEntity.get().getAddress().setZipCode(entity.getAddress().getZipCode());
                updateEntity.get().getAddress().setReferences(entity.getAddress().getReferences());
                return updateEntity.get();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }



    @Transactional
    public Boolean delete(Integer id, User user) {
        Optional<Profile> entity = findOne(id);
        if (entity.isPresent()) {
            entity.get().getUser().setEnabled(false);
            logService.saveProfile("Eliminar", entity.get(), user);
            profileRepository.save(entity.get());
            return true;
        }
        return false;
    }

    @Transactional
    public String  recoveryPassword(Profile profile) {
        if(profile.getId() == null){
            return  passwordEncoder.encode(profile.getUser().getPassword());
        }else{
            Optional<Profile> tmpProfile = findOne(profile.getId());
            if(tmpProfile.isPresent()){
                return tmpProfile.get().getUser().getPassword();
            }
        }
        return "";
    }
}
