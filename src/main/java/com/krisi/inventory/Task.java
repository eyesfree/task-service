package com.krisi.inventory;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Task {
	@Id
	private String id;

	private boolean isSubTask;
	private String projectId;
	private String name;
	private String description;
	private Status status;
	private String assigneeName;
	private int priority;
	private Date dueDate;
	private Date createdDate;
	private List<String> subTasks;

	@Override
	public String toString() {
		return String.format("Task { id = %s, name = '%s', description = '%s'\n", id, name, description);
	}

	public boolean isSubTask() {
		return isSubTask;
	}

	public void setSubTask(boolean isSubTask) {
		this.isSubTask = isSubTask;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<String> getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(List<String> subTasks) {
		this.subTasks = subTasks;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(final int priority) {
		this.priority = priority;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public String getAssigneeName() {
		return this.assigneeName;
	}

	public void setAssigneeName(final String assignee) {
		this.assigneeName = assignee;
	}
	
	public String toJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}
}
