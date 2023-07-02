package net.javaguides.sms.entities.pet.repositories;

import net.javaguides.sms.entities.pet.entities.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage, Integer> {
}
