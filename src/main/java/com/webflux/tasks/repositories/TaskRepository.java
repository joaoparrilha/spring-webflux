package com.webflux.tasks.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.webflux.tasks.model.Task;

@Repository
public interface TaskRepository extends MongoRepository<Task, String>{

}
