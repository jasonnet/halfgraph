package graph;

//import static org.jasonnet.logln.Logln.logln;
import java.util.ArrayList;

/**
 * An element in the path of a traversal.   Implicitly, because 
 * it is in a linked list of traversers, it actually also 
 * expresses the path itself. 
 *  
 * <p>Limitation: this class is not unit tested.</p> 
 * 
 * @author Jason (11/4/2018)
 */
public
class Traverser {
    private Traverser _prior_trav;
    private Vertex _vertex;

    private Graph.Direction _direction;
    private Edge _edge;

    protected Traverser( Traverser prior, Graph.Direction direction, Vertex vert, Edge edge) {
        this._prior_trav = prior;
        this._vertex = vert;
        this._direction = direction;
        this._edge = edge;
    }

    protected Traverser( Vertex vert) {
        // Ideally we'd have a private .NONE value we could specify here.  Java doesn't support that though.  So we're going to specify an arbitrary value.  All code should check if there is a prior traverser before accessing the direction 
        this( /*prior:*/null, Graph.Direction.IN, vert, /*edge:*/ null);
    }

    /**
     * Returns a representation of the whole Traverser chain that 
     * begins with this object. 
     *  
     * <p>Limitation: Conceptionally this method really should be a 
     * method of Path, but we don't have such a class or 
     * interface.</p> 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return String 
     */
    public String toString() {
        boolean verbose = true;
        String prior_string;
        if (_prior_trav == null) {
            prior_string = "";
        } else {
            String edge_str;
            if (verbose) {
                edge_str = (_edge._label.isPresent()) ? "["+_edge._label.get()+":]-" : "";
            } else {
                edge_str = "";
            }
            prior_string = _prior_trav.toString() + (_direction==Graph.Direction.OUT ? "-"+edge_str+">" : "<-"+edge_str);
        }
        if (verbose) {
            // ex.        (person:_0)-[to_self:]->(person:_0)-[likes:]->(person:id1)-[likes:]->(person:id2)
            return  prior_string+_vertex.toVerboseString();
        } else {
            // ex.      (_0)->(_0)->(id1)->(id2)
            return  prior_string+_vertex.toString();
        }
    }



    /**
     * The vertex for this step in the traversal.
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Vertex   This value is never null.
     */
    public Vertex vertex() {
        return _vertex;
    }

    /**
     * The direction of this hop. If prior_traverser() would return 
     * a null value, this method would throw a RuntimeException(). 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Graph.Direction the direction of travel. IN or OUT. 
     *         This usually will be obvious if the step was in() or
     *         out(), but this method can be valuable if the step
     *         was both().
     */
    public Graph.Direction direction() {
        if (_prior_trav == null) throw new RuntimeException("no prior");
        return _direction;
    }

    /**
     * The Edge of this hop. If prior_traverser() would return a 
     * null value, this method would throw a RuntimeException(). 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Edge the edge of the hop
     */
    public Edge edge() {
        if (_prior_trav == null) throw new RuntimeException("no prior");
        return _edge;
    }

    /**
     * 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Traverser   This value can be null.  Null indicates 
     *         the end of the chain.
     */
    public Traverser prior_traverser() {
        return _prior_trav;
    }

}
