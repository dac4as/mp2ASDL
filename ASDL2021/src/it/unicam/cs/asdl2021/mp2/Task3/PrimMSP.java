package it.unicam.cs.asdl2021.mp2.Task3;

import it.unicam.cs.asdl2021.mp2.Task1.GraphEdge;
import it.unicam.cs.asdl2021.mp2.Task1.GraphNode;
import it.unicam.cs.asdl2021.mp2.Task1.Graph;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Classe singoletto che implementa l'algoritmo di Prim per trovare un Minimum
 * Spanning Tree di un grafo non orientato, pesato e con pesi non negativi.
 * 
 * L'algoritmo usa una coda di min priorità tra i nodi implementata dalla classe
 * TernaryHeapMinPriorityQueue. I nodi vengono visti come PriorityQueueElement
 * poiché la classe GraphNode<L> implementa questa interfaccia. Si noti che
 * nell'esecuzione dell'algoritmo è necessario utilizzare l'operazione di
 * decreasePriority.
 * 
 * @author Template: Luca Tesei, Implementation: Niccolò Cuartas - niccolo.cuartas@studenti.unicam.it
 * 
 * @param <L>
 *                etichette dei nodi del grafo
 *
 */
public class PrimMSP<L> {

    /*
     * Coda di priorità che va usata dall'algoritmo. La variabile istanza è
     * protected solo per scopi di testing JUnit.
     */
    protected TernaryHeapMinPriorityQueue queue;

    /**
     * Crea un nuovo algoritmo e inizializza la coda di priorità con una coda
     * vuota.
     */
    public PrimMSP() {
        this.queue = new TernaryHeapMinPriorityQueue();
    }

    /**
     * Utilizza l'algoritmo goloso di Prim per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non negativi.
     * Dopo l'esecuzione del metodo nei nodi del grafo il campo previous deve
     * contenere un puntatore a un nodo in accordo all'albero di copertura
     * minimo calcolato, la cui radice è il nodo sorgente passato.
     * 
     * @param g
     *              un grafo non orientato, pesato, con pesi non negativi
     * @param s
     *              il nodo del grafo g sorgente, cioè da cui parte il calcolo
     *              dell'albero di copertura minimo. Tale nodo sarà la radice
     *              dell'albero di copertura trovato
     * 
     * @throw NullPointerException se il grafo g o il nodo sorgente s sono nulli
     * @throw IllegalArgumentException se il nodo sorgente s non esiste in g
     * @throw IllegalArgumentException se il grafo g è orientato, non pesato o
     *        con pesi negativi
     */
    public void computeMSP(Graph<L> g, GraphNode<L> s) {
        if(g==null || s==null) throw new NullPointerException();
        if(!g.containsNode(s)) throw new IllegalArgumentException("Il nodo non esiste nel grafo passato");
        if(g.isDirected()) throw new IllegalArgumentException("Il grafo è orientato");
        System.out.println(g.getEdges());
        for(GraphEdge<L> edge : g.getEdges())
        {
            if(!edge.hasWeight() || edge.getWeight()<0)
                throw new IllegalArgumentException("Esistono archi con valore di peso non accettabile in questo grafo (NaN o <0)");
        }

        for(GraphNode<L> v : g.getNodes())
        {
            v.setPriority(Integer.MAX_VALUE);//set priorità iniziale
            v.setPrevious(null);//annulla il nodo precedente (se presente, altrimenti non cambia nulla)
        }

        queue.decreasePriority(s, 0);//il nodo sorgente ha priorità 0
        Set<GraphNode<L>> visitedNodes = new HashSet<>();

        for(GraphNode<L> v : g.getNodes())
        {
            this.queue.insert(v);//inserisco tutti i nodi nella queue di priorità
        }

        while(!this.queue.isEmpty())
        {
            GraphNode<L> u = (GraphNode<L>) this.queue.extractMinimum();//al primo passaggio dovrebbe essere estratto il nodo s (con priorità 0)
            for(GraphNode<L> v : g.getAdjacentNodesOf(u))//si va ad analizzare le adiacenze del nodo u
            {//viene analizzato ogni arco adiacente di ciascun nodo adiacente
                for(GraphEdge<L> w : g.getEdgesOf(v))
                {//se queue contiene il nodo v E se il peso dell'arco è minore della priorità associata al nodo adiacente (corrente)


                    if(this.queue.getTernaryHeap().contains(v) && w.getWeight()<v.getPriority())
                    {//il precedente di v è u
                        v.setPrevious(u);
                        //v.setPriority(w.getWeight());//e la priorità dell'arco viene registrata sul nodo v
                        queue.decreasePriority(v, w.getWeight());
                    }
                }
            }
            visitedNodes.add(u);
        }
    }

}
