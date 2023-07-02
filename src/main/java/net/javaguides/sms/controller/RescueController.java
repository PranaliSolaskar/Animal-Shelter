package net.javaguides.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.sms.entity.Rescue;
import net.javaguides.sms.service.RescueService;

@Controller
public class RescueController {

	private RescueService rescueService;

	public RescueController(RescueService rescueService) {
		super();
		this.rescueService = rescueService;
	}
	
	@GetMapping("/showrescue")
	public String listStudents(Model model) {
		model.addAttribute("rescue", rescueService.getAllRescue());
		return "rescue";
	}
	
	@GetMapping("/addrescue")
	public String createRescueForm(Model model) {
		
		// create student object to hold student form data
		Rescue rescue = new Rescue();
		model.addAttribute("rescue", rescue);
		System.out.println("its ok ");
		return "rescue";
		
	}
	
	@PostMapping("/rescue")
	public String saveStudent(@ModelAttribute("rescue") Rescue rescue) {
		rescueService.saveRescue(rescue);
		return "redirect:/rescues";
	}
	
	@GetMapping("/rescue/edit/{id}")
	public String editRescueForm(@PathVariable Long id, Model model) {
		model.addAttribute("rescue", rescueService.getRescueById(id));
		return "edit_student";
	}
	

	@PostMapping("/rescue/{id}")
	public String updateRescue(@PathVariable Long id,
			@ModelAttribute("rescue") Rescue rescue,
			Model model) {
		
		// get student from database by id
		Rescue existingRescue = rescueService.getRescueById(id);
		existingRescue.setId(id);
		existingRescue.setfName(rescue.getfName());
		existingRescue.setPhoneno(rescue.getPhoneno());
		existingRescue.setAtype(rescue.getAtype());
		existingRescue.setNo_ofa(rescue.getNo_ofa());
		existingRescue.setMinfo(rescue.getMinfo());
		
		// save updated student object
		rescueService.updateRescue(existingRescue);
		return "redirect:/students";		
	}
	
	@GetMapping("/rescues/{id}")
	public String deleteRescue(@PathVariable Long id) {
		rescueService.deleteRescueById(id);
		return "redirect:/students";
	}
}