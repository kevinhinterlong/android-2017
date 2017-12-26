package org.hackillinois.app2017.Events;

/**
 * Created by kevin on 11/21/2017.
 */

public enum EventDay {
	FRIDAY(0),
	SATURDAY(1),
	SUNDAY(2);

	private final int id;

	EventDay(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static EventDay from(int i) {
		for(EventDay eventDay : EventDay.values()) {
			if(eventDay.id == i) {
				return eventDay;
			}
		}
		return null;
	}
}
