package it.unicam.cs.asdl2021.mp2.Task3;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Class that provides an implementation of a "dynamic" min-priority queue based
 * on a ternary heap. "Dynamic" means that the priority of an element already
 * present in the queue may be decreased, so possibly this element may become
 * the new minumum element. The elements that can be inserted may be of any
 * class implementing the interface <code>PriorityQueueElement</code>. This
 * min-priority queue does not have capacity restrictions, i.e., it is always
 * possible to insert new elements and the number of elements is unbound.
 * Duplicated elements are permitted while <code>null</code> elements are not
 * permitted.
 *
 * @author Template: Luca Tesei, Implementation: Niccolò Cuartas - niccolo.cuartas@studenti.unicam.it
 *
 */
public class TernaryHeapMinPriorityQueue {

    /*
     * ArrayList for representing the ternary heap. Use all positions, including
     * position 0 (the JUnit tests will assume so). You have to adapt
     * child/parent indexing formulas consequently.
     */
    private ArrayList<PriorityQueueElement> heap;

    /**
     * Create an empty queue.
     */
    public TernaryHeapMinPriorityQueue() {
        this.heap = new ArrayList<PriorityQueueElement>();
    }


    //find leaves/parents
    private int leftIndex(int h){
        return (3 * h) + 1;
    }

    private int centralIndex(int h){
        return (3 * h) + 2;
    }

    private int rightIndex(int h){
        return (3 * h) + 3;
    }

    private int getParent(int h){
        return (h - 1) / 3;
    }

    /**
     * Determina se lo heap è vuoto.
     *
     * @return true se lo heap è vuoto.
     */
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    /**
     * Return the current size of this queue.
     *
     * @return the number of elements currently in this queue.
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * Add an element to this min-priority queue. The current priority
     * associated with the element will be used to place it in the correct
     * position in the ternary heap. The handle of the element will also be set
     * accordingly.
     *
     * @param element
     *                    the new element to add
     * @throws NullPointerException
     *                                  if the element passed is null
     */
    public void insert(PriorityQueueElement element) {
        if(element==null)
            throw new NullPointerException();
        heap.add(element);
        element.setHandle(size()-1);
        int handleElement=element.getHandle();
        while(handleElement>0)//risalgo l'albero
        {
            if(Double.compare(heap.get(handleElement).getPriority(),heap.get(getParent(handleElement)).getPriority())<0)//se handle element è minore del suo parent handle element, li scambio
                swap(handleElement,getParent(handleElement));
            handleElement=getParent(handleElement);
        }
    }

    /**
     * Returns the current minimum element of this min-priority queue without
     * extracting it. This operation does not affect the ternary heap.
     *
     * @return the current minimum element of this min-priority queue
     *
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement minimum() {
        if(heap.isEmpty())
            throw new NoSuchElementException();
        return heap.get(0);
    }

    /**
     * Extract the current minimum element from this min-priority queue. The
     * ternary heap will be updated accordingly.
     *
     * @return the current minimum element
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement extractMinimum() {
        if(isEmpty())
            throw new NoSuchElementException();
        PriorityQueueElement min=minimum();//salvo il nodo da restituire
        if(heap.size()==1)
        {
            heap.clear();
            return min;
        }
        swap(0,heap.size()-1);//scambio il primo elemento(root=0=minumum()) con ultimo infondo
        heap.remove(size()-1);//cancello l'ultimo
        heapify(0);//e chiamo heapify() sull'ultimo, dato che deve essere rioprdinato
        min.setHandle(0);
        return min;
    }

    private void swap ( int root, int index){
        PriorityQueueElement iElement=heap.get(index);
        int iIndex=heap.indexOf(iElement);
        PriorityQueueElement rElement=heap.get(root);
        int rIndex=heap.indexOf(rElement);

        iElement.setHandle(rIndex);
        rElement.setHandle(iIndex);

        heap.set(root,iElement);
        heap.set(index,rElement);


    }
    /**
     * Decrease the priority associated to an element of this min-priority
     * queue. The position of the element in the ternary heap must be changed
     * accordingly. The changed element may become the minimum element. The
     * handle of the element will also be changed accordingly.
     *
     * @param element
     *                        the element whose priority will be decreased, it
     *                        must currently be inside this min-priority queue
     * @param newPriority
     *                        the new priority to assign to the element
     *
     * @throws NoSuchElementException
     *                                      if the element is not currently
     *                                      present in this min-priority queue
     * @throws IllegalArgumentException
     *                                      if the specified newPriority is not
     *                                      strictly less than the current
     *                                      priority of the element
     */
    public void decreasePriority(PriorityQueueElement element,
                                 double newPriority) {
        if(element==null ||element.getHandle() < 0 || element.getHandle() > size())
            throw new NoSuchElementException();
        if(element.getPriority()<newPriority)
            throw new IllegalArgumentException();

        element.setPriority(newPriority);
        heapify(getParent(element.getHandle()));
    }

    private void heapify(int h){
        int min = h;
        int left = leftIndex(h);
        int right = rightIndex(h);
        int center = centralIndex(h);

        // se elemento di left index è minore del root
        if (left < heap.size() && Double.compare(heap.get(left).getPriority(),(heap.get(min).getPriority()))<0)
            min = leftIndex(h);//lo metto su min


        if (right < heap.size() && Double.compare(heap.get(right).getPriority(),(heap.get(min).getPriority()))<0)
            min = rightIndex(h);//metto su min


        if (center < heap.size() && Double.compare(heap.get(center).getPriority(),(heap.get(min).getPriority()))<0)
            min = centralIndex(h);


        // se min è diverso da root
        if (min != h) {
            swap(h,min);
            // ricorsione
            heapify(min);
        }
    }

    /**
     * Erase all the elements from this min-priority queue. After this operation
     * this min-priority queue is empty.
     */
    public void clear() {
        this.heap.clear();
    }

    /*
     * This method is only for JUnit testing purposes.
     */
    protected ArrayList<PriorityQueueElement> getTernaryHeap() {
        return this.heap;
    }

}
