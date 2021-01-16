package it.unicam.cs.asdl2021.mp2;

import it.unicam.cs.asdl2021.mp2.Task1.AdjacencyMatrixUndirectedGraph;
import it.unicam.cs.asdl2021.mp2.Task1.Graph;
import it.unicam.cs.asdl2021.mp2.Task1.GraphEdge;
import it.unicam.cs.asdl2021.mp2.Task1.GraphNode;
import it.unicam.cs.asdl2021.mp2.Task3.PrimMSP;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PrimMSPTest {

    @Test
    void computeMSP() {
        Graph<String> gr = new AdjacencyMatrixUndirectedGraph<>();
        GraphNode<String> a = new GraphNode<String>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        gr.addNode(d);
        GraphNode<String> e = new GraphNode<String>("e");
        gr.addNode(e);
        GraphNode<String> f = new GraphNode<String>("f");
        gr.addNode(f);
        GraphNode<String> g = new GraphNode<String>("g");
        gr.addNode(g);
        GraphNode<String> h = new GraphNode<String>("h");
        gr.addNode(h);
        GraphNode<String> i = new GraphNode<String>("i");
        gr.addNode(i);
        //gr.addEdge(new GraphEdge<String>(a, a, false, 4));
        gr.addEdge(new GraphEdge<String>(a, b, false, 4));
        gr.addEdge(new GraphEdge<String>(a, h, false, 8.5));
        gr.addEdge(new GraphEdge<String>(b, h, false, 11));
        System.out.println(gr.getEdgesOf(a));
        gr.addEdge(new GraphEdge<String>(b, c, false, 8));
        gr.addEdge(new GraphEdge<String>(c, i, false, 2));
        gr.addEdge(new GraphEdge<String>(c, d, false, 7));
        gr.addEdge(new GraphEdge<String>(c, f, false, 4));
        gr.addEdge(new GraphEdge<String>(d, f, false, 14));
        gr.addEdge(new GraphEdge<String>(d, e, false, 9));
        gr.addEdge(new GraphEdge<String>(e, f, false, 10));
        gr.addEdge(new GraphEdge<String>(f, g, false, 2));
        gr.addEdge(new GraphEdge<String>(g, i, false, 6));
        gr.addEdge(new GraphEdge<String>(g, h, false, 1));
        gr.addEdge(new GraphEdge<String>(h, i, false, 7));
        PrimMSP<String> alg = new PrimMSP<>();
        alg.computeMSP(gr,a);
        Set<GraphEdge<String>> result = new HashSet<GraphEdge<String>>();
        result.add(new GraphEdge<String>(a, b, false, 4));
        result.add(new GraphEdge<String>(b, c, false, 8));
        result.add(new GraphEdge<String>(c, i, false, 2));
        result.add(new GraphEdge<String>(c, d, false, 7));
        result.add(new GraphEdge<String>(c, f, false, 4));
        result.add(new GraphEdge<String>(d, e, false, 9));
        result.add(new GraphEdge<String>(f, g, false, 2));
        result.add(new GraphEdge<String>(g, h, false, 1));
        assertNull(a.getPrevious());
        assertTrue(a.getPrevious()==null);
        assertEquals(a,b.getPrevious());
        assertEquals(b,c.getPrevious());
        assertEquals(b.getPrevious(), a);
        assertEquals(c.getPrevious(), b);
        assertEquals(i.getPrevious(), c);
        assertEquals(f.getPrevious(), c);
        assertEquals(g.getPrevious(), f);
        assertEquals(h.getPrevious(), g);
        assertEquals(d.getPrevious(), c);
        assertEquals(e.getPrevious(), d);
        gr.clear();
        gr.addNode(a);
        gr.addNode(b);
        gr.addNode(c);
        gr.addNode(d);
        gr.addNode(e);
        gr.addNode(f);
        gr.addEdge(new GraphEdge<String>(a, b, false, 4));
        gr.addEdge(new GraphEdge<String>(a, c, false, 2));
        gr.addEdge(new GraphEdge<String>(b, c, false, 4));
        gr.addEdge(new GraphEdge<String>(c, d, false, 3));
        gr.addEdge(new GraphEdge<String>(c, e ,false, 4));
        gr.addEdge(new GraphEdge<String>(c, f, false, 2));
        gr.addEdge(new GraphEdge<String>(d, e, false, 3));
        gr.addEdge(new GraphEdge<String>(e, f, false, 3));
        gr.addEdge(new GraphEdge<String>(f, f, false, 2));
       /* gr.addEdge(new GraphEdge<String>(e, f, false, 10));
        gr.addEdge(new GraphEdge<String>(f, g, false, 2));
        gr.addEdge(new GraphEdge<String>(g, i, false, 6));
        gr.addEdge(new GraphEdge<String>(g, h, false, 1));
        gr.addEdge(new GraphEdge<String>(h, i, false, 7));*/
        PrimMSP<String> alg2 = new PrimMSP<>();
        alg2.computeMSP(gr,a);
        assertNull(a.getPrevious());
        assertEquals(c.getPrevious(),a);
        assertEquals(b.getPrevious(),a);
       // assertEquals(b.getPrevious(), c);
        assertEquals(d.getPrevious(), c);
        assertEquals(f.getPrevious(), c);
        assertEquals(e.getPrevious(),d);
        //assertEquals(e.getPrevious(), f);
   /*     assertEquals(g.getPrevious(), f);
        assertEquals(h.getPrevious(), g);
        assertEquals(d.getPrevious(), c);
        assertEquals(e.getPrevious(), d);*/
    }
}