package com.webflux.tasks.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.tasks.controller.converter.TaskDTOConverter;
import com.webflux.tasks.controller.dto.TaskDTO;
import com.webflux.tasks.model.TaskState;
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
	public Page<TaskDTO> getTasks(@RequestParam(required=false) String id,
										@RequestParam(required=false) String title,
										@RequestParam(required=false) String description,
										@RequestParam(required=false, defaultValue="0") int priority,
										@RequestParam(required=false)TaskState taskState,
										@RequestParam(value="pageNumber", defaultValue="0") Integer pageNumber,
										@RequestParam(value="pageSize", defaultValue="10") Integer pageSize){
		return service.findPaginated(converter.convert(id, title, description, priority, taskState), pageNumber, pageSize)
				.map(converter::convert);
	}
	
	@PostMapping
	public Mono<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
		return service.insert(converter.convert(taskDTO))
				.map(converter::convert);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable String id){
		return Mono.just(id)
				.flatMap(service::deleteById);
	}
}
