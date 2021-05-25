package com.krisi.inventory;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface TasksProjectRepository extends MongoRepository<TasksProject, String>{

	List<TasksProject> findByName(@Param("name") String name);
}
