package model.MARK_I.observableDesign;

import java.util.Observable;

import java.util.Observer;

/**
 * Observable is a class & Observer is an interface
 */
public class Student implements Observer {

    @Override
    public void update(Observable arg0, Object arg1) {
	System.out.println("message board changed: " + arg1);
    }
}
