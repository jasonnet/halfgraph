package graph;

//import static org.jasonnet.logln.Logln.logln;
import java.util.Iterator;
import java.util.HashSet;

/**
 * A step that eliminates paths with cycles or retains only 
 * paths with cycles. 
 *  
 * <p>Limitation: this code is not yet unit tested.</p> 
 *  
 * <p>In this implementation, the definition of a cycle is the 
 * presence of a vertex more than once in a path.</p> 
 * 
 * @author Jason (11/4/2018)
 */
public
class CycleFilterStep extends Step {
    private boolean _want_cycles;

    protected CycleFilterStep( Step prior, boolean want_cycles ) {
        super(prior);
        _want_cycles = want_cycles;
    }



    @Override
    /**
     * An implentation of next() used by Step.next() and 
     * Step.hasNext(). 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Traverser 
     */
    protected Traverser _next() {
        HashSet<Vertex> found = new HashSet<>();
        Traverser _prev_traverser;
        while (_prior_step.hasNext()) {
            _prev_traverser= _prior_step.next();
            found.clear();

            Traverser trav2 = _prev_traverser;
            boolean has_cycle = false;
            while (trav2!=null) {
                Vertex v2 = trav2.vertex();
                if (found.contains(v2)) {
                    has_cycle = true;
                    break;
                }
                found.add(v2);
                trav2 = trav2.prior_traverser();
            }
            if (_want_cycles == has_cycle) {
                return _prev_traverser; // design: for now we'll just return it.  In a more sophisticated implementation, there will probably be requirements that would dictate that a new copy be returned
            }
        }
        return null;  // nothing left
    }

    @Override
    /**
     * Returns a string representing what this Step is doing. 
     * Typical values include  cycle(true) or cycle(false).
     * 
     * @author Jason (11/4/2018)
     * 
     * @return String   never null
     */
    protected String _toStepString() {
        return "cycles("+_want_cycles+")";
    }


}
