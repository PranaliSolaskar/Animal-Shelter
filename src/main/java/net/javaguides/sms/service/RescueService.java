package net.javaguides.sms.service;

import java.util.List;

import net.javaguides.sms.entity.Rescue;
import net.javaguides.sms.entity.Student;

public interface RescueService {
	List<Rescue> getAllRescue();
	
	Rescue saveRescue(Rescue rescue);
	
	Rescue getRescueById(Long id);
	
	Rescue updateRescue(Rescue recue);
	
	void deleteRescueById(Long id);
}
