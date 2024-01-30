package com.webflux.tasks.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.webflux.tasks.model.Task;

import reactor.core.publisher.Mono;

@Service
public class TaskService {
	
	public static List<Task> taskList = new ArrayList<>();
	
	public Mono<Task> insert(Task task){
		return Mono.just(task)
				.map(Task::insert)
				.flatMap(this::save);
	}
	
	public Mono<List<Task>> list(){
		return Mono.just(taskList);
	}
	
	private Mono<Task> save(Task task){
		return Mono.just(task)
				.map(Task::newTask);
	}
}
