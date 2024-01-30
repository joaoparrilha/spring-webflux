package com.webflux.tasks.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.tasks.controller.converter.TaskDTOConverter;
import com.webflux.tasks.controller.dto.TaskDTO;
import com.webflux.tasks.model.Task;
import com.webflux.tasks.service.TaskService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task")
public class TaskController {

	private final TaskService service;
	
	private final TaskDTOConverter converter;
	
	public TaskController(TaskService service, TaskDTOConverter converter) {
		this.service = service;
		this.converter = converter;
	}
	
	@GetMapping
	public Mono<List<TaskDTO>> getTasks(){
		return service.list()
				.map(converter::convertList);
	}
	
	@PostMapping
	public Mono<TaskDTO> createTask(@RequestBody Task task){
		return service.insert(task)
				.map(converter::convert);
	}
}
