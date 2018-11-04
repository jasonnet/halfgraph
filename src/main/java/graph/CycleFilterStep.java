package graph;

//import static org.jasonnet.logln.Logln.logln;
import java.util.Iterator;
import java.util.HashSet;

public
class CycleFilterStep extends Step {
    private boolean _want_cycles;

    protected CycleFilterStep( Step prior, boolean want_cycles ) {
        super(prior);
        _want_cycles = want_cycles;
    }



    @Override
    protected Traverser _next() {
        HashSet<Vertex> found = new HashSet<>();
        Traverser _prev_traverser;
        while (_prior_step.hasNext()) {
            _prev_traverser= _prior_step.next();
            found.clear();

            Traverser trav2 = _prev_traverser;
            boolean has_cycle = false;
            while (trav2!=null) {
                Vertex v2 = trav2._top_vertex();
                if (found.contains(v2)) {
                    has_cycle = true;
                    break;
                }
                found.add(v2);
                trav2 = trav2._prior_trav;
            }
            if (_want_cycles == has_cycle) {
                return _prev_traverser; // design: for now we'll just return it.  In a more sophisticated implementation, there will probably be requirements that would dictate that a new copy be returned
            }
        }
        return null;  // nothing left
    }

    protected String _toStepString() {
        return "cycles("+_want_cycles+")";
    }


}
