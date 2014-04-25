package model;

import junit.framework.TestCase;

public class SampleClassTest extends TestCase {
    private SampleClass sampleClass;

    public void setUp() {
	this.sampleClass = new SampleClass();
    }

    public void test_packagePrivateMethod() {
	// this method can't be called right now why?
	//this.sampleClass.packagePrivateMethod();
    }
}
