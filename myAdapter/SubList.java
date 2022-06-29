package myAdapter;


/**
 * SubList e' una classe che permette di operare in un intervallo di una lista tra i valori
 * {@code from}, incluso, e {@code to}, escluso.
 * (se {@code fromIndex} e {@code toIndex} sono uguali la lista e' vuota).
 * Questa sublist e' supportata da una lista genitore di cui e' sottoclasse,
 * percio' qualsiasi modifica non strutturale eseguita sulla SubList si riflette sulla lista a viceversa.
 *<p>
 *
 * I costruttori non lanciano errori perche' il controllo degli indici passati come parametro ai costruttori
 * avviene all'interno del metodo subList()
 * <p>
 *
 *
 * <p>Questa classe permette di operare in un intervallo di una lista senza utilizzare metodi che indichino in modo esplicito il range sul quale operare.
 * Ogni metodo che richiede una lista puo' operare in un suo intervallo passando una SubList (quindi una porzione di essa) invece che la lista completa
 * <p>
 *
 * Per non essere ridondante i metodi che non presentano nessuna differenza significativa da ListAdapter non verranno documentati
 * <p>
 *
 * @author Filippo Galli
 *
 */
public class SubList extends ListAdapter {

    private final HList parent;

    /**
     * Costruttore di SubList che riceve come parametro una HList
     * @param parent lista genitore di cui SubList e sottoinsieme
     * @param fromIndex indice bordo sinistro di parent
     * @param toIndex indice bordo destro di parent
     */
    public SubList(HList parent, int fromIndex, int toIndex)
    {
        this.parent = parent;
        this.from = fromIndex;
        this.to = toIndex;
        this.isFather = false;
    }

    /**
     * Costruttore di SubList che riceve come parametro un'altra SubList
     * @param subParent lista genitore di tipo SubList di cui this.SubList e sottoinsieme
     * @param fromIndex indice bordo sinistro di parent
     * @param toIndex indice bordo destro di parent
     */
    public SubList(SubList subParent, int fromIndex, int toIndex) {
        this.parent = subParent.parent;
        this.from = subParent.from + fromIndex;
        this.to = toIndex;
        this.isFather = false;
    }

    /**
     * Ritorna il numero di elementi della sublist
     *
     * @return il numero di elementi della sublist.
     */
    public int size() {
        return to - from;
    }

    /**
     * Ritorna true se la sublist non contiene elementi.
     *
     * @return true se la sublist non contiene elementi.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     * Ritorna l'elemento in una specifica posizione della sublist
     *
     * @param index indice dell'elemento da ritornare.
     * @return l'elemento in una specifica posizione della sublist.
     *
     * @throws IndexOutOfBoundsException se l'indice e' al di fuori della sublist (index &lt; 0
     *                                   || index &gt;= this.size()).
     */
    public Object get(int index) {
        if (this.size() <= index || index<0)
        {
            throw new IndexOutOfBoundsException();
        }

        return parent.get(index + from);

    }

    /**
     * Rimuove tutti gli elementi della sublist (optional operation).
     * Alla fine della chiamata del metodo la subList e di conseguenza anche la parte della lista "padre" a cui essa e' associata saranno vuote
     */
    public void clear()
    {
        Object[] arr = this.toArray();

        for (int i = 0; i < arr.length; i++) {
            while (this.contains(arr[i]))
            {
                this.remove(arr[i]);
            }
        }
    }

    /**
     * Ritorna la prima ricorrenza di uno specifico elemento passato come parametro.
     * Se l'elemento non e' presente nella SubList ritorna -1.
     *
     * @param obj elemento da cercare.
     * @return Indice della SubList in cui si trova la prima occorrenza dell'elemento.
     * Se l'elemento non e' presente -1
     *
     */
    public int indexOf(Object obj)
    {
        for(int i = 0; i<this.size(); i++)
        {
            if (parent.get(i+from).equals(obj))
            {
                return (i);
            }
        }
        return -1;
    }

    /**
     * Ritorna l'ultima ricorrenza di uno specifico elemento passato come parametro.
     * Se l'elemento non e' presente nella SubList ritorna -1.
     *
     * @param obj elemento da cercare.
     * @return Indice della SubList in cui si trova la prima occorrenza dell'elemento.
     * Se l'elemento non e' presente -1
     *
     */
    public int lastIndexOf(Object obj)
    {
        Object[] arr = this.toArray();
        for(int i = arr.length-1; 0<i; i--)
        {
            if (arr[i] == obj)
            {
                return i+from;
            }
        }
        return -1;
    }


    /**
     * Aggiunge l'elemento indicato alla fine della SubList.
     * Alla fine della chiamata del metodo la subList e di conseguenza anche la parte della lista "padre" a cui essa e' associata conterranno l'elemento
     *
     * @param elem elemento da aggiungere
     * @return true se l'aggiunta avviene con successo
     */
    public boolean add(Object elem) {
        parent.add(to++, elem);
        return true;
    }


    /**
     * Rimuove l'elemento che si trova nell'indice passato come parametro
     * Alla fine della chiamata del metodo la subList e di conseguenza anche la parte della lista "padre" a cui essa e' associata conterranno l'elemento
     *
     * @param index indice elemento da eliminare
     * @return l'oggetto rimosso
     * @throws IndexOutOfBoundsException se l'indice e' al di fuori della SubList
     */
    public Object remove(int index)
    {
        try {
            to--;
            return this.parent.remove(index+from);

        }
        catch (IndexOutOfBoundsException e)
        {
            to++;
            throw new IndexOutOfBoundsException();
        }
    }


    /**
     * Rimuove l'elemento indicato.
     * Alla fine della chiamata del metodo la subList e di conseguenza anche la parte della lista "padre" a cui essa e' associata non conterranno l'elemento rimosso
     *
     * @param obj elemento da rimuovere
     * @return true se la rimozione avviene con successo
     */
    public boolean remove(Object obj)
    {
        if (this.contains(obj))
        {
            //CERCA INDICE NELLA SUBLIST
            int index = this.indexOf(obj);
            parent.remove(index+from);
            to--;
            return true;
        }
        return false;
    }

    /**
     *
     * ritorna true se la SubList contiene l'elemento indicato
     *
     * @param object elemento che si controlla sia contenuto nella SubList
     * @return true se l'elemento e' contenuto nella SubList
     */
    public boolean contains (Object object)
    {
        for (int i=from;i<to;i++)
        {
            if (parent.get(i).equals(object))
            {
                return true;
            }
        }
        return false;
    }


    /**
     *
     * sostituisce l'elemento che si trova nella posizione index della sublist con l'elemento element
     *
     * @param index indice dell'elemento di sublist da sostituire
     * @param element elemento da inserire nell'indice index
     * @return l'elemento precedentemente presente nella posizione index della SubList
     * @throws IndexOutOfBoundsException se l'indice e' al di fuori della SubList
     */
    public Object set(int index, Object element) {
        if (this.size() <= index || index<0)
        {
            throw new IndexOutOfBoundsException();
        }
        return parent.set(index+from,element);
    }



}
