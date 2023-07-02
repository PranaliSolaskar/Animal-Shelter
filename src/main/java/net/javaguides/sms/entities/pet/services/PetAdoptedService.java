package net.javaguides.sms.entities.pet.services;

import net.javaguides.sms.config.email.EmailService;
import net.javaguides.sms.entities.log.LogService;
import net.javaguides.sms.entities.pet.entities.Pet;
import net.javaguides.sms.entities.pet.entities.PetAdopted;
import net.javaguides.sms.entities.pet.repositories.PetAdoptedRepository;

import net.javaguides.sms.entities.user.User;
import net.javaguides.sms.entities.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PetAdoptedService {

    
    private PetAdoptedRepository petAdoptedRepository;

    
    private LogService logService;

    
    private UserService userService;

    
    private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(PetAdoptedService.class);

    @Transactional(readOnly = true)
    public List<PetAdopted> findAll() {
        return petAdoptedRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Integer countByUserRequestAccepted(Integer id){
        return petAdoptedRepository.countByUserIdAndIsAcceptedIsTrueAndAndIsCanceledIsFalse(id);
    }

    @Transactional(readOnly = true)
    public Integer countByUserRequestCanceled(Integer id){
        return petAdoptedRepository.countByUserIdAndIsAcceptedIsFalseAndAndIsCanceledIsTrue(id);
    }

    @Transactional(readOnly = true)
    public Integer countByUserRequestPending(Integer id){
        return petAdoptedRepository.countByUserIdAndIsAcceptedIsFalseAndAndIsCanceledIsFalse(id);
    }

    @Transactional(readOnly = true)
    public Optional<PetAdopted> findOne(Integer id) {
        return petAdoptedRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<PetAdopted> findUsername(Integer id) {
        return petAdoptedRepository.findByUserId(id);
    }

    @Transactional
    public Optional<PetAdopted> save(PetAdopted entity, User user) {
        var action = "Actualizar";
        if (entity.getId() == null) {
            action = "Crear";
        }
        logService.savePetAdoptedLog(action,entity,user);
        return Optional.of(petAdoptedRepository.save(entity));
    }

    @Transactional
    public Boolean accept(Integer id, Integer idPet, Integer idUser) {
        Optional<PetAdopted> entity = petAdoptedRepository.findById(id);
        Optional<User> user = userService.findOne(idUser);
        if (entity.isPresent() && user.isPresent()) {
            var petAdopted = entity.get();
            petAdopted.setIsAccepted(true);
            petAdopted.setIsCanceled(false);
            petAdopted.getPet().setIsAdopted(true);
            save(petAdopted, user.get());
            petAdoptedRepository.setAllPetCanceled(idPet, idUser);
            sendAdoptionConfirmation(petAdopted);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean cancel(Integer id) {
        Optional<PetAdopted> entity = petAdoptedRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsCanceled(true);
            entity.get().setIsAccepted(false);
            save(entity.get(),entity.get().getUser());
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = petAdoptedRepository.existsById(id);
        if (entity) {
            petAdoptedRepository.deleteById(id);
        }
        return entity;
    }

    @Transactional()
    public Boolean checkIsPresentInAdoptions(Pet currentPet, User currentUser){
        var flag = false;
        List<PetAdopted> petUserAdopted = findUsername(currentUser.getId());
        for (PetAdopted p:petUserAdopted) {
            if(currentPet.getId().equals(p.getPet().getId())){
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Transactional
    public void sendAdoptionConfirmation(PetAdopted petAdopted) {
        try {
            if(Boolean.TRUE.equals(petAdopted.getIsAccepted()) && Boolean.FALSE.equals(petAdopted.getIsCanceled())){
                emailService.sendConfirmationAdoptTemplate(petAdopted);
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }
}
