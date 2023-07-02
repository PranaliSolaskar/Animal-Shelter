package net.javaguides.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.javaguides.sms.entity.Product;

public interface ProductRepositry extends JpaRepository<Product, Long> {

}
