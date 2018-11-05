package graph;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Iterator;

/**
 * The vertex. 
 *  
 * <p>Limitations:</p> 
 * <p>Limitations:</p> 
 * 
 * @author Jason (11/4/2018)
 */
public
class Vertex extends Element {
    private Optional<String> _exid;  // post-deadline-comment: todo: stop using Optional and start using a verifier, @NonNull and @Nullable
    private ArrayList<Edge> _outs = new ArrayList<>();
    private ArrayList<Edge> _ins = new ArrayList<>();

    /**
     * 
     * 
     * @author Jason (11/4/2018)
     * 
     * @param g   the graph
     * @param id  the internal id of the new vertex.  
     * @param label  the label of the vertex.  null is an acceptable 
     *               value.
     * @param exid  the externalid of the vertex.  null is an 
     *              acceptable value in which case the new vertex
     *              can be looked up by the internal id only.
     */
    protected Vertex( Graph g, int id, String label, String exid ) {
        // todo: improve signature so that callers do not accidentally swap the last two parameters
        super(g, id, label );
        this._exid = Optional.ofNullable(exid);
    }

    /**
     * 
     * 
     * @author Jason (11/4/2018)
     * 
     * @param label  the label of the new edge.  null is an 
     *               acceptable value.
     * @param vDst   the destination vertex of the edge.  null is 
     *               not an acceptable value.
     * 
     * @return Edge 
     */
    public Edge addEdge( String label, Vertex vDst ) throws java.lang.RuntimeException {
        if (vDst==null) {
            throw new java.lang.NullPointerException();   // todo: improve this signaling
        }
        if (vDst._g != this._g) {
            throw new java.lang.RuntimeException("wrong graph");   // todo: improve this signaling
        }
        Edge retval = _g._addEdge( this, label, vDst );
        _outs.add(retval);
        vDst._ins.add(retval);
        return retval;
    }

    @Override
    public String id() {
        return this._exid.orElse(super.id());  // if it's unspecified here, let Element give us the auto-generated value
    }


    protected String toVerboseString() {
        String vprefix = _label.orElse("");
        return "(" + vprefix + ":" + _exid.orElse(super.id()) + ")";
    }



    public String toString() {
        return "(\"" + _exid.orElse(super.id()) + "\")";
    }

    /**
     * Get an iterator of edges for this vertex.
     *  
     * <p>Limitation: This method does not yet support the BOTH 
     * direction.  If BOTH is passed in, this method will throw a 
     * RuntimeException. </p> 
     *  
     * @author Jason (11/4/2018)
     * 
     * @param direction  the direction of the edges to be returned. 
     *                   No edges that are not of this direction
     *                   will be returned.   
     * 
     * @return Iterator 
     */
    public Iterator<Edge> getEdges( Graph.Direction direction ) {
        if (direction==Graph.Direction.OUT) return this._outs.iterator();
        if (direction==Graph.Direction.IN) return this._ins.iterator();
        if (direction==Graph.Direction.BOTH) throw new RuntimeException("BOTH not yet supported");  
        throw new RuntimeException("not yet supported");  
    }

}
