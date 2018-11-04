package graph;

//import static org.jasonnet.logln.Logln.logln;
import java.util.Iterator;

/**
 * implementation of the in(), out() and both() steps of query 
 * specification. 
 * 
 * @author Jason (11/4/2018)
 */
public
class TraversalStep extends Step {
    /**
     * the incoming Traverser that next() and hasNext() is working 
     * on. The next Traversers that this class generates will have a
     * reference to this Traverser.
     * 
     * @author Jason (11/4/2018)
     */
    private Traverser _prev_traverser;  

    /**
     * Indicates if the query specification was in(), out() or
     * both(). 
     * 
     * @author Jason (11/4/2018)
     */
    private Graph.Direction _requested_direction;

    /**
     * Indicates if the query specification was in() (null), or 
     * in("likes") or something else. 
     * 
     * @author Jason (11/4/2018)
     */
    private String _requested_label;

    /**
     * the out edges of the _prev_traverser vertex that this class 
     * will consider.  A null value indicate there are none to 
     * consider. 
     * 
     * @author Jason (11/4/2018)
     */
    private Iterator<Edge> _edge_iter_out;  
    /**
     * the in edges of the _prev_traverser vertex that this class 
     * will consider.  A null value indicate there are none to 
     * consider. 
     * 
     * @author Jason (11/4/2018)
     */
    private Iterator<Edge> _edge_iter_in;

    /**
     * Constructor
     * 
     * @author Jason (11/4/2018)
     * 
     * @param prior   this must not be null. 
     * @param edge_direction IN, OUT or BOTH
     * @param label   null is an acceptable value 
     */
    protected TraversalStep( Step prior, Graph.Direction edge_direction, String label ) {
        super(prior);
        if (prior==null) throw new RuntimeException();
        if (edge_direction==Graph.Direction.OUT) {
        } else if (edge_direction==Graph.Direction.IN) {
        } else if (edge_direction==Graph.Direction.BOTH) {
        } else {
            throw new RuntimeException("internal error. not yet supported");  // future proof code
        }
        _requested_label = label;
        _requested_direction = edge_direction;
    }


    protected TraversalStep( Step prior, Graph.Direction edge_direction ) {
        this(prior,edge_direction,null);
    }

    private Iterator<Edge> __next_iter() {
        // Design: we need this complicated method (or other similarly complicated impls) to support .both() steps in the face of edges whose src and dst are the same vertex.  This allows us to treat the traversal in each direction uniquely for the sake 
        Iterator<Edge> retval;
        if (_edge_iter_out!=null) {
            if (_edge_iter_out.hasNext()) {
                return _edge_iter_out;
            } else {
                _edge_iter_out = null;
            }
        }
        if (_edge_iter_in!=null) {
            if (_edge_iter_in.hasNext()) {
                return _edge_iter_in;
            } else {
                _edge_iter_in = null;
            }
        }
        return null;
    }

    @Override
    /**
     * Implements most of the Step.next() functionality.
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Traverser - this value can be null
     */
    protected Traverser _next() {
        Iterator<Edge> edge_iter;
        while ((edge_iter= __next_iter()) !=null) {
            // assert: edge_iter.hasNext()  
            Edge edge = edge_iter.next();
            if ((_requested_label==null) || (edge._label.isPresent() && edge._label.get().equals(_requested_label) )) {
                Traverser retval = new Traverser( _prev_traverser,  edge_iter==_edge_iter_in? Graph.Direction.IN : Graph.Direction.OUT,   edge_iter==_edge_iter_in? edge._vSrc : edge._vDst, edge);  // what does the Neptune code style guide say about long lines like this?
                return retval;
            }
        }
        while (_prior_step.hasNext()) {
            _prev_traverser = _prior_step.next();
            // todo: check for null or have already called hasNext()
            Vertex prior_vertex = _prev_traverser.vertex();

            _edge_iter_out = ((_requested_direction==Graph.Direction.OUT) || (_requested_direction==Graph.Direction.BOTH)) ? prior_vertex.getEdges( Graph.Direction.OUT ) : null;
            _edge_iter_in = ((_requested_direction==Graph.Direction.IN) || (_requested_direction==Graph.Direction.BOTH)) ? prior_vertex.getEdges( Graph.Direction.IN ) : null ;
            while ((edge_iter= __next_iter()) !=null) {
                // assert: edge_iter.hasNext()  
                Edge edge = edge_iter.next();
                if ((_requested_label==null) || (edge._label.isPresent() && edge._label.get().equals(_requested_label) )) {
                    Traverser retval = new Traverser( _prev_traverser,  edge_iter==_edge_iter_in? Graph.Direction.IN : Graph.Direction.OUT,   edge_iter==_edge_iter_in? edge._vSrc : edge._vDst, edge);  // what does the Neptune code style guide say about long lines like this?
                    return retval;
                }
            }
        }
        return null;  // nothing left
    }

    /**
     * Returns a string representing what this Step is doing. 
     * Typical values include  in() and out. 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return String   never null
     */
    protected String _toStepString() {
        String filter_label = _requested_label==null ? "" : "\""+_requested_label+"\"";
        if (_requested_direction==Graph.Direction.OUT) return "out("+filter_label+")";
        if (_requested_direction==Graph.Direction.BOTH) return "both("+filter_label+")";
        if (_requested_direction==Graph.Direction.IN) return "in("+filter_label+")";
        throw new RuntimeException("internal error");
    }


}

