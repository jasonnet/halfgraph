package graph;


public
class Edge extends Element {
    Vertex _vSrc, _vDst;

    protected Edge( Graph g, int id, Vertex vSrc, String label, Vertex vDst ) {
        super(g, id, label);
        this._vSrc = vSrc;
        this._vDst = vDst;
    }



}
