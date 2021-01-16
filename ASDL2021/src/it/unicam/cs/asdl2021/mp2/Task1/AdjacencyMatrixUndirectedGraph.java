/**
 * 
 */
package it.unicam.cs.asdl2021.mp2.Task1;

import javax.swing.*;
import java.util.*;

/**
 * Classe che implementa un grafo non orientato tramite matrice di adiacenza.
 * Non sono accettate etichette dei nodi null e non sono accettate etichette
 * duplicate nei nodi (che in quel caso sono lo stesso nodo).
 * 
 * I nodi sono indicizzati da 0 a nodeCount() - 1 seguendo l'ordine del loro
 * inserimento (0 è l'indice del primo nodo inserito, 1 del secondo e così via)
 * e quindi in ogni istante la matrice di adiacenza ha dimensione nodeCount() *
 * nodeCount(). La matrice, sempre quadrata, deve quindi aumentare di dimensione
 * ad ogni inserimento di un nodo. Per questo non è rappresentata tramite array
 * ma tramite ArrayList.
 * 
 * Gli oggetti GraphNode<L>, cioè i nodi, sono memorizzati in una mappa che
 * associa ad ogni nodo l'indice assegnato in fase di inserimento. Il dominio
 * della mappa rappresenta quindi l'insieme dei nodi.
 * 
 * Gli archi sono memorizzati nella matrice di adiacenza. A differenza della
 * rappresentazione standard con matrice di adiacenza, la posizione i,j della
 * matrice non contiene un flag di presenza, ma è null se i nodi i e j non sono
 * collegati da un arco e contiene un oggetto della classe GraphEdge<L> se lo
 * sono. Tale oggetto rappresenta l'arco. Un oggetto uguale (secondo equals) e
 * con lo stesso peso (se gli archi sono pesati) deve essere presente nella
 * posizione j, i della matrice.
 * 
 * Questa classe non supporta i metodi di cancellazione di nodi e archi, ma
 * supporta tutti i metodi che usano indici, utilizzando l'indice assegnato a
 * ogni nodo in fase di inserimento.
 * 
 * @author Template: Luca Tesei, Implementation: NICCOLO' CUARTAS - niccolo.cuartas@studenti.unicam.it
 *
 */
public class AdjacencyMatrixUndirectedGraph<L> extends Graph<L> {
    /*
     * Le seguenti variabili istanza sono protected al solo scopo di agevolare
     * il JUnit testing
     */

    // Insieme dei nodi e associazione di ogni nodo con il proprio indice nella
    // matrice di adiacenza
    protected Map<GraphNode<L>, Integer> nodesIndex;

    // Matrice di adiacenza, gli elementi sono null o oggetti della classe
    // GraphEdge<L>. L'uso di ArrayList permette alla matrice di aumentare di
    // dimensione gradualmente ad ogni inserimento di un nuovo nodo.
    protected ArrayList<ArrayList<GraphEdge<L>>> matrix;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */
    //private Integer index;
    /**
     * Crea un grafo vuoto.
     */
    public AdjacencyMatrixUndirectedGraph() {
        this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
        this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
        //index = 0;
    }

    @Override
    public int nodeCount() {
        return this.nodesIndex.size();
    }

    @Override
    public int edgeCount() {
        if(this.getEdges()==null) return 0;
        int count=0;
        for (ArrayList<GraphEdge<L>> edgeList: matrix) {
            for(GraphEdge<L> edge : edgeList) {
                if (edge!=null) count++;
            }
        }
        return count;
    }


    @Override
    public void clear() {
        this.matrix.clear();// = new ArrayList<ArrayList<GraphEdge<L>>>();
        this.nodesIndex.clear(); //= new HashMap<GraphNode<L>, Integer>();
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa un grafo non orientato
        return false;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        return new HashSet<>(nodesIndex.keySet());
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        if (node==null) throw new NullPointerException();
        if (this.containsNode(node)) return false;

        nodesIndex.put(node, nodeCount());
        matrix.add(new ArrayList<GraphEdge<L>>());
        for(int k=0; k<this.nodeCount();k++)
        {
            matrix.get(nodeCount()-1).add(null);
        }


        for(ArrayList<GraphEdge<L>> indexes : matrix)//compenso le altre liste dalla matrice
        {
            /*for (int i = indexes.size(); i < this.nodeCount()-1; i++) {//riempio la riga di valori vuoti al momento dell'inserimento del nuovo nodo
                matrix.get(i).add(null);
            }*/
            if(matrix.get(matrix.indexOf(indexes)).size() < matrix.size())
            {
                for(int i=matrix.get(matrix.indexOf(indexes)).size();i< matrix.size();i++)
                {
                    matrix.get(matrix.indexOf(indexes)).add(null);
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Remove di nodi non supportata");
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        if(node==null) throw new NullPointerException();
        return this.nodesIndex.containsKey(node);
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
            throw new NullPointerException("Tentativo di ricercare un nodo con etichetta null");
//TODO
        //System.out.println(nodesIndex.keySet());

        return nodesIndex.get(this.getNodeOf(label));
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
        if(i<0 || i>(this.nodeCount() - 1)) throw new IndexOutOfBoundsException("Indice non valido");

        int index=0;
        for(GraphNode<L> node : getNodes())
        {
            if(i==index)
                return node;
            else i++;
        }
        return null;
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        if(node==null) throw new NullPointerException();
        if(!this.containsNode(node)) throw new IllegalArgumentException();
        Set<GraphEdge<L>> setEdges = this.getEdgesOf(node); //inserisco in un set tutti gli archi (coppie di nodi che contengono il nodo richiesto)
        Set<GraphNode<L>> setNodes = new HashSet<>(); //lista dei nodi selezionati da ritornare, verranno selezionati di seguito
        for (GraphEdge<L> edge : setEdges) {//ciclo tutti gli archi contenenti il nodo in questione
            if (edge.getNode1().equals(node) || isDirected())//se il primo nodo dell'arco è uguale al nodo passato, non deve essere aggiunto OPPURE il grafo è orientato, ||isDirected() è omissibile
                setNodes.add(edge.getNode2());
            else
                setNodes.add(edge.getNode1());//altrimenti viene aggiunto il primo (che sarà sicuramente diverso dal nodo passato)
        }
        return setNodes;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        if(this.matrix==null) return null;
        Set<GraphEdge<L>> setEdges = new HashSet<>();

        for (ArrayList<GraphEdge<L>> edgeList : matrix) {
            setEdges.addAll(edgeList);
        }
        setEdges.remove(null);
        return setEdges;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {//due grafi NON ORIENTATI sono uguali secondo EQUALS se sono non orientati, e devono essere entrambi aggiunti
        if(edge==null) throw new NullPointerException("Nodo passato è nullo");
        if(this.isDirected() || !this.containsNode(edge.getNode1()) || !this.containsNode(edge.getNode2()) || edge.isDirected()) throw new IllegalArgumentException();
        if(this.containsEdge(edge)) return false;

        int indexNode1 = this.getNodeIndexOf(edge.getNode1().getLabel());
        int indexNode2 = this.getNodeIndexOf(edge.getNode2().getLabel());
        this.matrix.get(indexNode1).set(indexNode2, edge);
        if(!edge.getNode1().equals(edge.getNode2()))//se i nodi collegati sono diversi, vanno aggiunti sia su i,j che su j,i nella matrice
        {
            this.matrix.get(indexNode2).set(indexNode1, edge);
        }
        return true;
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        throw new UnsupportedOperationException(
                "Operazione di remove non supportata in questa classe");
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        return this.getEdges().contains(edge);
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        if(node==null) throw new NullPointerException("Nodo nullo");
        if(!this.containsNode(node)) throw new IllegalArgumentException("Nodo non esiste");

        //return new HashSet<>(matrix.get(nodesIndex.get(node))); //evitato perchè aggiunge un elemento null a indice 0
        Set<GraphEdge<L>> toReturn = new HashSet<>();
        for(GraphEdge<L> edge : this.matrix.get(this.getNodeIndexOf(node.getLabel())))
        {
            if(edge!=null)
                toReturn.add(edge);
        }
        return toReturn;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

}
