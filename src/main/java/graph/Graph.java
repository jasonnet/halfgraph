package graph;

//import static org.jasonnet.logln.Logln.logln;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

// todo: document all RuntimeExceptions

/**
 * A simple in-memory graph implementation. 
 *  
 * Limitation: This class has only the methods needed to achieve 
 * the requirements of the assignment.  For example, it does not 
 * provide callers with the ability to list or find edges. 
 * Similarly, it doesn't provide a way for a caller to get a 
 * list of the vertices of the graph, except through the use of 
 * the Step methods. 
 *  
 * Limitation: labels can only be strings. 
 *  
 * Limitation: To keep this implementation small, it reuses 
 * exceptions that are provided by Java rather than providing 
 * more descriptive exceptions of its own.  As a side effect of 
 * this, some exceptions used are RuntimeExceptions whereas an 
 * ideal implementation would use non-RuntimeExceptions. 
 *  
 *  
 * @author Jason (11/4/2018)
 */
public
class Graph {

    private ArrayList<Vertex> _vertices = new ArrayList<>();
    private HashMap<String,Vertex> _vertices_by_exid = new HashMap<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public enum Direction { IN, OUT, BOTH };

    protected static final String INTERNAL_ID_PREFIX = "_"; 

    public Graph() {
    }

    /**
     *  
     *  
     * 
     * @author Jason (11/4/2018)
     * 
     * @param label - the label of the vertex to create.  A null 
     *              value may be specified.
     *  
     * @param id - the identifier whereby the vertex can be found 
     *           and identified.  A null value is allowed in which
     *           case an id will be automatically assigned. Those
     *           automatically assigned values begin will begin with
     *           a _ character to distinguish them from explicitly
     *           specified id's.   This method does not allow the
     *           caller to specify an id that begins with the _
     *           character.
     * 
     * @return Vertex 
     */
    public Vertex addVertex( String label, String id ) {
        if (id!=null) {
            if (id.startsWith(INTERNAL_ID_PREFIX)) throw new IllegalArgumentException("specified id parameter must not begin with _");
            if (_vertices_by_exid.containsKey(id)) throw new IllegalArgumentException("specified vertex already exists");
        }
        Vertex retval = new Vertex( this, _vertices.size(), label, /*external_id:*/id );
        if (id!=null) {
            _vertices_by_exid.put(id,retval);
        }
        _vertices.add(retval);
        return retval;
    }

    protected Edge _addEdge( Vertex vSrc, String label, Vertex vDst ) {
        Edge retval = new Edge( this, edges.size(), vSrc, label, vDst );
        edges.add(retval);
        return retval;
    }

    class VerticesStep extends Step {
        String _key_string; // design: this field exists just to improve the quality of the toString() method
        Graph _g;
        Iterator<Vertex> _iter;

        protected VerticesStep( Graph g) {
            super(null);
            this._g = g;
            _key_string = "()";
            _iter = _g._vertices.iterator();
        }
        protected VerticesStep( Vertex v) {
            super(null);
            this._g = v._g;
            _key_string = v.toString();
            ArrayList<Vertex> al = new ArrayList<>(); al.add(v);  
            _iter = al.iterator();
        }

        protected Traverser _next() {
            if (_iter.hasNext()) {
                Vertex vv = _iter.next();
                if (vv == null) return null;
                Traverser retval = new Traverser( vv);
                return retval;
            }
            return null;
        }
        protected String _toStepString() {
            return "g.V"+_key_string+"";
        }
    }

    Step V() {
        return new VerticesStep(this);
    }
    Step V(String id) {
        if (id==null) return null;   // todo: evaluate what is the proper behavior to implement here
        Vertex vFound = _vertices_by_exid.get(id);
        if (vFound==null) {
            if (id.startsWith(INTERNAL_ID_PREFIX)) {
                // perhaps an auto-genned internal id was specified
                String numstr = id.substring(INTERNAL_ID_PREFIX.length());
                int num = Integer.parseInt(numstr);
                vFound = _vertices.get(num);
            } else {
                vFound = _vertices_by_exid.get(id);
            }
            if (vFound==null) {
                throw new RuntimeException("vertex not found");
            }
        }
        return new VerticesStep(vFound);
    }

}
