package net.javaguides.sms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.Rescue;
import net.javaguides.sms.repository.RescueRepo;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.RescueService;
import net.javaguides.sms.service.StudentService;

@Service
public class RescueServiceImpl implements RescueService{
	private RescueRepo rescueRepository;
	
public RescueServiceImpl(RescueRepo rescueRepository) {
		super();
		this.rescueRepository = rescueRepository;
	}

	@Override
	public List<Rescue> getAllRescue() {
		return rescueRepository.findAll();
	}

	@Override
	public Rescue saveRescue(Rescue rescue) {
		return rescueRepository.save(rescue);
	}

	@Override
	public Rescue getRescueById(Long id) {
		return rescueRepository.findById(id).get();
	}

	@Override
	public Rescue updateRescue(Rescue rescue) {
		return rescueRepository.save(rescue);
	}

	@Override
	public void deleteRescueById(Long id) {
		rescueRepository.deleteById(id);	
	}

}
