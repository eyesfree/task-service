package com.krisi.inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

// @RepositoryRestResource(collectionResourceRel = "tasks", path = "tasks")
public interface TaskRepository extends MongoRepository<Task, String> {

	List<Task> findByName(@Param("name") String name);
	Optional<Task> findById(@Param("id") Long id);
	List<Task> findByStatus(@Param("status") Status status);
}
