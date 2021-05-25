package com.krisi.inventory;

import org.springframework.data.annotation.Id;

public class TasksProject {

	@Id
	private String id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("TasksProject { id = %s, name = '%s' '\n", id, name);
	}

	public String getId() {		
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
