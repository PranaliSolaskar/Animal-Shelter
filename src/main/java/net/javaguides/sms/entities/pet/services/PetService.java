package net.javaguides.sms.entities.pet.services;

import net.javaguides.sms.entities.character.Character;
import net.javaguides.sms.entities.character.CharacterService;
import net.javaguides.sms.entities.color.Color;
import net.javaguides.sms.entities.color.ColorService;
import net.javaguides.sms.entities.log.LogService;
import net.javaguides.sms.entities.pet.entities.Pet;
import net.javaguides.sms.entities.pet.repositories.PetRepository;
import net.javaguides.sms.entities.size.Size;
import net.javaguides.sms.entities.size.SizeService;
import net.javaguides.sms.entities.type.Type;
import net.javaguides.sms.entities.type.TypeService;
import net.javaguides.sms.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import java.util.ArrayList;

@Service
public class PetService {

    
    private PetRepository petRepository;
    
    private TypeService typeService;
    
    private SizeService sizeService;
    
    private CharacterService characterService;
    
    private ColorService colorService;
    
    private LogService logService;

    
    @Transactional(readOnly = true)
    public List<Pet> findAll() {
        return petRepository.findAllByIsActiveAndIsDroppedFalseOrderByCreatedAtDesc(true);
    }

    @Transactional(readOnly = true)
    public List<Pet> findAllisActiveFalse() {
        return petRepository.findAllByIsActiveAndIsDroppedFalseOrderByCreatedAtDesc(false);
    }

    @Transactional(readOnly = true)
    public Long countTotal() {
        return petRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Pet> findLastThreePets() {
        return petRepository.findLastThreePets();
    }

    @Transactional(readOnly = true)
    public List<Pet> findPetsForAdopted() {
        return petRepository.findPetsForAdopted();
    }

    @Transactional
    public Optional<Pet> findOne(Integer id) {
        return petRepository.findById(id);
    }

    @Transactional
    public Optional<Pet> save(Pet entity, User user) {
        var action = "Actualizar";
        if (entity.getId() == null) {
            action = "Crear";
        }
        logService.savePetLog(action, entity, user);
        return Optional.of(petRepository.save(entity));
    }

    @Transactional
    public Optional<Pet> update(Pet entity) {
        Optional<Pet> updatedEntity;
        updatedEntity = petRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            petRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean accept(Integer id) {
        Optional<Pet> entity = petRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsActive(true);
            petRepository.save(entity.get());
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean delete(Integer id , User user) {
        Optional<Pet> entity = petRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsActive(false);
            entity.get().setIsDropped(true);
            logService.savePetLog("Eliminar", entity.get(), user);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Integer coutnByIsActive(Boolean flag) {
        return petRepository.countByIsActiveAndIsDroppedFalse(flag);
    }

    @Transactional(readOnly = true)
    public Integer coutnByIsAdopted(Boolean flag) {
        return petRepository.countByIsAdoptedAndIsDroppedFalse(flag);
    }

    @Transactional(readOnly = true)
    public List<Pet> findTopFive() {
        return petRepository.findTop5ByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Pet> findByNameOrBreed(String query) {
        return petRepository.findAllByNameContainingOrBreedContainingAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(query, query);
    }

    @Transactional(readOnly = true)
    public List<Pet> findByAge(String ages) {
        String[] agesName = ages.split(",");
        List<String> agesList = List.of(agesName);
        Collection<String> collection = agesList;
        return petRepository.findByAgeInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(collection);
    }

    @Transactional(readOnly = true)
    public List<Pet> findBySize(String sizes) {
        String[] sizesNames = sizes.split(",");
        List<Size> filterSizes = new ArrayList<>();
        for (String name : sizesNames) {
            Optional<Size> size = sizeService.findByName(name);
            size.ifPresent(filterSizes::add);
        }
        Collection<Size> collection = new ArrayList<>(filterSizes);
        return petRepository.findBySizeInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(collection);
    }

    @Transactional(readOnly = true)
    public List<Pet> findByCharacters(String characters) {
        String[] charactersNames = characters.split(",");
        List<Character> filterCharacters = new ArrayList<>();
        for (String name : charactersNames) {
            Optional<Character> character = characterService.findByName(name);
            character.ifPresent(filterCharacters::add);
        }
        Collection<Character> collection = new ArrayList<>(filterCharacters);
        return petRepository.findByCharacterInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(collection);
    }

    @Transactional(readOnly = true)
    public List<Pet> findByColor(String colors) {
        String[] colorsNames = colors.split(",");
        List<Color> filterColors = new ArrayList<>();
        for (String name : colorsNames) {
            Optional<Color> color = colorService.findByName(name);
            color.ifPresent(filterColors::add);
        }
        Collection<Color> collection = new ArrayList<>(filterColors);
        return petRepository.findByColorInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(collection);
    }

    @Transactional(readOnly = true)
    public List<Pet> findByType(String types) {
        String[] typesName = types.split(",");
        List<Type> filterTypes = new ArrayList<>();
        for (String s : typesName) {
            Optional<Type> type = typeService.findByName(s);
            type.ifPresent(filterTypes::add);
        }
        Collection<Type> collection = new ArrayList<>(filterTypes);
        return petRepository.findByTypeInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(collection);
    }

    @Transactional
    public Boolean checkIsPresentInFavorites(Pet currentPet, List<Pet> userPetsFavorites) {
        var flag = false;
        for (Pet p : userPetsFavorites) {
            if (p.getId().equals(currentPet.getId())) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
