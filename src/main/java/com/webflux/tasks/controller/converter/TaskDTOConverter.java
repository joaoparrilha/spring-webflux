package com.webflux.tasks.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.webflux.tasks.controller.dto.TaskDTO;
import com.webflux.tasks.model.Task;

@Component
public class TaskDTOConverter {

	public TaskDTO convert(Task task) {
		return Optional.ofNullable(task)
				.map(source -> {
					TaskDTO dto = new TaskDTO();
					dto.setTitle(source.getTitle());
					dto.setDescription(source.getDescription());
					dto.setPriority(source.getPriority());
					dto.setState(source.getState());
					return dto;
				})
				.orElse(null);
	}
	
	public Task convert(TaskDTO taskDTO) {
		return Optional.ofNullable(taskDTO)
				.map(source -> Task.builder()
						.withTitle(source.getTitle())
						.withDescription(source.getDescription())
						.withPriority(source.getPriority())
						.withState(source.getState())
						.build())
				.orElse(null);
	}
	
	public List<TaskDTO> convertList(List<Task> list){
		return Optional.ofNullable(list)
				.map(array -> array.stream().map(this::convert).collect(Collectors.toList()))
				.orElse(new ArrayList<>());
	}
}
