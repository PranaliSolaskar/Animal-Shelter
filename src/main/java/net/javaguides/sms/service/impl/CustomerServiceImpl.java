package net.javaguides.sms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.Customer;
import net.javaguides.sms.entity.Rescue;
import net.javaguides.sms.repository.CustomerRepository;
import net.javaguides.sms.repository.RescueRepo;
import net.javaguides.sms.service.CustomerService;
import net.javaguides.sms.service.RescueService;

@Service
public class CustomerServiceImpl implements CustomerService{

	private CustomerRepository customerRepository;
	
	public CustomerServiceImpl(CustomerRepository  customRepository) {
			super();
			this.customerRepository = customRepository;
		}
		
		@Override
		public List<Customer> getAllCustomers() {
	        return customerRepository.findAll();
	    }

		@Override
	    public Customer getCustomerById(Long id) {
	        return customerRepository.findById(id).get();
	    }

		@Override
	    public Customer saveCustomer(Customer customer) {
	        return customerRepository.save(customer);
	    }

		@Override
	    public void deleteCustomerid(Long id) {
	        customerRepository.deleteById(id);
	    }
	    
	    @Override
		public Customer updateCustomer(Customer customer) {
			return customerRepository.save(customer);
		}

}
