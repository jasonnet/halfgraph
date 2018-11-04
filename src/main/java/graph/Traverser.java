package graph;

//import static org.jasonnet.logln.Logln.logln;
import java.util.ArrayList;

public
class Traverser {
    protected Traverser _prior_trav;
    protected Vertex _vertex;
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

    protected Vertex _top_vertex() {
        return this._vertex;
    }

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

}
