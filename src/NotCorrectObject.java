 

 

 

 

 

 


 

/*
This class is used to throw an exception when the program receives an object that has a wrong type
 */

public class NotCorrectObject extends Exception {
    public NotCorrectObject() {
        super();
    }

    public NotCorrectObject(String msg) {
        super(msg);
    }
}
