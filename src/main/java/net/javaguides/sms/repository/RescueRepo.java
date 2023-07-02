package net.javaguides.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.sms.entity.Rescue;

public interface RescueRepo extends JpaRepository<Rescue, Long> {

}
