package graph;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.Iterator;

/**
 * Unit test for Graph. 
 *  
 * <p>Limitations: Because of how much interaction we have 
 * between our classes, most hypothetical unit tests would look 
 * a lot like "function" tests.  And right now we have a lot of 
 * function test in our FunctionTest class.  So right now these
 * unit tests focus on what can't easily be tested in 
 * FunctionTest's use of snapshots.<p>
 *  
 * <p>Limitation: There is not yet any testing to confirm that 
 * methods are suitably set to public or private or 
 * protected.<p> 
 *  
 */
public class GraphTest 
{
    static String LABEL_PERSON = "person";
    static String LABEL_PLACE = "place";

    static String LABEL_KNOWS = "knows";
    static String LABEL_LIVES_IN = "lives_in";


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
    public void shouldBeConstructable_Graph() {
        Graph graph = new Graph();
        Vertex v1bruce = graph.addVertex(LABEL_PERSON, "bruce");
        assertNotNull(v1bruce);

        Vertex v2mike = graph.addVertex(LABEL_PERSON, "mike");
        assertNotNull(v2mike);
        
        Vertex v3seattle  = graph.addVertex(LABEL_PLACE, "seattle");
        assertNotNull(v3seattle);

        Edge edge;
        edge = v1bruce.addEdge( LABEL_KNOWS, v2mike );
        assertNotNull(edge);
        edge = v2mike.addEdge( LABEL_KNOWS, v1bruce );
        assertNotNull(edge);
        edge = v2mike.addEdge( LABEL_LIVES_IN, v3seattle );
        assertNotNull(edge);

        Step queryAll = graph.V();
        testIterator(queryAll, 3);
        Step queryOne = graph.V("bruce");;
        testIterator(queryOne, 1);
    }

    @Test
    public void shouldAcceptNullLabel_addVertex() {
        Graph graph = new Graph();
        Vertex v1bruce = graph.addVertex(null, "bruce");
        assertNotNull(v1bruce);

        Step queryAll = graph.V();
        testIterator(queryAll, 1);
    }

    @Test
    public void shouldAcceptNullId_addVertex() {
        Graph graph = new Graph();
        Vertex v1nameless = graph.addVertex(LABEL_PERSON, null);
        assertNotNull(v1nameless);

        Step queryAll = graph.V();
        testIterator(queryAll, 1);
    }

    @Test
    public void shouldThrowExceptions_addVertex() {
        Graph graph = new Graph();
        Vertex v2mike = graph.addVertex(null, "mike");
        assertNotNull(v2mike);
        try {
            Vertex v2mike_again = graph.addVertex(null, "mike");
            assertTrue("expected exception did not occur", false);
        } catch (IllegalArgumentException exc) {
            assertTrue(true);
        } catch (Exception exc) {
            assertTrue("unexpected exception", false);
        }

        try {
            Vertex v4bogus = graph.addVertex(null, "_underscore_prefix_not_allowed_here");
            assertTrue("expected exception did not occur", false);
        } catch (IllegalArgumentException exc) {
            assertTrue(true);
        } catch (Exception exc) {
            assertTrue("unexpected exception", false);
        }

    }

    @Test
    public void shouldThrowExceptions_V() {
        Graph graph = new Graph();
        Vertex v2mike = graph.addVertex(null, "mike");
        assertNotNull(v2mike);
        try {
            Step step1 = graph.V(null);
            assertTrue("expected exception did not occur", false);
        } catch (IllegalArgumentException exc) {
            assertTrue(true);
        } catch (Exception exc) {
            assertTrue("unexpected exception", false);
        }

    }


}
