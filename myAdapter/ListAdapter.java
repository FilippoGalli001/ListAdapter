/**
 * Classe che implementa {@link myAdapter.HList} e{@link myAdapter.HCollection} utilizzando come adaptee la classe java.util.Vector CLDC1.1
 * <br>
 *
 * @see myAdapter.HList
 * @see myAdapter.HCollection
 */
package myAdapter;

import java.util.NoSuchElementException;

/**
 * ListAdapter e' una classe che implementa tutti i metodi delle classi Collection e List di Java 2 Runtime Environment, J2RE 1.4.2.
 *  Funge da adattatore di quelle interfacce che usano come adaptee la classe java.util.Vector della Java Micro Edition, CLDC1.1.
 *  La classe permette l'inserimento di elementi doppi (ovvero gia' presenti nella lista) ed elementi di tipo null.
 * {<a href="https://docs.oracle.com/javame/config/cldc/ref-impl/cldc1.1/jsr139/index.html">...</a>}
 * <p>
 *
 * La classe implementa le interfacce di {@link HCollection} e {@link HList}
 *
 * <p>
 * La classe possiede una classe interna ListIteratorAdapter, ovvero un iteratore che permette di attraversare la lista in entrambe le direzioni
 * permettendo inoltre di apportare alcune modifiche alla lista
 *
 * <p>
 * Per quanto riguarda la documentazione, se i metodi utilizzati non presentano alcuna differenza dall'interfaccia che ListAdapter implementa,
 * verra' inserita la documentazione dei metodi dell'interfaccia corrispondente
 *
 * @author Filippo Galli
 *
 */
public class ListAdapter implements HList, HCollection {
    protected Vector list;

    /**
     * bordo della lista, necessario per il corretto funzionamento della classe SubList {@link myAdapter.SubList}
     */
    protected int from,to;
    /**
     * indica se la lista e' anche SubList di un oggetto di tipo ListAdapter
     */
    protected boolean isFather;

    /**
     * Costruttore di ListAdapter senza parametri
     */
    public ListAdapter() {
        from = 0;
        to = 0;
        list = new Vector();
        isFather =true;
    }

    /**
     * Costruttore di copia
     *
     * @param coll  HCollection da copiare
     */
    public ListAdapter(HCollection coll) {

        this.from = 0;
        this.to = coll.size();
        this.list = new Vector();
        this.isFather =true;
        Object[] x = coll.toArray();
        for (int i = 0; i<coll.size(); i++)
        {
            this.list.addElement(x[i]);
        }
    }

    /**
     * Costruttore di ListAdapter con parametro di tipo int
     * @param initialSize    - size iniziale della lista
     */
    public ListAdapter(int initialSize)
    {
        this.list = new Vector(initialSize);
        this.from = 0;
        this.to = initialSize;
        this.isFather =true;
    }

    /**
     * Costruttore di ListAdapter con due parametri di tipo int
     *
     * @param initialSize      - size iniziale della lista
     * @param capacityIncrease - capacity della lista
     */
    public ListAdapter(int initialSize, int capacityIncrease)
    {
        this.from = 0;
        this.to = initialSize;
        this.list = new Vector(initialSize, capacityIncrease);
        this.isFather =true;
    }

    /**
     * Returns the number of elements in this list. If this list contains more than
     * Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return the number of elements in this list.
     */
    public int size()
    {
        return this.list.size();
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements.
     */
    public boolean isEmpty()
    {
        return this.list.isEmpty();
    }

    /**
     *
     * Returns true if this list contains the specified element. More formally,
     * returns true if and only if this list contains at least one element e such
     * that (o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e)).
     *
     * @param obj element whose presence in this list is to be tested.
     * @return true if this list contains the specified element.
     */
    public boolean contains(Object obj)
    {
        return list.contains(obj);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence.
     */
    public HIterator iterator()
    {
        return new ListIteratorAdapter();
    }

    /**
     * Returns an array containing all the elements in this list in proper
     * sequence. Obeys the general contract of the Collection.toArray method.
     *
     * @return an array containing all the elements in this list in proper
     *         sequence.
     */
    public Object[] toArray()
    {
        Object[] x = new Object[this.size()];
        for (int i = 0; i<this.size(); i++)
        {
            x[i] = this.get(i);
        }
        return x;
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence. Obeys the general contract of the Collection.toArray(Object[]) method.
     *
     * If this collection fits in the specified array with room to spare (i.e., the array has more elements than this collection),
     * the element in the array immediately following the end of the collection is set to null.
     * This is useful in determining the length of this collection only if the caller knows that this collection does not contain any null elements.)
     * If this collection makes any guarantees as to what order its elements are returned by its iterator, this method must return the elements in the same order.
     *
     * Poiche' siamo in ambiente CLDC1.1 e' impossibile creare un nuovo array dello stesso tipo a runtime. Viene quindi al suo posto creato un array di Object[]
     *
     * @param arrayTarget the array into which the elements of this list are to be
     *                    stored, if it is big enough; otherwise, a new array of the
     *                    same runtime type is allocated for this purpose.
     * @return an array containing the elements of this list.
     *
     * @throws NullPointerException if the specified array is null.
     */
    public Object[] toArray(Object[] arrayTarget)
    {
        if (arrayTarget == null)
        {
            throw new NullPointerException();
        }

        Object[] x = this.toArray();

        if (arrayTarget.length < this.size()) //arrayTarget troppo grande
        {
            return x; //ritorno nuovo array
        }

        for (int i = 0; i<this.size(); i++) //Riempie target fino a svuotare elementi lista
        {
            arrayTarget[i] = x[i];
        }

        for (int i = this.size(); i<arrayTarget.length; i++) //spazio libero che rimane a target settato a null
        {
            arrayTarget[i] = null;
        }

        return arrayTarget;
    }

    // Modification Operations

    /**
     * Appends the specified element to the end of this list (optional operation).
     * <p>
     *
     *
     * @param obj element to be appended to this list.
     * @return true (as per the general contract of the Collection.add method).
     *
     */
    public boolean add(Object obj)
    {
        this.list.addElement(obj);
        return true;
    }

    /**
     * Removes the first occurrence in this list of the specified element (optional
     * operation). If this list does not contain the element, it is unchanged. More
     * formally, removes the element with the lowest index i such that (o==null ?
     * get(i)==null : o.equals(get(i))) (if such an element exists).
     *
     * @param obj element to be removed from this list, if present.
     * @return true if this list contained the specified element.
     */
    public boolean remove(Object obj)
    {
        return this.list.removeElement(obj);
    }

    // Bulk Modification Operations

    /**
     *
     * Returns true if this list contains all of the elements of the specified
     * collection.
     *
     * @param coll collection to be checked for containment in this list.
     * @return true if this list contains all of the elements of the specified
     *         collection.
     * @throws NullPointerException if the specified collection is null.
     * @see #contains(Object)
     */
    public boolean containsAll(HCollection coll)
    {
        if (coll == null)
        {
            throw new NullPointerException();
        }
        Object[] arr = coll.toArray();

        for (int i = 0; i < arr.length; i++) {
            if (!contains(arr[i]))
            {
                return false;
            }
        }

        return true;






    }

    /**
     * Appends all of the elements in the specified collection to the end of this
     * list, in the order that they are returned by the specified collection's
     * iterator (optional operation). The behavior of this operation is unspecified
     * if the specified collection is modified while the operation is in progress.
     * (Note that this will occur if the specified collection is this list, and it's
     * nonempty.)
     *
     * @param coll collection whose elements are to be added to this list.
     * @return true if this list changed as a result of the call.
     *
     * @throws NullPointerException     if the specified collection is null.
     * @see #add(Object)
     */
    public boolean addAll(HCollection coll)
    {
        if (coll == null)
        {
            throw new NullPointerException();
        }

        int n = size();
        HIterator iter = coll.iterator();
        while(iter.hasNext())
        {
            add(iter.next());
        }

        return !(n == size());

    }

    /**
     * Inserts all of the elements in the specified collection into this list at the
     * specified position (optional operation). Shifts the element currently at that
     * position (if any) and any subsequent elements to the right (increases their
     * indices). The new elements will appear in this list in the order that they
     * are returned by the specified collection's iterator. The behavior of this
     * operation is unspecified if the specified collection is modified while the
     * operation is in progress. (Note that this will occur if the specified
     * collection is this list, and it's nonempty.)
     *
     * @param index index at which to insert first element from the specified
     *              collection.
     * @param coll     elements to be inserted into this list.
     * @return true if this list changed as a result of the call.
     *
     * @throws NullPointerException          if
     *                                       the specified collection is null.
     * @throws IndexOutOfBoundsException     if the index is out of range (index
     *                                       &lt; 0 || index &gt; size()).
     */
    public boolean addAll(int index, HCollection coll)
    {
        if (index < 0 || index > this.size())
        {
            throw new IndexOutOfBoundsException();
        }

        int n = this.size();
        HIterator iter = coll.iterator();
        int i = 0;
        while(iter.hasNext())
        {
            add(index+i,iter.next());
            i++;
        }

        return (n != this.size());

    }

    /**
     * Removes from this list all the elements (multiple elements included) that are contained in the specified
     * collection (optional operation).
     *
     * @param coll collection that defines which elements will be removed from this
     *          list.
     * @return true if this list changed as a result of the call.
     *
     * @throws NullPointerException          if the specified collection is null.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(HCollection coll)
    {
        if (coll == null)
        {
            throw new NullPointerException();
        }

        int n = size();
        Object[] arr = coll.toArray();

        for (int i = 0; i < arr.length; i++) {
            while (this.contains(arr[i]))
            {
                remove(arr[i]);
            }
        }

        return !(n == size());
    }

    /**
     * Retains only the elements in this list that are contained in the specified
     * collection (optional operation). In other words, removes from this list all
     * the elements that are not contained in the specified collection.
     *
     * @param coll collection that defines which elements this set will retain.
     *
     * @return true if this list changed as a result of the call.
     *
     * @throws NullPointerException          if the specified collection is null.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean retainAll(HCollection coll)
    {
        if (coll == null)
        {
            throw new NullPointerException();
        }

        int n = size();
        Object[] arr = this.toArray();

        for (int i = 0; i < arr.length; i++) {

            if (!coll.contains(arr[i]))
            {
                remove(arr[i]);
            }
        }

        return !(n == size());
    }

    /**
     * Removes all of the elements from this list (optional operation). This list
     * will be empty after this call returns (unless it throws an exception).
     *
     */
    public void clear()
    {
        list.removeAllElements();
    }

    // Comparison and hashing

    /**
     * Compares the specified object with this list for equality. Returns true if
     * and only if the specified object is also a list, both lists have the same
     * size, and all corresponding pairs of elements in the two lists are
     * <i>equal</i>. (Two elements e1 and e2 are <i>equal</i> if (e1==null ?
     * e2==null : e1.equals(e2)).) In other words, two lists are defined to be equal
     * if they contain the same elements in the same order. This definition ensures
     * that the equals method works properly across different implementations of the
     * List interface.
     *
     * @param obj the object to be compared for equality with this list.
     * @return true if the specified object is equal to this list.
     */
    public boolean equals(Object obj)
    {
        try
        {
            return (this.hashCode() == obj.hashCode());

        }
        catch(NullPointerException e)
        {
            return false;
        }

    }

    /**
     * Returns the hash code value for this list. The hash code of a list is defined
     * to be the result of the following calculation:
     *
     * <pre>
     * hashCode = 1;
     * Iterator i = list.iterator();
     * while (i.hasNext()) {
     *     Object obj = i.next();
     *     hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
     * }
     * </pre>
     *
     * This ensures that list1.equals(list2) implies that
     * list1.hashCode()==list2.hashCode() for any two lists, list1 and list2, as
     * required by the general contract of Object.hashCode.
     *
     * @return the hash code value for this list.
     * @see Object#hashCode()
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    public int hashCode()
    {
        int hash = 1;
        HIterator i = this.iterator();
        while (i.hasNext())
        {
            Object obj = i.next();
            hash = 31 * hash + (obj == null ? 0 : obj.hashCode());
        }

        return hash;
    }

    // Positional Access Operations

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of element to return.
     * @return the element at the specified position in this list.
     *
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
     *                                   || index &gt;= size()).
     */
    public Object get(int index)
    {
        try {
            return list.elementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new IndexOutOfBoundsException();
        }

    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element (optional operation).
     *
     * @param index   index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     *
     * @throws IndexOutOfBoundsException     if the index is out of range (index
     *                                       &lt; 0 || index &gt;= size()).
     */
    public Object set(int index, Object element)
    {
        if (index < 0 || index > this.size())
        {
            throw new IndexOutOfBoundsException();
        }

        Object obj = this.get(index);
       list.setElementAt(element, index);
        return obj;
    }

    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation). Shifts the element currently at that position (if any)
     * and any subsequent elements to the right (adds one to their indices).
     *
     * @param index   index at which the specified element is to be inserted.
     * @param element element to be inserted.
     *
     * @throws IndexOutOfBoundsException     if the index is out of range (index
     *                                       &lt; 0 || index &gt; size()).
     */
    public void add(int index, Object element)
    {
        try {
            list.insertElementAt(element, index);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Removes the element at the specified position in this list (optional
     * operation). Shifts any subsequent elements to the left (subtracts one from
     * their indices). Returns the element that was removed from the list.
     *
     * @param index the index of the element to removed.
     * @return the element previously at the specified position.
     *
     * @throws IndexOutOfBoundsException     if the index is out of range (index
     *                                       &lt; 0 || index &gt;= size()).
     */
    public Object remove(int index)
    {
        try {
            Object elem = list.elementAt(index);
            list.removeElementAt(index);
            return elem;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new IndexOutOfBoundsException();
        }
    }

    // Search Operations

    /**
     * Returns the index in this list of the first occurrence of the specified
     * element, or -1 if this list does not contain this element. More formally,
     * returns the lowest index i such that (o==null ? get(i)==null :
     * o.equals(get(i))), or -1 if there is no such index.
     *
     * @param obj element to search for.
     * @return the index in this list of the first occurrence of the specified
     *         element, or -1 if this list does not contain this element.
     */
    public int indexOf(Object obj)
    {
        return list.indexOf(obj);
    }

    /**
     * Returns the index in this list of the last occurrence of the specified
     * element, or -1 if this list does not contain this element. More formally,
     * returns the highest index i such that (o==null ? get(i)==null :
     * o.equals(get(i))), or -1 if there is no such index.
     *
     * @param obj element to search for.
     * @return the index in this list of the last occurrence of the specified
     *         element, or -1 if this list does not contain this element.
     */
    public int lastIndexOf(Object obj)
    {
        return list.lastIndexOf(obj);
    }

    // List Iterators

    /**
     * Returns a list iterator of the elements in this list (in proper sequence).
     *
     * @return a list iterator of the elements in this list (in proper sequence).
     */
    public HListIterator listIterator()
    {
        return new ListIteratorAdapter();
    }

    /**
     * Returns a list iterator of the elements in this list (in proper sequence),
     * starting at the specified position in this list. The specified index
     * indicates the first element that would be returned by an initial call to the
     * next method. An initial call to the previous method would return the element
     * with the specified index minus one.
     *
     * @param index index of first element to be returned from the list iterator (by
     *              a call to the next method).
     * @return a list iterator of the elements in this list (in proper sequence),
     *         starting at the specified position in this list.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
     *                                   || index &gt; size()).
     */
    public HListIterator listIterator(int index)
    {
        if (index < 0 || index > this.size())
        {
            throw new IndexOutOfBoundsException();
        }
        return new ListIteratorAdapter(index);
    }

    // View

    /**
     * Returns a view of the portion of this list between the specified fromIndex,
     * inclusive, and toIndex, exclusive. (If fromIndex and toIndex are equal, the
     * returned list is empty.) The returned list is backed by this list, so
     * non-structural changes in the returned list are reflected in this list, and
     * vice-versa. The returned list supports all of the optional list operations
     * supported by this list.
     * <p>
     *
     * This method eliminates the need for explicit range operations (of the sort
     * that commonly exist for arrays). Any operation that expects a list can be
     * used as a range operation by passing a subList view instead of a whole list.
     * For example, the following idiom removes a range of elements from a list:
     *
     * <pre>
     * list.subList(from, to).clear();
     * </pre>
     *
     * Similar idioms may be constructed for indexOf and lastIndexOf, and all of the
     * algorithms in the Collections class can be applied to a subList.
     * <p>
     *
     * The semantics of the list returned by this method become undefined if the
     * backing list (i.e., this list) is <i>structurally modified</i> in any way
     * other than via the returned list. (Structural modifications are those that
     * change the size of this list, or otherwise perturb it in such a fashion that
     * iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList.
     * @param toIndex   high endpoint (exclusive) of the subList.
     * @return new SubList(this,fromIndex,toIndex) a view of the specified range within this list.
     *
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *                                   (fromIndex &lt; 0 || toIndex &gt; size ||
     *                                   fromIndex &gt; toIndex).
     */
    public HList subList(int fromIndex, int toIndex)
    {
        if (fromIndex < 0 || size() < toIndex || toIndex < fromIndex)
        {
            throw new IndexOutOfBoundsException();
        }
        return new SubList(this,fromIndex,toIndex);

    }


    /**
     *
     * Iteratore di ListAdapter che permette di attraversare la lista in entrambe le direzioni,
     * apportare modifiche e richiedere la posizione dell'iteratore nella lista.
     * <p>
     *
     * L'iteratore e' classe interna di ListAdapter e per questo motivo puo' accedere a tutte le sue variabili di tipo protected
     * <p>
     *
     * la classe presenta un cursore che si trova sempre tra l'elemento che sarebbe ritornato da una chiamata di
     * previous() e l'elemento che sarebbe ritornato da una chiamata di next().
     * In una lista di n elementi l'iteratore puo' trovarsi in n+1 posizioni da 0 (prima del primo elemento) a n (dopo l'ultimo elemento)
     *
     * <P>
     * Si noti che i metodi{@link #remove} e {@link #set(Object)} operano non in base al cursore ma all'ultimo elemento
     * ritornato dai metodi {@link #next} o {@link #previous()}.
     * <P>
     *
     * @see HCollection
     * @see HList
     * @see HIterator
     * @see HListIterator
     */

    public class ListIteratorAdapter implements HIterator, HListIterator
    {
        /**
         * cursore dell'iteratore che si trova sempre tra l'elemento che sarebbe ritornato da una chiamata di
         * previous() e l'elemento che sarebbe ritornato da una chiamata di next()
         */
        private int cursor;

        /**
         * l'ultima azione di HListIterator ha rimosso un elemento della HList
         */
        private boolean removeDone;

        /**
         * l'ultima azione di HListIterator ha aggiunto un elemento della HList
         */
        private boolean addDone;

        /**
         * HListIterator ha appena eseguito un next nella HList
         */
        private boolean nextDone;

        /**
         * HListIterator ha appena eseguito un previous nella HList
         */
        private boolean previousDone;


        /**
         * Costruttore di ListIteratorAdapter con cursore che punta a prima del primo elemento della lista
         */
        public ListIteratorAdapter()
        {
            this.cursor = 0;
            removeDone = false;
            addDone = false;
            nextDone= false;
            previousDone= false;
        }

        /**
         * Costruttore di ListIteratorAdapter con cursore che punta a prima dell'elemento index della lista
         * @param index indice di partenza
         */
        public ListIteratorAdapter(int index)
        {
            this.cursor = index;
            removeDone = false;
            addDone = false;
            nextDone= false;
            previousDone= false;
        }



        /**
         * Returns true if this list iterator has more elements when traversing
         * the list in the forward direction. (In other words, returns true if
         * next would return an element rather than throwing an exception.)
         *
         * @return true if the list iterator has more elements when traversing
         *         the list in the forward direction.
         */
        public boolean hasNext()
        {
            return  this.cursor < ListAdapter.this.size();
        }

        /**
         * Returns the next element in the list. This method may be called repeatedly to
         * iterate through the list, or intermixed with calls to previous to go
         * back and forth. (Note that alternating calls to next and
         * previous will return the same element repeatedly.)
         *
         * @return the next element in the list.
         * @exception NoSuchElementException if the iteration has no next element.
         */
        public Object next()
        {
            if (!this.hasNext())
            {
                throw new NoSuchElementException();
            }
            Object next = ListAdapter.this.get(this.cursor);
            this.cursor++;
            nextDone = true;
            addDone = false;
            removeDone= false;
            previousDone = false;
            return next;
        }

        /**
         * Returns true if this list iterator has more elements when traversing
         * the list in the reverse direction. (In other words, returns true if
         * previous would return an element rather than throwing an exception.)
         *
         * @return true if the list iterator has more elements when traversing
         *         the list in the reverse direction.
         */
        public boolean hasPrevious()
        {
            return this.cursor > 0;
        }

        /**
         * Returns the previous element in the list. This method may be called
         * repeatedly to iterate through the list backwards, or intermixed with calls to
         * next to go back and forth. (Note that alternating calls to
         * next and previous will return the same element repeatedly.)
         *
         * @return the previous element in the list.
         *
         * @exception NoSuchElementException if the iteration has no previous element.
         */
        public Object previous()
        {
            if (!this.hasPrevious())
            {
                throw new NoSuchElementException();
            }
            Object prev = ListAdapter.this.get(cursor - 1);
            this.cursor--;
            previousDone = true;
            addDone = false;
            removeDone= false;
            nextDone = false;
            return prev;
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call
         * to next. (Returns list size if the list iterator is at the end of
         * the list.)
         *
         * @return the index of the element that would be returned by a subsequent call
         *         to next, or list size if list iterator is at end of list.
         */
        public int nextIndex()
        {
            return this.cursor;
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call
         * to previous. (Returns -1 if the list iterator is at the beginning of
         * the list.)
         *
         * @return the index of the element that would be returned by a subsequent call
         *         to previous, or -1 if list iterator is at beginning of list.
         */
        public int previousIndex()
        {
            if (this.cursor > 0)
            {
                return this.cursor - 1;
            }
            return -1;
        }

        // Modification Operations

        /**
         * Removes from the list the last element that was returned by next or
         * previous (optional operation). This call can only be made once per
         * call to next or previous. It can be made only if
         * ListIterator.add has not been called after the last call to
         * next or previous.
         *
         * @exception IllegalStateException         neither next nor
         *                                          previous have been called,
         *                                          or remove or add
         *                                          have been called after the last call
         *                                          to * next or
         *                                          previous.
         */
        public void remove()
        {
            if (removeDone || addDone || !(previousDone || nextDone))
            {
                throw new IllegalStateException();
            }
            if(nextDone)
            {
                this.previous();
                ListAdapter.this.remove(this.cursor);
                previousDone = false;
                nextDone = false;
            }
            if(previousDone)
            {
                ListAdapter.this.remove(this.cursor);
                previousDone = false;
            }
            removeDone = true;
        }

        /**
         * Replaces the last element returned by next or previous with
         * the specified element (optional operation). This call can be made only if
         * neither ListIterator.remove nor ListIterator.add have been
         * called after the last call to next or previous.
         *
         * @param obj the element with which to replace the last element returned by
         *          next or previous.
         *
         * @exception IllegalStateException         if neither next nor
         *                                          previous have been called,
         *                                          or remove or add
         *                                          have been called after the last call
         *                                          to next or
         *                                          previous.
         */
        public void set(Object obj)
        {
            if (removeDone || addDone || !(previousDone || nextDone))
            {
                throw new IllegalStateException();
            }
            if (this.nextDone)
            {
                ListAdapter.this.set(this.cursor - 1, obj);
            }

            if (this.previousDone)
            {
                ListAdapter.this.set(this.cursor, obj);
            }
        }

        /**
         * Inserts the specified element into the list (optional operation). The element
         * is inserted immediately before the next element that would be returned by
         * next, if any, and after the next element that would be returned by
         * previous, if any. (If the list contains no elements, the new element
         * becomes the sole element on the list.) The new element is inserted before the
         * implicit cursor: a subsequent call to next would be unaffected, and
         * a subsequent call to previous would return the new element. (This
         * call increases by one the value that would be returned by a call to
         * nextIndex or previousIndex.)
         *
         * @param obj the element to insert.
         */
        public void add(Object obj)
        {
            ListAdapter.this.add(this.cursor, obj);
            this.cursor++;
            addDone = true;
        }
    }
}
