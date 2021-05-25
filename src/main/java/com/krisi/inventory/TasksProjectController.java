package com.krisi.inventory;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("projects/v1")
public class TasksProjectController {
    private static final Logger log = Logger.getLogger(TasksProjectController.class.getName());

	private TasksProjectRepository repository;

	TasksProjectController(final TasksProjectRepository repository) {
        this.repository = repository;
    }
	
    @GetMapping
    public List<TasksProject> getAll() {
        return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public TasksProject findById(@PathVariable("id") String id) {
        Optional<TasksProject> projectWithId = repository.findById(id);
        if(projectWithId.isPresent()) {
        	TasksProject project = projectWithId.get();
        	log.info("Project found: " + project.getName());
            return project;
        } else {
        	log.info("Project not found for id: " + id);
        	return null;
        }       
    }
   
    @PostMapping()
    public TasksProject save(@RequestBody final TasksProject project) {
        log.info("Saving project with id: " + project.getId());
        return repository.save(project);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        log.info("Deleting project with id: " + id);
        repository.deleteById(id);
    }
}
