package model.MARK_I.observableDesign;

import java.util.Observable;

public class MessageBoard extends Observable {
    private String message;

    public MessageBoard(String message) {
	this.message = message;
    }

    public String getMessage() {
	return this.message;
    }

    public void setMessage(String message) {
	this.message = message;
	this.setChanged();
	this.notifyObservers(message);
    }
}
