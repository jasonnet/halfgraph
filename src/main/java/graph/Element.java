package graph;

import java.util.HashMap;
import java.util.Optional;

public
class Element {
    int _id;
    Graph _g;
    Optional<String> _label;
    HashMap<String,String>  props = new HashMap<>();

    protected Element( Graph g, int id, String label ) {
        this._id = id;
        this._g = g;
        this._label = Optional.ofNullable(label);
    }

    public Element addProp( String label, String val ) {
        props.put(label, val);
        return this;
    }

}
