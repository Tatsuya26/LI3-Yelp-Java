
 

 

 


/*
class is used to throw an exception when the program wants to add a business/review/user to the catalog
but the catalog already exists
 */

public class StructureAlreadyExists extends Exception{
    public StructureAlreadyExists() {
        super();
    }

    public StructureAlreadyExists(String msg) {
        super(msg);
    }

}
