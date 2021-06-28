 

 

 

 

import java.util.Collection;
import java.util.Iterator;

public class CloneIteratorReview{
    Iterator<Review> iter;

    public CloneIteratorReview(Collection<Review> c){
        this.iter = c.iterator();
    }

    public boolean hasNext(){
        return iter.hasNext();
    }

    public Review next(){
        return iter.next().clone();
    }
}
