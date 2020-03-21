package com.krisi.inventory;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class Task {
	@Id
	private String id;
	
	private String name;
	private String description; 
	private Status status;
	private String assigneeName;
	private int priotiry;
	private LocalDateTime dueDate; 
	private LocalDateTime createdDate;
	
	@Override
	public String toString() {
		return String.format("Task { id = %s, name = '%s', description = '%s'\n", id, name, description); 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAssigneeName() {
		return this.assigneeName;
	}

	public void setAssigneeName(String assignee) {
		this.assigneeName = assignee;
	}
}
