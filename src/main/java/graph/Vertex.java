package graph;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Iterator;

public
class Vertex extends Element {
    Optional<String> _exid;
    ArrayList<Edge> _outs = new ArrayList<>();
    ArrayList<Edge> _ins = new ArrayList<>();

    protected Vertex( Graph g, int id, String label, String exid ) {
        // todo: improve signature so that callers do not accidentally swap the last two parameters
        super(g, id, label );
        this._exid = Optional.ofNullable(exid);
    }

    public Edge addEdge( String label, Vertex vDst ) throws java.lang.RuntimeException {
        if (vDst._g != this._g) {
            throw new java.lang.RuntimeException("wrong graph");   // todo: improve this signaling
        }
        Edge retval = _g._addEdge( this, label, vDst );
        _outs.add(retval);
        vDst._ins.add(retval);
        return retval;
    }

    public String toVerboseString() {
        String vprefix = _label.orElse("");
        return "(" + vprefix + ":" + _exid.orElse("_"+_id) + ")";
    }

    public String toString() {
        return "(\"" + _exid.orElse("_"+_id) + "\")";
    }

    public Iterator<Edge> getEdges( Graph.Direction direction ) {
        if (direction==Graph.Direction.OUT) return this._outs.iterator();
        if (direction==Graph.Direction.IN) return this._ins.iterator();
        if (direction==Graph.Direction.BOTH) throw new RuntimeException("BOTH not yet supported");  
        throw new RuntimeException("not yet supported");  
    }

}
