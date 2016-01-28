package neighbor;
import java.util.*;
public class FixedSizePriorityQueue<E> extends TreeSet<E> {

    public int elementsLeft;

    public FixedSizePriorityQueue(int maxSize, Comparator<E> comparator) {
        super(comparator);
        this.elementsLeft = maxSize;
    }


    /**
     * @return true if element was added, false otherwise
     * */
    @Override
    public boolean add(E e) {
        if (elementsLeft == 0 && size() == 0) {
            // max size was initiated to zero => just return false
            return false;
        } else if (elementsLeft > 0) {
            // queue isn't full => add element and decrement elementsLeft
            boolean added = super.add(e);
            if (added) {
                elementsLeft--;
            }
            return added;
        } else {
            // there is already 1 or more elements => compare to the least
            int compared = super.comparator().compare(e, this.last());
            if (compared <0) {
                // new element is less than the largest in queue => pull the largest and add new one to queue
                
                boolean added=super.add(e);
                if(added) this.pollLast();
                return true;
            } else {
                // new element is greater than the largest in queue => return false
                return false;
            }
        }
    }
}
