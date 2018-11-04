package graph;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.Iterator;

/**
 * Unit test for Graph. 
 *  
 * Limitations: Because of how much interaction we have between 
 * our classes, most hypothetical unit tests would look a lot 
 * like "function" tests.  And right now we have a lot of
 * function test in our FunctionTest class.  So right now these
 * unit tests focus on what can't easily be tested in 
 * FunctionTest's use of snapshots.
 *  
 */
public class EdgeTest 
{

    void testIterator( Iterator iter, int expected_size ) {
        assertNotNull(iter);
        int cnt = 0;
        while (iter.hasNext()) {
            iter.next();
            cnt++;
        }
        assertTrue( cnt==expected_size );
    }

    @Test
    public void shouldHaveId() {
        Graph graph = new Graph();
        Vertex vA = graph.addVertex("person", "vvvA");
        Vertex vB = graph.addVertex("person", "vvvB");
        Edge edge = vA.addEdge( "likes", vB);
        assertNotNull(edge);
        assertNotNull(edge.id());
        assertTrue(edge.id().equals("_0")); // we don't give people a way to assign an id to the edge, so .id() will always return the automatically generated external_id.
        assertTrue(edge._id()==0);
    }



}
