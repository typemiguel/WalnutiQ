package test.java.model;

import main.java.model.SampleClass;

public class SampleClassTest extends junit.framework.TestCase {
    private SampleClass sampleClass;

    public void setUp() {
	this.sampleClass = new SampleClass();
    }

    public void test_packagePrivateMethod() {
	// this method can't be called right now why?
	//this.sampleClass.packagePrivateMethod();
    }
}
