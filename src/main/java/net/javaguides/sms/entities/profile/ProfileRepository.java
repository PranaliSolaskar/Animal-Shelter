package net.javaguides.sms.entities.profile;

import net.javaguides.sms.entities.address.Address;
import net.javaguides.sms.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUser(User user);
    Optional<Profile> findByAddress(Address address);
    List<Profile> findAllByUser_Enabled(Boolean enabled); //NOSONAR
}
