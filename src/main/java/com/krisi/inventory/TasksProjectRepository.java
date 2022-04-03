package com.krisi.inventory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TasksProjectRepository extends MongoRepository<TasksProject, String>{

	@Query(value = "{ 'name' : ?0 }")
	TasksProject findByName(String name);
}
