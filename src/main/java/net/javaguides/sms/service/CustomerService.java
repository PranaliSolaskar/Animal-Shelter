package net.javaguides.sms.service;

import java.util.List;
import java.util.Optional;

import net.javaguides.sms.entity.Customer;
import net.javaguides.sms.entity.Rescue;

public interface CustomerService {

List<Customer> getAllCustomers();
	
	Customer saveCustomer(Customer customer);
	
	 Customer getCustomerById(Long id);
	
	Customer updateCustomer(Customer customer);
	
	
	void deleteCustomerid(Long id);
}
