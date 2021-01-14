/**
 * 
 */
package it.unicam.cs.asdl2021.mp2.Task2;

import it.unicam.cs.asdl2021.mp2.Task1.GraphEdge;
import it.unicam.cs.asdl2021.mp2.Task1.GraphNode;
import it.unicam.cs.asdl2021.mp2.Task1.Graph;
import it.unicam.cs.asdl2021.mp2.Task1.MapAdjacentListDirectedGraph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * Un oggetto di questa classe singoletto è un attore che trova le componenti
 * fortemente connesse in un grafo orientato che viene passato come parametro.
 * 
 * @author Template: Luca Tesei, Implementation: NICCOLO' CUARTAS - niccolo.cuartas@studenti.unicam.it
 *
 */
public class StronglyConnectedComponentsFinder<L> {

    Deque<GraphNode<L>> stack = new ArrayDeque<>();
    Set<GraphNode<L>> scc;
    Set<Set<GraphNode<L>>> sccSet = new HashSet<>();
    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */

    /**
     * Dato un grafo orientato determina l'insieme di tutte le componenti
     * fortemente connesse dello stesso.
     * 
     * @param g
     *              un grafo orientato
     * @return l'insieme di tutte le componenti fortemente connesse di g dove
     *         ogni componente fortemente connessa è rappresentata dall'insieme
     *         dei nodi che la compongono.
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è orientato
     * @throws NullPointerException
     *                                      se il grafo passato è nullo
     */
    public Set<Set<GraphNode<L>>> findStronglyConnectedComponents(Graph<L> g) {
        if(!g.isDirected()) throw new IllegalArgumentException("Grafo non orientato");
        if(g==null) throw new NullPointerException();//l'ide ritiene non necessario ma rispetto l'api

        //verrà implementato l'algoritmo di Kosaraju
        // chiama un algoritmo DFS sul grafo
        this.DSF(g);
        Graph<L> reversedGraph = this.reverse(g);//inverte il grafo
        this.DFSstack(reversedGraph);//chiama dfs sul grafo invertito, l'ordine di accesso ai nodi è stabilito dallo stack

        return sccSet;
    }

/**
    Metodo per impostare tutti i nodi del grafo bianchi
*/
    private void unvisitNodes(Graph<L> g)
    {
        for(GraphNode<L> u : g.getNodes())
        {
            u.setColor(GraphNode.COLOR_WHITE);
        }
    }

    /**
        Primo DFS che richiama la sua parte ricorsiva per ogni nodo del grafo
     */
    private void DSF(Graph<L> g)
    {
        unvisitNodes(g);

        for(GraphNode<L> u : g.getNodes())
        {
            if(u.getColor()==GraphNode.COLOR_WHITE)
                DSFvisit(g, u);
        }
    }

    /**
     Secondo DFS per il grafo invertito che usa i nodi dello stack (Kosaraju), che chiama la sua parte ricorsiva
     */

    private void DFSstack(Graph<L> g)
    {
        unvisitNodes(g);

        while(!stack.isEmpty())
        {
            GraphNode<L> popped = stack.pop();
            if(popped.getColor()==GraphNode.COLOR_WHITE)
            {
                scc = new HashSet<GraphNode<L>>();
                DSFRecStack(g, popped);
                sccSet.add(scc);//aggiungo gli elementi connessi tra loro al set
            }
        }
    }

    /**
        Ricorsione del primo DFS
     */
    private void DSFvisit(Graph<L> g, GraphNode<L> u) {

        u.setColor(GraphNode.COLOR_BLACK);
        if (!g.getAdjacentNodesOf(u).isEmpty()) {
            for (GraphNode<L> v : g.getAdjacentNodesOf(u))//scorro ogni nodo collegato (adiacente) a quello passato
            {
                if (v.getColor() == GraphNode.COLOR_WHITE)//se il nodo non è visitato
                {
                    DSFvisit(g, v);//visito v che diventa u
                }
            }

        }
        stack.push(u);
        //System.out.println(stack);
    }

    /**
         Ricorsione del DFS di Kosaraju
     */
    private void DSFRecStack(Graph<L> g, GraphNode<L> u)
    {
        //if(u.getColor()==GraphNode.COLOR_BLACK) stack.remove(u);//rimuovo dallo stack il nodo se risulta già visitato
        u.setColor((GraphNode.COLOR_BLACK));//se non è nero lo coloro
        scc.add(u);//viene aggiunto al set che contiene l'insieme dei cfc dei nodi correnti
        if(!g.getAdjacentNodesOf(u).isEmpty())
        {
            for(GraphNode<L> v : g.getAdjacentNodesOf(u))
            {
                if(v.getColor() == GraphNode.COLOR_WHITE) {
                    DSFRecStack(g, v);
                }
            }
        }
    }

    /**
        Metodo che inverte un grafo andando a invertire l'ordine dei suoi archi (scambia i nodi)
        Nel dettaglio crea una copia del grafo originario ma con gli archi invertiti
     */
    private Graph<L> reverse(Graph<L> g)
    {
        Graph<L> reversedGraph = new MapAdjacentListDirectedGraph<>();
        for(GraphNode<L> node : g.getNodes())//popolo la lista di adiacenza
        {
            reversedGraph.addNode(node);
        }
        for(GraphEdge<L> edge : g.getEdges())//dopo aver creato la lista di adiacenza, aggiungo gli archi invertiti
        {
            reversedGraph.addEdge(new GraphEdge<L>(edge.getNode2(), edge.getNode1(), true));
        }
        return reversedGraph;
    }

    }


