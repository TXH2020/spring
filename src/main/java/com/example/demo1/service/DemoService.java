package com.example.demo1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo1.model.DemoModel;
import com.example.demo1.repository.DemoRepository;

@Service
public class DemoService {
	DemoRepository demoRepository;
	public DemoService(DemoRepository d) {
		demoRepository=d;
	}
	public DemoModel create(DemoModel d) {
        return demoRepository.save(d);
    }
	public List<DemoModel> getAll() {
        return demoRepository.findAll();
    }
}
