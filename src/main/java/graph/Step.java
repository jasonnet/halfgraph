package graph;

import java.util.NoSuchElementException;
import java.util.Iterator;


/**
 * a step in a traversal. 
 *  
 * <p>Steps chain, via the _prior_step member variable, to form a
 * query specification. Steps also are Iterators of Traversers. 
 * As such one can use next() and hasNext() methods to get all 
 * the matching paths. </p>
 *  
 * <p>Limitation: There currently is no Query class in HalfGraph
 * so the chain of Step objects can also be thought of as 
 * representing a query specification.</p> 
 * 
 * @author Jason (11/4/2018)
 */
abstract
class Step implements Iterator<Traverser> {
    protected Step _prior_step;    // design: This is not Optional because in all intances of use, the caller is very aware of if it will/can be null.

    /** 
     * This member acts as a cache of the next Traverser for this 
     * Step to return. 
     */ 
    private Traverser _next_traverser_to_return;


    protected Step( Step prior) {
        _prior_step = prior;
    }


    @Override
    public boolean hasNext() {
        if (_next_traverser_to_return!=null) {
            return true;
        }
        _next_traverser_to_return = _next();
        return _next_traverser_to_return!=null;
    }

    @Override
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
    abstract protected Traverser _next();
 
    /**
     * Return a new query specification that includes a traversal 
     * from the Traversers generated here to Traversers one "out" 
     * hop further in a traversal. 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Step 
     */
    public Step out() {
        Step retval = new TraversalStep(this, Graph.Direction.OUT);
        return retval;
    }

    /**
     * Return a new query specification that includes a traversal 
     * from the Traversers generated here to Traversers one "out" 
     * hop (with a label constraint on the edge) further in a 
     * traversal. 
     *  
     * @author Jason (11/4/2018)
     * 
     * @param label  edges with only this label are considered in 
     *               the next hop of the traversal.  null is an
     *               acceptable value that all edges will be
     *               considered.
     * @return Step 
     */
    public Step out(String label) {
        Step retval = new TraversalStep(this, Graph.Direction.OUT, label);
        return retval;
    }

    /**
     * The in() version of out().
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Step 
     */
    public Step in() {
        Step retval = new TraversalStep(this, Graph.Direction.IN);
        return retval;
    }

    /**
     * Return a new query specification that includes a traversal 
     * from the Traversers generated here to Traversers one "out" 
     * hop (with a label constraint on the edge) further in a 
     * traversal. 
     *  
     * @author Jason (11/4/2018)
     * 
     * @param label  edges with only this label are considered in 
     *               the next hop of the traversal.  null is an
     *               acceptable value that all edges will be
     *               considered.
     * 
     * @return Step 
     */
    public Step in(String label) {
        Step retval = new TraversalStep(this, Graph.Direction.IN, label);
        return retval;
    }

    /**
     * The in() version of out().
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Step 
     */
    public Step both() {
        Step retval = new TraversalStep(this, Graph.Direction.BOTH);
        return retval;
    }

    /**
    * Return a new query specification that includes a traversal 
    * from the Traversers generated here to Traversers one "out" 
    * hop (with a label constraint on the edge) further in a 
    * traversal. 
    *  
    * @author Jason (11/4/2018)
    * 
    * @param label  edges with only this label are considered in 
    *               the next hop of the traversal.  null is an
    *               acceptable value that all edges will be
    *               considered.
    * 
    * @return Step 
     */
    public Step both(String label) {
        Step retval = new TraversalStep(this, Graph.Direction.BOTH, label);
        return retval;
    }

    /**
     * Return a new query specification that includes a subsequent 
     * step that either eliminates paths with cycles or includes 
     * only paths with cycles. 
     *  
     * <p>Note: the definition of cycle here is that a vertex occurs 
     * twice in a path. 
     * </p> 
     * 
     * @author Jason (11/4/2018)
     * 
     * @param want_cycles true if cycles are wanted.  false if 
     *                    cycles are not wanted.
     * 
     * @return Step 
     */
    public Step cycles(Boolean want_cycles) {
        Step retval = new CycleFilterStep(this, want_cycles);
        return retval;
    }


    /**
     * Get a string that represents the query specification that 
     * ends with this Step. 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return String 
     */
    public String toQueryString() {
        String prior_string = (_prior_step == null) ? "" : _prior_step.toQueryString() + ".";
        return  prior_string+_toStepString();

    }

    /**
     * This method needs to be implemented by subclasses so that 
     * this class can implement the toQueryString() method.  The 
     * returned string can be something like "in()" or 
     * "out(\"likes\") for example. 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return String 
     */
    abstract protected String _toStepString();


}
