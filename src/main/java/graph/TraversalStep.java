package graph;

//import static org.jasonnet.logln.Logln.logln;
import java.util.Iterator;

public
class TraversalStep extends Step {
    private Traverser _prev_traverser;  // todo: rename this
    private Graph.Direction _requested_direction;
    private String _requested_label;

    private Iterator<Edge> _edge_iter_out;
    private Iterator<Edge> _edge_iter_in;

    protected TraversalStep( Step prior, Graph.Direction edge_direction, String label ) {
        super(prior);
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
            Vertex prior_vertex = _prev_traverser._top_vertex();

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

    protected String _toStepString() {
        String filter_label = _requested_label==null ? "" : "\""+_requested_label+"\"";
        if (_requested_direction==Graph.Direction.OUT) return "out("+filter_label+")";
        if (_requested_direction==Graph.Direction.BOTH) return "both("+filter_label+")";
        if (_requested_direction==Graph.Direction.IN) return "in("+filter_label+")";
        throw new RuntimeException("internal error");
    }


}
