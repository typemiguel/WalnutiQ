package model.MARK_I.observableDesign;

public class MessageBoardTest extends junit.framework.TestCase {
    private MessageBoard messageBoard;
    private Student student1;
    private Student student2;

    public void setUp() {
	this.messageBoard = new MessageBoard("hello world");
	this.student1 = new Student();
	this.student2 = new Student();
    }

    public void testSetMessage() {
	this.messageBoard.setMessage("before adding observers");
	this.messageBoard.addObserver(this.student1);
	this.messageBoard.addObserver(this.student2);
	this.messageBoard.setMessage("after adding observers");
    }
}
