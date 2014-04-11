package model.MARK_I.observableDesign;

import java.util.Observable;

/**
 *  1) Observable is a class & Observer is an interface
 *  2) Observable class maintains a list of observers
 *  3) When an Observable object is updated it invokes the
 *  update() method of each of its observers to notify that
 *  it has changed
 */
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
