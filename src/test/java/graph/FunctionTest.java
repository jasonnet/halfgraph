package graph;

public
class FunctionTest {

    static public String LABEL_PERSON = "person";
    static public String LABEL_PLACE = "place";

    static public String LABEL_LIKES = "likes";
    static public String LABEL_LIVES_IN = "lives_in";
    static public String LABEL_TOSELF = "to_self";

    static public java.io.PrintStream ps = System.out;

    static public void report( Step step ) {
        ps.println("  "+step.toQueryString());
        step.forEachRemaining( trav -> ps.println("      "+trav) );
    }

    static public void runTestSuiteOnce(Graph graph) {
        if (true) {
            report(graph.V());
            report(graph.V("_0"));
            report(graph.V("id1"));
            report(graph.V()     .both());
            report(graph.V("_0") .both());
            report(graph.V("id1").both());
            report(graph.V()     .both(LABEL_LIKES));
            report(graph.V("_0") .both(LABEL_LIKES));
            report(graph.V()     .out());
            report(graph.V("_0") .out());
            report(graph.V("id1").out());
            report(graph.V()     .out().out());
            report(graph.V("id1").out().out());
            report(graph.V("_0") .out().out());
            report(graph.V()     .out().out().cycles(true));
            report(graph.V("id1").out().out().cycles(true));
            report(graph.V("_0") .out().out().cycles(true));
            report(graph.V()     .out().out().cycles(false));
            report(graph.V("id1").out().out().cycles(false));
            report(graph.V("_0") .out().out().cycles(false));
            report(graph.V()     .out().out().out());
            report(graph.V("id1").out().out().out());
            report(graph.V("_0") .out().out().out());
            report(graph.V()     .out().out().out().cycles(true));
            report(graph.V("id1").out().out().out().cycles(true));
            report(graph.V("_0") .out().out().out().cycles(true));
            report(graph.V()     .out().out().out().cycles(false));
            report(graph.V("id1").out().out().out().cycles(false));
            report(graph.V("_0") .out().out().out().cycles(false));
            report(graph.V()     .in());
            report(graph.V("id1").in());
            report(graph.V("_0") .in());
            report(graph.V()     .in().in());
            report(graph.V("id1").in().in());
            report(graph.V("_0") .in().in());
            report(graph.V()     .in().in().cycles(true));
            report(graph.V("id1").in().in().cycles(true));
            report(graph.V("_0") .in().in().cycles(true));
            report(graph.V()     .in().in().cycles(false));
            report(graph.V("id1").in().in().cycles(false));
            report(graph.V("_0") .in().in().cycles(false));
            report(graph.V()     .in().in().in());
            report(graph.V("id1").in().in().in());
            report(graph.V()     .in().in().in().cycles(true));
            report(graph.V()     .in().in().in().cycles(false));
            report(graph.V()     .in().out());
            report(graph.V("id1").in().out());
            report(graph.V("_0") .in().out());
            report(graph.V().out(LABEL_LIKES));
            report(graph.V("_0").out(LABEL_LIKES));
            report(graph.V("id1").out(LABEL_LIKES));
            report(graph.V().out(LABEL_LIVES_IN));
            report(graph.V("_0").out(LABEL_LIVES_IN));
            report(graph.V("id1").out(LABEL_LIVES_IN));
        }
    }

    static public void runAssignment(Graph graph) {
        ps.println("// 1. Show me all out edges from a vertex along a particular edge label.");
        report(graph.V().out(LABEL_LIKES));
        report(graph.V().out(LABEL_LIVES_IN));
        report(graph.V().out(LABEL_TOSELF));
        report(graph.V("_0").out(LABEL_LIKES));
        ps.println("// 2. Show me all in edges from a vertex along a particular edge label.");
        report(graph.V().in(LABEL_LIKES));
        report(graph.V().in(LABEL_LIVES_IN));
        report(graph.V("_0").in(LABEL_LIKES));
        ps.println("// 3. Show me all two-hop paths out from a vertex along a particular edge label. The solutions should clearly show the path from source to target (e.g. [a->b->c], [a->b->d], ...)");
        report(graph.V().out(LABEL_LIKES).out(LABEL_LIKES));
        report(graph.V("_0").out(LABEL_LIKES).out(LABEL_LIKES));
        ps.println("// 4. Same as (3) for three-hop paths.");
        report(graph.V().out(LABEL_LIKES).out(LABEL_LIKES).out(LABEL_LIKES));
        report(graph.V("_0").out(LABEL_LIKES).out(LABEL_LIKES).out(LABEL_LIKES));
        ps.println("// 5. Show me three-hop paths out that contain cycles.");
        report(graph.V().out().out().out().cycles(true));
        report(graph.V("_0").out().out().out().cycles(true));
        ps.println("// 6. Show me three-hop paths out that do not contain cycles.");
        report(graph.V().out().out().out().cycles(false));
        report(graph.V("_0").out().out().out().cycles(false));
    }


    static public void main(String[] args) throws java.io.FileNotFoundException {
        Graph graph = new Graph();

        ps = new java.io.PrintStream(new java.io.FileOutputStream("target/test.stdout.latest.log"));

        ps.println( "-------- "); ps.println("add two vertex _0 and id1");  ps.println("--------");
        Vertex v0 = graph.addVertex(LABEL_PERSON, null);
        Vertex v1 = graph.addVertex(LABEL_PERSON, "id1");
        runTestSuiteOnce(graph);

        ps.println( "-------- "); ps.println("add an edge from 0 to 1");  ps.println("--------");
        v0.addEdge(LABEL_LIKES, v1);
        runTestSuiteOnce(graph);

        ps.println( "-------- "); ps.println("add an edge from 0 to 0");  ps.println("--------");
        v0.addEdge(LABEL_TOSELF, v0);
        //runTestSuiteOnce(graph);


        ps.println( "-------- "); ps.println("add a duplicate edge from 0 to 1");  ps.println("--------");
        v0.addEdge(LABEL_LIKES, v1);
        //runTestSuiteOnce(graph);

        ps.println( "-------- "); ps.println("add v2 and edge from 1 to 2");  ps.println("--------");
        Vertex v2 = graph.addVertex(LABEL_PERSON, "id2");
        v1.addEdge(LABEL_LIKES, v2);
        //runTestSuiteOnce(graph);

        ps.println( "-------- "); ps.println("add v2 and edge from 2 to 3");  ps.println("--------");
        Vertex v3 = graph.addVertex(LABEL_PERSON, "id3");
        v2.addEdge(LABEL_LIKES, v3);
        //runTestSuiteOnce(graph);

        ps.println( "-------- "); ps.println("add edge from 3 to 1");  ps.println("--------");
        v3.addEdge(LABEL_LIKES, v1);
        runTestSuiteOnce(graph);

        ps.println( "-------- "); ps.println("add v4 and two edges from 3 to 4");  ps.println("--------");
        Vertex v4 = graph.addVertex(LABEL_PERSON, "place4");
        v3.addEdge(LABEL_LIKES, v4);
        v3.addEdge(LABEL_LIVES_IN, v4);
        runTestSuiteOnce(graph);

        ps.println( "-------- "); ps.println("add labelless v5 and like edge from 3 to 5");  ps.println("--------");
        Vertex v5 = graph.addVertex(null, "labelless5");
        v3.addEdge(LABEL_LIKES, v5);
        v5.addEdge(null, v2);
        runTestSuiteOnce(graph);

        ps.close();  // close the snapshot file

        ps = System.out;
        runAssignment(graph);
    }

}
