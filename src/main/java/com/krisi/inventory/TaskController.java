package com.krisi.inventory;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("tasks/v2")
public class TaskController {

    private static final Logger log = Logger.getLogger(TaskController.class.getName());

    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Task> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{name}")
    public List<Task> findByName(@PathVariable("name") String name) {
        List<Task> tasksWithName = repository.findByName(name);
        log.info("There are " + tasksWithName.size() + " tasks with name " + name);
        return tasksWithName;
    }

    @PostMapping()
    public Task save(@RequestBody final Task task) {
        log.info("Saving task with id" + task.getId());
        return repository.save(task);
    }


    @PutMapping("/{id}")
    public Task update(@PathVariable("id") String id, @RequestBody final Task task) {
        log.info("Updating task with id" + task.getId());
        if(!task.getId().equals(id)) {
            log.warning("Id from request and task.id do not match " + id);
            return null;
        }

        return repository.save(task);
    }

    @DeleteMapping
    public void deleteAll() {
        log.info("Deleting all");
        repository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        log.info("Deleting " + id);
        repository.deleteById(id);
    }
}