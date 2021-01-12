/**
 * 
 */
package it.unicam.cs.asdl2021.mp2.Task1;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Implementazione della classe astratta {@code Graph<L>} che realizza un grafo
 * orientato. Non sono accettate etichette dei nodi null e non sono accettate
 * etichette duplicate nei nodi (che in quel caso sono lo stesso nodo).
 * 
 * Per la rappresentazione viene usata una variante della rappresentazione con
 * liste di adiacenza. A differenza della rappresentazione standard si usano
 * strutture dati più efficienti per quanto riguarda la complessità in tempo
 * della ricerca se un nodo è presente (pseudocostante, con tabella hash) e se
 * un arco è presente (pseudocostante, con tabella hash). Lo spazio occupato per
 * la rappresentazione risultà tuttavia più grande di quello che servirebbe con
 * la rappresentazione standard.
 * 
 * Le liste di adiacenza sono rappresentate con una mappa (implementata con
 * tabelle hash) che associa ad ogni nodo del grafo i nodi adiacenti. In questo
 * modo il dominio delle chiavi della mappa è l'insieme dei nodi, su cui è
 * possibile chiamare il metodo contains per testare la presenza o meno di un
 * nodo. Ad ogni chiave della mappa, cioè ad ogni nodo del grafo, non è
 * associata una lista concatenata dei nodi collegati, ma un set di oggetti
 * della classe GraphEdge<L> che rappresentano gli archi uscenti dal nodo: in
 * questo modo la rappresentazione riesce a contenere anche l'eventuale peso
 * dell'arco (memorizzato nell'oggetto della classe GraphEdge<L>). Per
 * controllare se un arco è presenta basta richiamare il metodo contains in
 * questo set. I test di presenza si basano sui metodi equals ridefiniti per
 * nodi e archi nelle classi GraphNode<L> e GraphEdge<L>.
 * 
 * Questa classe non supporta le operazioni di rimozione di nodi e archi e le
 * operazioni indicizzate di ricerca di nodi e archi.
 * 
 * @author Template: Luca Tesei, Implementation: NICCOLO' CUARTAS - niccolo.cuartas@studenti.unicam.it
 *
 * @param <L>
 *                etichette dei nodi del grafo
 */
public class MapAdjacentListDirectedGraph<L> extends Graph<L> {

    /*
     * Le liste di adiacenza sono rappresentate con una mappa. Ogni nodo viene
     * associato con l'insieme degli archi uscenti. Nel caso in cui un nodo non
     * abbia archi uscenti è associato con un insieme vuoto. La variabile
     * istanza è protected solo per scopi di test JUnit.
     */
    protected final Map<GraphNode<L>, Set<GraphEdge<L>>> adjacentLists;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */
    
    /**
     * Crea un grafo vuoto.
     */
    public MapAdjacentListDirectedGraph() {
        // Inizializza la mappa con la mappa vuota
        this.adjacentLists = new HashMap<GraphNode<L>, Set<GraphEdge<L>>>();
    }

    @Override
    public int nodeCount() {
        return this.adjacentLists.keySet().size();
    }

    @Override
    public int edgeCount() {
        if(this.getEdges()==null) return 0;
        int count = 0;
        for (GraphNode<L> keyNode: this.getNodes()) {
            count += this.adjacentLists.get(keyNode).size();
        }
        return count;
    }

    @Override
    public void clear() {
        this.adjacentLists.clear();
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa grafi orientati
        return true;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        return this.adjacentLists.keySet();
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        if(node==null) throw new NullPointerException();
        if(this.containsNode(node))
            return false;

        this.adjacentLists.put(node, new HashSet<GraphEdge<L>>());//aggiunta una nuova "riga" all'hashmap, non ci sono valori associati al nuovo nodo (archi)
        return true;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        if (node == null)
            throw new NullPointerException(
                    "Tentativo di rimuovere un nodo null");
        throw new UnsupportedOperationException(
                "Rimozione dei nodi non supportata");
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        if(node==null) throw new NullPointerException();
        return this.adjacentLists.containsKey(node);
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        if(label==null) throw new NullPointerException("Etichetta nulla");
        Set<GraphNode<L>> setNodes = this.getNodes();
        GraphNode<L> nl = new GraphNode<>(label);
        for(GraphNode<L> node : setNodes)
        {
            if (node.equals(nl))//l'obj da passare a equals è il nodo che ha label
                return node;
        }
        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        if (label == null)
            throw new NullPointerException(
                    "Tentativo di ricercare un nodo con etichetta null");
        throw new UnsupportedOperationException(
                "Ricerca dei nodi con indice non supportata");
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
        throw new UnsupportedOperationException(
                "Ricerca dei nodi con indice non supportata");
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        if(node==null) throw new NullPointerException();
        if(!this.containsNode(node)) throw new IllegalArgumentException();
        //Set<GraphEdge<L>> setEdges = this.adjacentLists.get(node); //inserisco in un set tutti gli archi (coppie di nodi che contengono il nodo richiesto) deprecated
        Set<GraphEdge<L>> setEdges = this.getEdgesOf(node); //inserisco in un set tutti gli archi (coppie di nodi che contengono il nodo richiesto)
        Set<GraphNode<L>> setNodes = new HashSet<>(); //lista dei nodi selezionati che verrà restituita, verranno selezionati di seguito
        for (GraphEdge<L> edge : setEdges) {//seleziono i nodi diversi da quello passato
            setNodes.add(edge.getNode2()); //nello specifico questo grafo è orientato, e come specificato su GraphEdge il nodo 2 è il nodo destinazione diverso dal nodo passato
        }

        return setNodes;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        if(!isDirected()) throw new UnsupportedOperationException("Il grafo non è orientato");
        if(!this.containsNode(node)) throw new IllegalArgumentException("Il nodo non esiste");
        if(node==null) throw new NullPointerException("Il nodo passato è nullo");

        Set<GraphNode<L>> setKeyNodes = this.getNodes(); //lista di tutti i nodi della mappa che andranno analizzati
        Set<GraphNode<L>> predNodes = new HashSet<>();//lista (vuota inizialmente) dei nodi che soddisferanno la richiesta (i predecessori del nodo passato)
        for(GraphNode<L> keyNode : setKeyNodes)//scorro la lista dei nodi di tutta la mappa
        {
            Set<GraphEdge<L>> setEdges = this.getEdgesOf(keyNode); //inserisco in un set tutti gli archi (coppie di nodi che contengono il nodo corrente)
            for(GraphEdge<L> edge : setEdges)
            {
                if(edge.getNode2().equals(node))//se il nodo passato è presente nel node2 dell'edge analizzato,
                    predNodes.add(edge.getNode1());// allora node1 sarà sicuramente il predecessore e deve essere aggiunto a una set (ricordiamo che il grafo in questione è orientato)
            }
        }
        return predNodes;
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        Set<GraphNode<L>> setKeyNodes = this.getNodes();
        Set<GraphEdge<L>> setEdges = new HashSet<>();

        for(GraphNode<L> keyNode : setKeyNodes)
        {
            setEdges.addAll(this.getEdgesOf(keyNode));
        }
        return setEdges;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        if(edge==null) throw new NullPointerException();
        if(!this.containsNode(edge.getNode1()) || !this.containsNode(edge.getNode2()) || (this.isDirected() && !edge.isDirected()) || (!this.isDirected() && edge.isDirected())) throw new IllegalArgumentException();

        return this.getEdgesOf(edge.getNode1()).add(edge);
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        throw new UnsupportedOperationException(
                "Rimozione degli archi non supportata");
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        if(edge==null) throw new NullPointerException();
        if(!this.containsNode(edge.getNode1()) || !this.containsNode(edge.getNode2())) throw new IllegalArgumentException();
        return this.getEdges().contains(edge);//funzionano entrambi i modi

        /*Set<GraphEdge<L>> edges = this.getEdgesOf(edge.getNode1());
        for(GraphEdge<L> e : edges)
        {
            if(e.getNode2().equals(edge.getNode2()))
                return true;
        }
        return false;*/
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        if(node==null) throw new NullPointerException("Nodo nullo");
        if(!this.containsNode(node)) throw new IllegalArgumentException("Nodo non esiste");
        return this.adjacentLists.get(node);
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        if(!isDirected()) throw new UnsupportedOperationException("Grafo non orientato");
        if(!this.containsNode(node)) throw new IllegalArgumentException("Nodo non esiste");
        if(node==null) throw new NullPointerException("Nodo nullo");
        Set<GraphEdge<L>> edgeSet = this.getEdges();
        Set<GraphEdge<L>> ingEdges = new HashSet<>();
        for(GraphEdge<L> edge : edgeSet)
        {
            if (edge.getNode2().equals(node))
                ingEdges.add(edge);
        }
        return ingEdges;
    }

}
