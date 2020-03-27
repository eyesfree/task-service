package com.krisi.inventory;

public enum Status {
	NEW("new"),
	IN_PROGRESS("in_progress"),
	DONE("done"),
	ON_HOLD("on_hold");

	private String statusName;
 
    Status(final String statusName) {
        this.statusName = statusName;
    }
 
    public String getStatusName() {
        return this.statusName;
    }
}
