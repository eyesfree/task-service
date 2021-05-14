package com.krisi.inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface TasksProjectRepository extends MongoRepository<TasksProject, String>{

	List<TasksProject> findByName(@Param("name") String name);
	Optional<TasksProject> findById(@Param("id") Long id);
}
