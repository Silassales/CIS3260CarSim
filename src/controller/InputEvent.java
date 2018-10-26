package controller;

public class InputEvent {
	private InputEventType eventType;
	
	public InputEvent(InputEventType type) {
		this.eventType = type;
	}
	
	public InputEventType getEventType() {
		return eventType;
	}

	@Override
	public String toString() {
		return "InputEvent [eventType=" + eventType + "]";
	}
}
