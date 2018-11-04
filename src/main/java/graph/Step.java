package graph;

import java.util.NoSuchElementException;
import java.util.Iterator;

public abstract
class Step implements Iterator<Traverser> {
    protected Step _prior_step;    // design: This is not Optional because in all intances of use, the caller is very aware if it will/can be null.
    private Traverser _next_traverser_to_return;

    protected Step( Step prior) {
        _prior_step = prior;
    }


    public boolean hasNext() {
        if (_next_traverser_to_return!=null) {
            return true;
        }
        _next_traverser_to_return = _next();
        return _next_traverser_to_return!=null;
    }

    public Traverser next() {
        Traverser retval;
        if (_next_traverser_to_return!=null) {
            retval = _next_traverser_to_return;
            _next_traverser_to_return = null;
        } else {
            // todo: design: consider requiring the caller to have called hasNext first.  (ie. Throwing an exception here.)
            retval = _next();
            if (retval==null) throw new NoSuchElementException();
        }
        return retval;  
    }

    /**
     * This method is to be implemented in the subclasses in support 
     * of the next() and hasNext() methods defined in this class. 
     * 
     * @author Jason (11/3/2018)
     * 
     * @return Traverser A null value indicates that there are no 
     *         more traversers.
     */
    //public Traverser _next();
    abstract protected Traverser _next();
    //public Traverser _next() { return null; }

    public Step out() {
        Step retval = new TraversalStep(this, Graph.Direction.OUT);
        return retval;
    }

    public Step out(String label) {
        Step retval = new TraversalStep(this, Graph.Direction.OUT, label);
        return retval;
    }

    public Step in() {
        Step retval = new TraversalStep(this, Graph.Direction.IN);
        return retval;
    }

    public Step in(String label) {
        Step retval = new TraversalStep(this, Graph.Direction.IN, label);
        return retval;
    }

    public Step both() {
        Step retval = new TraversalStep(this, Graph.Direction.BOTH);
        return retval;
    }

    public Step both(String label) {
        Step retval = new TraversalStep(this, Graph.Direction.BOTH, label);
        return retval;
    }

    public Step cycles(Boolean want_cycles) {
        Step retval = new CycleFilterStep(this, want_cycles);
        return retval;
    }



    public String toQueryString() {
        String prior_string = (_prior_step == null) ? "" : _prior_step.toQueryString() + ".";
        return  prior_string+_toStepString();

    }

    abstract protected String _toStepString();


}
