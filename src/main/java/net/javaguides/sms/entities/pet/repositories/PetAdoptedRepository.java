package net.javaguides.sms.entities.pet.repositories;

import net.javaguides.sms.entities.pet.entities.PetAdopted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetAdoptedRepository extends JpaRepository<PetAdopted, Integer> {
    List<PetAdopted> findAllByIsCanceled(Boolean isCanceled);

    List<PetAdopted> findByUserId(Integer username);

    Optional<PetAdopted> findByPet_Id(Integer pet); //NOSONAR

    Integer countByUserIdAndIsAcceptedIsTrueAndAndIsCanceledIsFalse(Integer username);

    Integer countByUserIdAndIsAcceptedIsFalseAndAndIsCanceledIsFalse(Integer username);

    Integer countByUserIdAndIsAcceptedIsFalseAndAndIsCanceledIsTrue(Integer username);

    @Modifying
    @Query(value = "UPDATE tbl_pets_adopted a SET a.is_canceled = 1, a.is_accepted = 0 WHERE a.pet_id = ?1 AND a.user_id <> ?2 ", nativeQuery = true)
    void setAllPetCanceled(Integer idPet, Integer idUser);
}
