import java.util.List;
import java.util.Stack;

public interface Ilayout {
    /**
     @return the children of the receiver.
     */
    List<Ilayout> children() throws CloneNotSupportedException;
    /**
    * @return true if the receiver equals the argument l;
    return false otherwise.
    */
    boolean isGoal(Ilayout l);
    /**
     @return the cost for moving from the input config to the receiver.
     */
    double getK();

    Stack<Pair<Character, Integer>>[] getLayout();

}
