package com.krisi.inventory;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("tasks/v2")
public class TaskController {

    private static final Logger log = Logger.getLogger(TaskController.class.getName());

    private TaskRepository repository;

    TaskController(TaskRepository repository) {
        this.repository = repository;
      }
    
    @GetMapping
    public List<Task> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Task save(@RequestBody Task task) {
        log.info("Saving task " + task);
        return repository.save(task);
    }
}