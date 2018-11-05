package graph;

import java.util.HashMap;
import java.util.Optional;

/**
 * The superclass of the Vertex and Edge class.  This class 
 * provides property support. 
 *  
 * <p>Limitation: This class does not yet have unit tests.</p> 
 * 
 * <p>Limitation: Property values must be expressed as strings.
 * </p> 
 * 
 * <p>Limitation: (post-deadline-comment) This code would be 
 * better served by use of @NonNull and @Nullable annotations 
 * and a verifier instead of Optional. 
 * </p> 
 * 
 * @author Jason (11/4/2018)
 */
abstract
public
class Element {
    private int _id;
    private HashMap<String,String>  _props = new HashMap<>();

    protected Graph _g;
    protected Optional<String> _label;  // post-deadline-comment: todo: stop using Optional and start using a verifier, @NonNull and @Nullable

    /**
     * 
     * 
     * @author Jason (11/4/2018)
     * 
     * @param g  the graph
     * @param id   this is the internal id of the element.
     * @param label - a null value is acceptable.  This indicates 
     *              that the caller did not care what label is
     *              associated with this element.
     */
    protected Element( Graph g, int id, String label ) {
        this._id = id;
        this._g = g;
        this._label = Optional.ofNullable(label);
    }

    /**
     * adds a property to the element. 
     *  
     * @author Jason (11/4/2018)
     * 
     * @param key - this parameter must not be null.  If a null 
     *            value is passed a NullPointerException is thrown.
     * @param val  - this parameter must not be null. If a null 
     *            value is passed a NullPointerException is thrown.
     * 
     * @return Element  A pointer to the element itself.  This 
     *         allows chaining of method calls.
     */
    public Element addProp( String key, String val ) {
        _props.put(key, val);
        return this;
    }

    /**
     * gets the specified named property of the element.
     * 
     * @author Jason (11/4/2018)
     * 
     * @param key - this parameter must not be null.  If a null 
     *            value is passed a NullPointerException is thrown.
     * 
     * @return String  if the property was not found, null is 
     *         returned.
     */
    public String getProp( String key ) {
        return _props.get(key);
    }

    /**
     * get the external id of the Element. 
     *  
     * @author Jason (11/4/2018)
     * 
     * @return String 
     */
    public String id() {
        // protected comment: The subclass must override this method if it wants different behavior.
        return Graph.INTERNAL_ID_PREFIX + _id();
    }

    /**
     * get an internal id for the Element. 
     *  
     * <p>Every vertex and edge has an internal id even if it 
     * doesn't have an external id.</p> 
     * 
     * @author Jason (11/4/2018)
     * 
     * @return String 
     */
    public int _id() {
        return _id;
    }

}
