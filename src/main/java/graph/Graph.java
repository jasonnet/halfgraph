package graph;

//import static org.jasonnet.logln.Logln.logln;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

// todo: document all RuntimeExceptions

/**
 * A simple in-memory graph implementation. 
 *  
 * <p>Limitation: This class has only the methods needed to 
 * achieve the requirements of the assignment.  For example, it 
 * does not provide callers with the ability to list or find 
 * edges. Similarly, it doesn't provide a way for a caller to 
 * get a list of the vertices of the graph, except through the 
 * use of the Step methods. </p>
 *  
 * <p>Limitation: labels can only be strings. </p>
 *  
 * <p>Limitation: To keep this implementation small, it reuses 
 * exceptions that are provided by Java rather than providing 
 * more descriptive exceptions of its own.  As a side effect of 
 * this, some exceptions used are RuntimeExceptions whereas an 
 * ideal implementation would use non-RuntimeExceptions. </p>
 *  
 * <p>Limitation: This implementation lacks many features that 
 * one would expect in a property graph implementation, 
 * including properties. (smile)   
 *  
 * @author Jason (11/4/2018)
 */
public
class Graph {

    private ArrayList<Vertex> _vertices = new ArrayList<>();
    private HashMap<String,Vertex> _vertices_by_exid = new HashMap<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public enum Direction { IN, OUT, BOTH };

    /**
     * Limitation: Callers can not create Vertice or Edges that have
     * an id that is explicitly specified to start with this 
     * string.  OTOH... if a Vertex or Edge is created with without 
     * a specified id, HalfGraph will automatically assign an id and
     * that id will begin with this string. 
     * 
     * @author Jason (11/4/2018)
     */
    protected static final String INTERNAL_ID_PREFIX = "_"; 

    public Graph() {
    }

    /**
     *  add a Vertex.
     *  
     * 
     * @author Jason (11/4/2018)
     * 
     * @param label   the label of the vertex to create.  A null 
     *              value may be specified.
     *  
     * @param id   the identifier to be associated with the new 
     *           Vertex. A
     *           null value is allowed in which case an id will be
     *           automatically assigned. Those automatically
     *           assigned values begin with a the
     *           Graph.INTERNAL_ID_PREFIX string to distinguish them
     *           from explicitly specified id's. This method does
     *           not allow the caller to specify an id that begins
     *           with the that string.  If a caller specifies such
     *           an id, or an id that has already been assigned, an
     *           java.lang.IllegalArgumentException is thrown.
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

    /**
     * a source step that can act as the source of a traversal.  It 
     * can generate Traversers for all vertices of the graph or just 
     * a specified vertex. 
     * 
     * @author Jason (11/4/2018)
     */
    private class VerticesStep extends Step {
        private String _key_string; // design: this field exists just to improve the quality of the toString() method
        //private Graph _g;
        private Iterator<Vertex> _iter;

        protected VerticesStep( Graph g) {
            super(null);
            //this._g = g;
            _key_string = "()";
            _iter = g._vertices.iterator();
        }
        protected VerticesStep( Vertex v) {
            super(null);
            //this._g = v._g;
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
        /**
         * returns a string like g.V() or g.V("john")
         * 
         * @author Jason (11/4/2018)
         * 
         * @return String 
         */
        protected String _toStepString() {
            return "g.V"+_key_string+"";
        }
    }

    /**
     * Return the first step in a traversal that begins with all the 
     * vertices of the graph. 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return Step 
     */
    public Step V() {
        return new VerticesStep(this);
    }


    /**
     * Return the first step in a traversal that begins with only 
     * the specified vertex of the graph. 
     *  
     * @param id   The id of the vertex at which to start the 
     *             traversal.  If there is no Vertex with the
     *             specified id, a Step is still generated, but no
     *             Traversers will be produced by that Step.   This
     *             parameter is not allowed to be null. If null is
     *             passed in, a IllegalArgumentException is thrown.
     *             If the
     * @author Jason (11/4/2018)
     * 
     * @return Step The Step created.
     */
    public Step V(String id) {
        if (id==null) throw new IllegalArgumentException("specified id parameter was null");
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
