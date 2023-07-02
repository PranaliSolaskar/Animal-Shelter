package net.javaguides.sms.controller;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import net.javaguides.sms.entity.Customer;
import net.javaguides.sms.entity.Rescue;
import net.javaguides.sms.repository.CustomerRepository;
import net.javaguides.sms.service.CustomerService;
import net.javaguides.sms.service.StudentService;

@Controller
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/showcustomer")
	public String listcustomers(Model model) {
		model.addAttribute("customer", customerService.getAllCustomers());
		return "custregisteration";
	}
	
	@GetMapping("/addcustomer")
	public String createRescueForm(Model model) {
		
		// create student object to hold student form data
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		System.out.println("its ok ");
		return "customer";
		
	}
	
	@PostMapping("/customer")
	public String savecustom(@ModelAttribute("rescue") Customer customer) {
		customerService.saveCustomer(customer);
		return "redirect:/rescues";
	}
	
	@GetMapping("/customer/edit/{id}")
	public String editcustomerForm(@PathVariable Long id, Model model) {
		model.addAttribute("rescue", customerService.getCustomerById(id));
		return "edit_student";
	}
	

	@PostMapping("/customer/{id}")
	public String updatecustomer(@PathVariable Long id,
			@ModelAttribute("customer") Customer customer,
			Model model) {
		
		// get student from database by id
		Customer updatedCustomer = customerService.getCustomerById(id);
		updatedCustomer.setCustname(customer.getCustname());
        updatedCustomer.setCusername(customer.getCusername());
        updatedCustomer.setCphoneNumber(customer.getCphoneNumber());
        updatedCustomer.setCemail(customer.getCemail());
        updatedCustomer.setCpassword(customer.getCpassword());
        updatedCustomer.setCgender(customer.getCgender());
        updatedCustomer.setCaddress(customer.getCaddress());
		// save updated student object
		customerService.updateCustomer(updatedCustomer);
		return "redirect:/students";		
	}
	
	@GetMapping("/customer/{id}")
	public String deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomerid(id);
		return "redirect:/customer";
	}
	
}
