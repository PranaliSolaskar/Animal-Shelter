package net.javaguides.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.sms.entity.Customer;
import net.javaguides.sms.entity.Product;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
