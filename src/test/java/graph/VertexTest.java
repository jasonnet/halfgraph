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
public class VertexTest 
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
        Vertex v = graph.addVertex("person", "vvv");
        assertNotNull(v);
        assertNotNull(v.id());
        assertTrue(v.id().equals("vvv"));
        assertTrue(v._id()==0);

        v = graph.addVertex("person", null);
        assertNotNull(v);
        assertNotNull(v.id());
        assertTrue(v.id().equals("_1"));
        assertTrue(v._id()==1);
    }



}
