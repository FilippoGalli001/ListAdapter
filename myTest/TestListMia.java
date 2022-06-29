package myTest;

import myAdapter.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;


/**
 * Classe Test dei metodi di {@link myAdapter.ListAdapter}
 * <br>
 * Summary: Questa classe verifica la correttezza dei metodi di ListAdapter. Viene verificato che tutti i metodi funzionino nel modo progettato e che generino le eccezioni appropriate.
 * La classe ListAdapterTest si occupa del test di tutti i metodi relativi alla HList raccogliendo talvolta piu' metodi in un unico test case quando questi sono logicamente simili
 * Per verificare senza troppi problemi se i metodi testati presentano degli errori la lista conterrà solo oggetti di tipo Integer che, provenendo dalla superclasse Object, non causeranno problemi di compatibilita'
 * <br><br>
 * Test Suite Design: in questa classe viene testato ogni metodo ed eccezione di ListAdapter.
 * <br><br>
 * <br><br>
 * Preconditions:
 * <br>Due nuovi oggetti di tipo ListAdapter che non contengono alcun elemento devono sempre essere inizializzato prima di ogni test insieme a un oggetto sempre di tipo ListAdapter
 * contenente alcuni elementi di tipo Integer
 * <br><br>
 * Postconditions: I metodi implementati devono sempre modificare l'elenco coerentemente al design di ciascun metodo
 * <br><br>
 * Test Suite Execution Records: Ogni metodo testato è corretto se tutti i test che ne verificano il corretto funzionamento danno esito positivo.
 * Il corretto funzionamento di tutti i metodi e' esso stesso l'Execution Records
 * <br><br>
 * Execution variables:
 * <br>HList list - elenco vuoto su cui vengono testati tutti i metodi comuni a HCollection e HList
 * <br>HCollection coll - oggetto di tipo CollectionAdapter utilizzato per testare metodi che accettano parametri di tipo HCollection
 * <br><br>
 *
 * @see myAdapter.HList
 * @see myAdapter.HIterator
 * @see myAdapter.HListIterator
 * @see myAdapter.HCollection
 *
 * NOTA: le versioni di Junit superiori a 4.0 (come quella utilizzata) non supportano le versioni di Java inferiori a 1.5.0
 *
 * @version JUnit 4.13
 * @version Hamcrest: 1.3
 * @author Filippo Galli
 */
public class TestListMia {

    private HList list;
    HCollection coll;

    /**
     * Metodo utilizzato per inizializzare le variabili prima di ogni test
     * <p>
     * Prima di ogni test viene creata una nuova HCollection e HList entrambe vuote
     */
    @Before
    public void initialize() {
        coll = new ListAdapter();
        list = new ListAdapter();

    }

    /**
     * Testa il costruttore di ListAdapter che riceve come parametro una HCollection
     * <p>
     *
     * <br><br>Summary: Test per verificare la corretta creazione di un oggetto di tipo
     * ListAdapter che contiene gli stessi valori della HCollection passata come parametro
     * <br><br>Design test: aggiungiamo una serie di elementi alla HCollection, viene invocato il costruttore di un nuovo oggetto di tipo ListAdapter passando la HCollection come parametro
     * Sono poi comparate le due liste
     * <br><br>Description: Vengono aggiunti due elementi alla HCollection "coll",
     * viene poi definita e inizializzata una nuova lista "newCollection" passando "coll" come parametro
     * Comparo poi le due liste ottenute verificando che contengano gli stessi elementi nello stesso ordine
     * <br><br>Preconditions: "coll" non dev'essere null e i metodi toArray() e add() devono funzionare correttamente
     * <br><br>Postconditions: L'oggetto creato dev'essere uguale a quello che gli e' stato passato come parametro nel costruttore
     * <br><br>Expected results: I due oggetti devono contenere gli stessi elementi nel medesimo ordine
     */
    @Test
    public void testConstructorWithParameter()
    {
        System.out.println("testConstructorWithParameter");
        coll.add(0);
        coll.add(1);

        HCollection newColl = new ListAdapter(coll);

        assertArrayEquals(coll.toArray(), newColl.toArray());
    }

    /**
     * Test of {@link myAdapter.ListAdapter#size()}
     * <p>
     * <br><br>Summary: Il test controlla che il metodo size di ListAdapter funzioni correttamente
     * <br><br>Design test: controlla che la dimensione della lista sia corretta nella situazione iniziale, dopo aver aggiunto un valore e dopo averlo rimosso
     * <br><br>Description: e' invocato il metodo size quando la lista e' vuota, dopo l'aggiunta di un singolo valore attraverso il metodo add() e dopo la rimozione dello stesso attraverso il metodo remove()
     * <br><br>Preconditions: I metodi add() e remove() devono funzionare correttamente
     * <br><br>Postconditions: il valore ritornato da size() dev'essere uguale alla dimensione della lista
     * <br><br>Expected results: la dimensione e' 0 se la lista e' vuota, aggiunto un elemento la dimensione sara' 1 mentre alla rimozione sara' 0
     */
    @Test
    public void testSize()
    {
        System.out.println("testSize");
        assertEquals(0, coll.size());
        coll.add(1);
        assertEquals(1, coll.size());
        coll.remove(1);
        assertEquals(0, coll.size());
    }

    /**
     * Test of {@link myAdapter.ListAdapter#isEmpty()}
     * <p>
     * <br><br>Summary: Il test controlla che il metodo isEmpty funzioni correttamente ritornando true o false a seconda del contenuto della lista
     * <br><br>Design test: viene controllato che la lista sia inizialmente vuota, successivamente si aggiunge e rimuove un elemento e si controlla che la lista sia
     * dopo i due passaggi rispettivamente non vuota e vuota
     * <br><br>Description: Viene prima di tutto verificato che la lista fornita sia vuota, viene poi inserito un elemento e verificato che la lista sia non vuota.
     * Viene infine rimosso l'elemento appena aggiunto e verificato che la lista sia nuovamente vuota
     * <br><br>Preconditions: i metodi add() e remove() devono funzionare correttamente
     * <br><br>Postconditions: il metodo deve ritornare true se la lista non contiene alcun elemento
     * <br><br>Expected results: la lista e' vuota prima dell'inserimento, isEmpty() ritorna false dopo l'inserimento, true alla rimozione
     */
    @Test
    public void testIsEmpty() {
        System.out.println("testIsEmpty");
        assertTrue(coll.isEmpty());
        coll.add(1);
        assertTrue(!coll.isEmpty());
        coll.remove(1);
        assertTrue(coll.isEmpty());
    }

    /**
     * Test of {@link myAdapter.ListAdapter#contains(Object)}
     * <p>
     * <br><br>Summary: Viene controllato che il metodo contains funzioni correttamente ritornando true se l'elemento inserito come parametro e' presente nella lista
     * <br><br>Design test: Viene controllato se degli specifici elementi (in particolare due integer e un null) sono presenti in una lista vuota
     * Vengono poi inseriti i medesimi elementi appena controllati nella lista, viene poi verificato che questi elementi appena aggiunti siano presenti nella lista
     * <br><br>Description: Verifichiamo che due interi e un null non siano presenti nella lista, li aggiungiamo e verifichiamo che il metodo contains ritorni true,
     * e' verificato inoltre che lo stesso metodo ritorni false se l'elemento richiesto non e' presente nella lista.
     * Il metodo non lancia eccezioni
     * <br><br>Preconditions: il metodo add() funziona correttamente
     * <br><br>Postconditions: il metodo contains() ritorna true se l'elemento e' contenuto nella lista. In caso contrario ritorna false;
     * <br><br>Expected results: Ritorna true se il metodo contiene gli elementi da noi inseriti. In ogni altro caso false
     */
    @Test
    public void testContains() {
        System.out.println("testContains");
        assertFalse(coll.contains(1));
        assertFalse(coll.contains(-3));
        assertFalse(coll.contains(null));
        coll.add(1);
        coll.add(-3);
        coll.add(null);
        assertTrue(coll.contains(1));
        assertTrue(coll.contains(null));
        assertFalse(coll.contains(-10));
    }

    /**
     * Test of {@link myAdapter.ListAdapter#toArray()}
     * <p>
     * <br><br>Summary: Il test controlla che il metodo toArray() ritorni un array contenente tutti gli elementi della lista nell'ordine in cui sono stati precedentemente inseriti
     * <br><br>Design test: vengono inseriti i dati all'interno della lista, viene creato un nuovo array contentment gli elementi inseriti e viene verificato che essi coincidano
     * <br><br>Description: dopo aver inserito manualmente tre elementi nella lista creo un nuovo array di Object contenente gli stessi elementi e verifico che essi siano uguali
     * in dimensione e contenuto
     * <br><br>Preconditions: il metodo size() deve funzionare correttamente
     * <br><br>Postconditions: toArray() deve ritornare un array di Object contenente tutti gli elementi della lista
     * <br><br>Expected results: i due array devono essere identici
     */
    @Test
    public void testToArray() {

        System.out.println("testToArray");
        coll.add(1);
        coll.add(2);
        coll.add(3);

        Object[] arr = new Object[]{1, 2, 3};

        assertArrayEquals(arr, coll.toArray());
        assertEquals(arr.length, coll.size());

        for (int i = 0; i<arr.length; i++)
        {
            assertEquals(arr[i], coll.toArray()[i]);
        }
    }

    /**
     * Test of {@link myAdapter.ListAdapter#toArray(Object[])}
     * <p>
     * <br><br>Summary: Il test controlla che il metodo toArray(Object[]) ritorni l'array passato come parametro con al proprio interno gli elementi contenuti nella lista,
     * se l'array passato come parametro puo' contenere meno elementi di quelli contenuti nella lista ritorna un nuovo array di Object.
     * Se l'array fornito e' piu' grande le celle in eccesso conterranno elemento null
     * <br><br>Design Test: La lista e' inizialmente vuota.
     * Si verifica inizialmente che passando come parametro al metodo toArray un array null venga lanciata un'eccezione.
     * Si crea ora un array contenente una serie di elementi
     * Vengono inseriti gli elementi della lista vuota all'interno dell'array ma, poiche' la lista non ha alcun elemento, le celle dell'array fornito come parametro
     * vengono impostate a null.
     * <br>Vengono successivamente inseriti nella lista una serie di elementi e viene verificato che il metodo funzioni come descritto dalla documentazione
     *
     * <br><br>Description: Dopo aver verificato il lancio dell'eccezione l'array viene riempito e si verificano le diverse situazioni all'invocazione del metodo toArray
     * Alla prima invocazione del metodo poiche' la lista e' vuota gli elementi dell'array vengono tutti impostati a null.
     * Nella seconda invocazione vengono inseriti soltanto due elementi e quindi la lista ha ancora gli ultimi due elementi null
     * Nella terza invocazione l'array contiene in ogni sua cella un elemento della lista, nessuna cella conterra' elementi nulli
     * <br><br>Preconditions: Il metodo add deve funzionare correttamente
     * <br><br>Postconditions: L'array ritornato deve contenere tutti gli elementi della lista e, se presenti celle in eccesso, esse devono contenere null
     * <br><br>Expected results: L'array passato come parametro deve contenere tutti gli elementi della lista
     */
    @Test
    public void testToArrayWithParameter() {
        System.out.println("testToArrayWithParameter");
        Object[] arr = null;

        try {
            coll.toArray(arr);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        arr = new Object[]{1, 2, 3, 4};
        assertArrayEquals(new Object[]{null, null, null, null}, coll.toArray(arr));

        coll.add(4);
        coll.add(5);
        assertArrayEquals(new Object[]{4, 5, null, null}, coll.toArray(arr));

        coll.add(6);
        coll.add(7);
        assertArrayEquals(new Object[]{4, 5, 6, 7}, coll.toArray(arr));
    }


    /**
     * Test of {@link myAdapter.ListAdapter#add(Object)}
     * <p>
     * <br><br>Summary: Verifica che il metodo add() inserisca un nuovo elemento passato come parametro nell'ultima posizione disponibile della lista
     * Verifica inoltre che il metodo add(index, element) posizioni l'elemento nell'indice selezionato causando uno shift di tutti gli elementi a destra di quell'indice
     * <br><br>Design test: Vengono aggiunti due elementi alla lista, viene creato un array attraverso il metodo toArray() e si verifica il corretto inserimento degli elementi
     * Viene in seguito svolto lo stesso procedimento decidendo a priori l'indice in cui inserire l'elemento
     * <br><br>Description: Dopo aver inserito i due elementi viene creata un array di Object contenente gli elementi appena inseriti e si verifica che essi si trovino
     * nella giusta posizione, in modo che il primo elemento inserito si trovi nella prima cella e cosi' via.
     * Vengono poi inseriti altri due elementi in un indice prestabilito e si verifica nuovamente tramite il metodo toArray che essi siano nella posizione corretta
     * <br><br>Preconditions: il metodo toArray() deve funzionare correttamente
     * <br><br>Postconditions: La lista deve contenere gli elementi inseriti nella giusta posizione
     * <br><br>Expected results: Gli elementi della lista devono trovarsi nella posizione aspettata
     */
    @Test
    public void testAddWithAndWithoutIndex() {
        System.out.println("testAddWithAndWithoutIndex");
        list.add(1);
        list.add(3);
        Object[] x = list.toArray();
        assertEquals(1, x[0]);
        assertEquals(3, x[1]);

        list.add(0,-1);
        list.add(2,2);
        x = list.toArray();
        assertEquals(-1, x[0]);
        assertEquals(2, x[2]);

    }

    /**
     * Test of {@link myAdapter.ListAdapter#remove(Object)}
     * <p>
     * <br><br>Summary: Verifica che il metodo remove() rimuova, se presente nella lista, l'elemento passato come parametro e ritorni true. Ritorni invece false se non e' presente.
     * Verifica inoltre che, se viene passato come parametro un indice, venga rimosso l'elemento in tale posizione controllando che l'indice non sia al di fuori dei bordi
     * <br><br>Design test:
     * Prima di tutto si verifica che venga lanciata un'eccezione se viene passato come parametro al metodo un indice invalido.
     * <br>Vengono poi aggiunti alla lista una serie di elementi di cui uno presente due volte.
     * <br>Vengono eliminati due elementi presenti nella lista e uno non presente verificando il valore ritornato dal metodo
     * <br>Viene poi controllato che sia stata eliminata soltanto la prima ricorrenza dell'elemento doppio e che la seconda rimozione sia stata svolta correttamente
     * infine viene eliminato l'elemento che si trova nell'indice selezionato e si verifica nuovamente la corretta rimozione
     * <br><br>Description: Vengono aggiunti quattro elementi di cui uno presente due volte in prima e ultima posizione.
     * Vengono eliminati due elementi presenti nella lista e uno non presente. Si controlla la validita' del valore di ritorno
     * Viene creato un array di Object contenente gli elementi rimasti nell'array e confrontato con quanto ci si dovrebbe aspettare a priori.
     * Viene infine verificata la correttezza del metodo remove() passando come parametro un indice valido
     * Poiche' non e' possibile sapere se il parametro intero inserito deve essere considerato di tipo Object o int (e quindi come indice) viene utilizzato un cast esplicito
     * <br><br>Preconditions: i metodi add(), toArray() e size() devono funzionare correttamente
     * <br><br>Postconditions: Ritorna true quando si prova a rimuovere un elemento presente nella lisya. Se si prova a rimuovere un elemento non presente ritorna false
     * <br><br>Expected results: Gli elementi che vogliamo rimuovere dalla lista vengono correttamente rimossi diminuendo la dimensione della lista
     */
    @Test
    public void testRemoveAndRemoveIndex() {
        System.out.println("testRemoveAndRemoveIndex");

        assertFalse(list.remove(new Object()));

        try {
            list.remove(list.size());
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }
        try {
            list.remove(-1);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1);
        assertTrue(list.remove((Object)1));
        assertTrue(list.remove((Object)2));
        assertFalse(list.remove((Object)9));
        assertEquals(2, list.size());
        assertArrayEquals(new Object[]{3,1}, list.toArray());
        list.add(3);
        assertEquals(list.remove((int)1), 1);
        assertEquals(2, list.size());


    }


    /**
     * Test of
     * {@link myAdapter.ListAdapter#containsAll(HCollection)}
     * <p>
     * <br><br>Summary: Verifica che la lista contenga tutti gli elementi di una HCollection passata come parametro
     * <br><br>Design test: Vengono aggiunti una serie di elementi nella lista. Viene poi creata una seconda lista passando al costruttore come parametro la prima lista.
     * Viene di seguito aggiunto alla prima lista un nuovo elemento e si verifica che questa contenga tutti gli elementi della seconda lista.
     * Successivamente viene inserito all'interno della seconda lista un elemento non presente nella prima e si invoca nuovamente il metodo.
     * In quest'ultimo caso la lista principale non contiene l'ultimo elemento inserito nella seconda lista e quindi containsAll ritornera' false.
     *
     * <br><br>Description: dopo aver verificato che passando una lista nulla venga lanciata un eccezione vengono aggiunti degli elementi nella lista.
     * Viene poi creata una seconda lista di test di tipo HCollection passando al costruttore la lista a cui abbiamo precedentemente aggiunto elementi.
     * Le due liste avranno ora gli stessi elementi e il metodo containsAll ritorna di conseguenza true.
     * Aggiungendo infine un nuovo elemento alla lista di test si verifica che questa contiene ora piu' elementi della prima lista ritornando false all'invocazione del metodo
     * <br><br>Preconditions: il costruttore di ListAdapter e il metodo add() devono funzionare correttamente
     * <br><br>Postconditions: la lista principale non contiene tutti gli elementi della lista passata come parametro
     * <br><br>Expected results: Viene ritornato true solo se la lista passata come parametro contiene tutti gli elementi della lista principale e/o e' una sua subList
     */
    @Test
    public void testContainsAll() {
        System.out.println("testContainsAll");
        try {
            coll.containsAll(null);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }


        coll.add(1);
        coll.add(2);
        coll.add(3);

        HCollection testColl = new ListAdapter(coll);

        assertTrue(coll.containsAll(testColl));

        coll.add(4);

        assertTrue(coll.containsAll(testColl));

        testColl.add(5);
        assertFalse(coll.containsAll(testColl));
    }

    /**
     * Test of {@link myAdapter.ListAdapter#addAll(HCollection)}
     * <p>
     * <br><br>Summary: il test verifica che il metodo inserisca all'interno della lista su cui invochiamo il metodo tutti gli elementi
     * contenuti nella HCollection passata come parametro
     *
     * <br><br>Design test: Viene prima di tutto passato come parametro un elemento nullo in modo da verificare il corretto lancio dell'eccezione
     * Viene poi creata una nuova HCollection contenente alcuni elementi che viene poi passata come parametro nel metodo addAll invocato dalla lista
     * Si verifica che siano stati inseriti nella lista tutti gli elementi di HCollection
     *
     * <br><br>Description:
     * Si passa come parametro un elemento nullo in modo da verificare il corretto lancio dell'eccezione
     * successivamente si crea una nuova lista di test in cui vengono inseriti alcuni elementi e si passa quest'ultima come parametro al metodo addAll.
     * Si verifica che la lista su cui viene invocato il metodo addAll ora contiene gli elementi precedentemente contenuti piu' i nuovi elementi della lista di test
     *
     * <br><br>Preconditions: i metodi add(Object) e containsAll(HCollection) devono funzionare correttamente
     * <br><br>Postconditions: Tutti gli elementi della HCollection passata come parametro devono essere contenuti all'interno della lista
     * <br><br>Expected results: La lista dovra' avere al suo interno tutti gli elementi di HCollection, passata come parametro
     */
    @Test
    public void testAddAll() {
        System.out.println("testAddAll");

        HCollection testColl = null;
        try {
            coll.addAll(testColl);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        coll.add(0);

        testColl = new ListAdapter();
        testColl.add(1);
        testColl.add(2);
        testColl.add(3);
        testColl.add(50);

        int collDim = coll.size();
        coll.addAll(testColl);


        Object[] collArr = coll.toArray();
        Object[] testCollArr = testColl.toArray();

        assertTrue(coll.containsAll(testColl));
        assertEquals(collDim + testColl.size(), coll.size());


        for(int i = 0; i<testCollArr.length; i++)
        {
            assertEquals(collArr[i+collDim], testCollArr[i]);
        }
    }


    /**
     * Test of {@link myAdapter.ListAdapter#addAll(int, HCollection)}
     * <p>
     * <br><br>Summary: il test verifica che il metodo inserisca nella lista tutti gli elementi contenuti nella HCollection passata come parametro
     * nella posizione index passata anch'essa come parametro
     * <br><br>Design test: vengono create due HCollection che vengono passate entrambe come parametro al metodo addAll() insieme al loro indice.
     * Viene poi verificato il corretto inserimento degli elementi
     * Viene inoltre testato il lancio delle eccezioni quando viene indicato un indice al di fuori dei bordi della lista
     *
     * <br><br>Description: Si verifica prima di tutto che passato come parametro un elemento nullo o un indice invalido vengano lanciate rispettivamente
     * NullPointerException e IndexOutOfBoundsException
     * Dopo aver creato due HCollection, che vengono passate entrambe come parametro al metodo addAll() insieme rispettivamente all'indice size() e 0 della lista,
     * si controlla il corretto inserimento degli elementi all'interno della lista
     *
     * <br><br>Preconditions: il metodo add() e toArray() devono essere funzionanti
     * <br><br>Postconditions: la lista conterra' al suo interno gli elementi delle due HCollection
     * <br><br>Expected results: i valori delle HCollection devono essere inseriti nella list nella posizione indicata
     */
    @Test
    public void testAddAllIndex() {
        System.out.println("testAddAllIndex");
        HCollection coll;

        try {
            list.addAll(0, null);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        coll = new ListAdapter();
        try {
            list.addAll(list.size() + 1, coll);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        coll.add(4);
        coll.add(5);

        list.add(1);
        list.add(2);
        list.add(3);

        list.addAll(list.size(), coll);
        assertArrayEquals(new Object[]{1, 2, 3, 4, 5}, list.toArray());

        HCollection list2 = new ListAdapter();
        list2.add(-1);
        list2.add(0);

        list.addAll(0, list2);
        assertArrayEquals(new Object[]{-1, 0, 1, 2, 3, 4, 5}, list.toArray());
    }

    /**
     * Test of {@link myAdapter.ListAdapter#removeAll(HCollection)}
     * <p>
     * <br><br>Summary: il metodo di test rimuove dalla lista tutti gli elementi di una HCollection che viene passata come parametro
     * <br><br>Design test:
     * <br>Controlla se la HCollection passata come riferimento e' un elemento null e in caso affermativo verifica che venga lanciata la corretta eccezione
     * <br>Viene poi passato come riferimento una HCollection non contenente alcun elemento della lista e si verifica che non venga rimosso alcun elemento da quest'ultima.
     * <br>Infine si passa come riferimento a removeAll() un' HCollection contenente elementi della lista e si verifica la corretta rimozione degli elementi della lista, duplicati compresi.
     * <br><br>Description:
     * Dopo aver verificato il lancio di un'eccezione passando null come parametro, vengono riempiti la lista e un HCollection quest'ultima con un elemento non presente nella lista
     * Dopo aver verificato che il valore restituito sia falso vengono aggiunti altri due elementi, questa volta presenti nella lista, e si verifica che l'invocazione
     * del metodo removeAll passando HCollection come parametro restituisca true.
     * Si confronta infine il contenuto della lista con un array di Object contenente quanto ci si aspetta al termine delle operazioni
     * <br><br>Preconditions: I metodi add (Object obj) e toArray() devono funzionare correttamente
     * <br><br>Postconditions: nella lista non deve essere presente alcun elemento di HCollection
     * <br><br>Expected results: viene ritornato false se la rimozione non e' avvenuta e quindi gli elementi di HCollection sono presenti nella lista, true altrimenti
     */
    @Test
    public void testRemoveAll() {
        System.out.println("testRemoveAll");
        HCollection testColl = new ListAdapter();

        try {
            list.removeAll(null);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1);

        testColl.add(4);
        assertFalse(list.removeAll(testColl)); //Provo a eliminare un numero non presente
        assertArrayEquals(new Object[]{1, 2, 3, 1}, list.toArray());

        testColl.add(1);
        testColl.add(2);
        list.removeAll(testColl);
        assertArrayEquals(new Object[]{3}, list.toArray());
    }

    /**
     * Test of {@link myAdapter.ListAdapter#retainAll(HCollection)}
     * <p>
     * <br><br>Summary: Il test rimuove da una lista tutti gli elementi che non sono presenti in un HCollection passata come parametro
     *
     * <br><br>Design test:
     * <br>Viene passato come parametro al metodo retainAll un HCollection che non condivide nessun elemento con la lista e si verifica che tutti gli elementi della lista vengano rimossi
     * <br>Viene ripetuto lo stesso procedimento con un HCollection che condivide alcuni elementi e si verifica che vengano eliminati solo gli elementi non condivisi
     * Infine si passa come riferimento una HCollection vuota che, non contenendo alcun elemento con la lista, porta alla rimozione di tutti gli elementi di quest'ultima
     * <br><br>Preconditions:
     * <br>I metodi add (Object obj) toArray() e size() devono funzionare correttamente
     * <br><br>Postconditions: la lista non deve contenere nessun elemento che non sia presente nella HCollection
     * <br><br>Expected results: la lista contiene soltanto gli elementi presenti in HCollection, alla fine sara' quindi vuota
     */
    @Test
    public void testRetainAll() {
        System.out.println("testRetainAll");
        HCollection testColl = new ListAdapter();


        list.add(1);
        list.add(2);
        list.add(3);
        testColl.add(4);
        assertTrue(list.retainAll(testColl));
        assertEquals(0, list.size());

        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(4);
        testColl.add(2);
        testColl.add(4);
        assertTrue(list.retainAll(testColl));
        assertArrayEquals(new Object[]{2, 2, 4}, list.toArray());

        testColl.clear();
        assertTrue(list.retainAll(testColl));
        assertEquals(0, list.size());
    }

    /**
     * Test of {@link myAdapter.ListAdapter#equals(Object)}
     * Test of {@link myAdapter.ListAdapter#hashCode()}
     * <p>
     * <br><br>Summary: il metodo di test verifica che i metodi equals() e hashCode() ritornino true se due HCollection hanno la stessa dimensione e contengono gli stessi elementi
     * <br><br>Design test: Verifica che una lista vuota non e' uguale ad un valore null.
     * Due HCollection vengono riempite con gli stessi elementi e si controlla che i due metodi testati ritornino true.
     * Viene poi aggiunto un elemento a una delle due liste e si verifica la disuguaglianza
     *
     * <br><br>Description: Prima di tutto verifica che, confrontando una lista con un elemento nullo, non venga lanciata un'eccezione ma venga invece ritornato false
     * Di seguito due HCollection vengono riempite con gli stessi elementi e si controlla che i metodi equals e hasCode ritornino true.
     * in una delle due liste viene infine aggiunto un elemento e si verifica che i due metodi ritornino false
     *
     *
     * <br><br>Preconditions: il metodo add() deve funzionare correttamente
     * <br><br>Postconditions: Le due HCollection hanno la stessa dimensione e contengono gli stessi valori nello stesso ordine
     * <br><br>Expected results: viene ritornato true se le liste sono uguali, altrimenti false
     */
    @Test
    public void testEqualsAndHashCode() {
        System.out.println("testEqualsAndHashCode");

        assertNotEquals(list, null);

        list.add(1);
        list.add(2);
        list.add(3);

        coll.add(1);
        coll.add(2);
        coll.add(3);

        assertEquals(list, coll);
        assertEquals(list.hashCode(), coll.hashCode());

        coll.add(4);
        assertNotEquals(list, coll);
        assertNotEquals(list.hashCode(), coll.hashCode());
    }


    /**
     * Test of {@link myAdapter.ListAdapter#get(int)}
     * <p>
     * <br><br>Summary: il metodo di test verifica che l'elemento ritornato dalla funzione get sia quello aspettato. Verifica poi il funzionamento del metodo set()
     * <br><br>Design test: dopo aver inserito gli elementi nella lista verifica che il metodo get() ritorni l'elemento nella posizione indicata.
     * Vengono in seguito impostati tutti gli indici della lista a -1 attraverso il metodo set() e si verifica la corretta sostituzione degli elementi
     * Verifica poi che venga lanciata un'eccezione quand viene inserito un indice invalido
     * <br><br>Description: Dopo aver inserito alcuni elementi, attraverso un ciclo for viene verificata la corretta restituzione degli elementi dal metodo get.
     * A questo punto attraverso un altro ciclo si imposta il valore di tutti gli indici della lista a -1 e si controlla attraverso il metodo toArray() se la sostituzione
     * e' avvenuta con successo per ogni indice della lista
     * Viene infine verificato il lancio di un'eccezione quando viene passato come parametro un indice negativo o maggiore-uguale alla dimensione dell'array
     * <br><br>Preconditions: il metodo toArray()  e il metodo equals() devono funzionare correttamente
     * <br><br>Postconditions: l'elemento ritornato dal metodo get dev'essere presente alla posizione della lista indicata dall'indice passato come parametro.
     * L'array di prova e l'array di list devono essere uguali
     * <br><br>Expected results: Viene ritornato true per ogni elemento esaminato nella lista dal metodo get
     */
    @Test
    public void testGetAndSet() {
        System.out.println("testGetAndSet");
        list.add(1);
        list.add(2);
        list.add(3);


        Object[] arr = list.toArray();

        for (int i = 0; i < arr.length; i++)
        {
            assertEquals(arr[i], list.get(i));
        }

        for (int i = 0; i <  arr.length; i++)
        {
            list.set(i, -1);
        }

        assertArrayEquals(new Object[]{-1, -1, -1}, list.toArray());


        try {
            list.get(list.size());
            throw new NullPointerException();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        try {
            list.get(-1);
            throw new NullPointerException();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        try {
            list.set(list.size(), "outRight");
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        try {
            list.set(-1, "outLeft");
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }
    }




    /**
     * Test of {@link myAdapter.ListAdapter#indexOf(Object)}
     * <p>
     * <br><br>Summary: test dei metodi indexOf() e lastIndexOf() che cercano all'interno di una lista rispettivamente la prima  e ultima ricorrenza dell'elemento passato come parametro
     * <br><br>Design test: Viene inizialmente cercato l'indice di un elemento in una lista vuota. Si inseriscono poi degli elementi tra cui anche duplicati e si verifica la correttezza dei metodi
     *
     * <br><br>Description: the first test is performed on an empty list, thus not returning the index containing the desired element.
     * The following invocations are made after the insertion of values, some even repeated, specifically: invocation to find a non-repeated element,
     * invocation to find a repeated element and invocation to find an element not contained in the list. For simplicity, the values entered are Integer
     *
     * <br><br>Preconditions: la lista fornita e' non nulla
     * <br><br>Postconditions: viene ritornata la posizione della prima e ultima ricorrenza dell'elemento indicato. altrimenti -1
     * <br><br>Expected results: poiche' la lista e' inizialmente vuota l'elemento non e' presente e ritorna -1;
     * vengono ritornati gli indici degli elementi cercati
     */
    @Test
    public void testIndicesOf()
    {
        System.out.println("testIndicesOf");
        assertEquals(-1, list.indexOf(9));
        assertEquals(-1, list.lastIndexOf(9));

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        list.add(1);

        assertEquals(1, list.indexOf(2));
        assertEquals(0, list.indexOf(1));

        assertEquals(3, list.lastIndexOf(2));
        assertEquals(4, list.lastIndexOf(1));
    }


}


