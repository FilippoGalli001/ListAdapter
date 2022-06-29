package myTest;

import myAdapter.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Classe test dei metodi di {@link myAdapter.SubList}
 * <br>
 * Summary: Questa classe testa le funzionalita' di tutti i metodi di SubList che sono stati sovrascritti e presentano quindi differenze dal punto di vista del codice
 * dalla superclasse che estendono
 * <br><br>
 * Design test: Vengono testati tutti i metodi sovrascritti di SubList, classe ereditata da ListAdapter. Spesso piu' metodi sono raggruppati in singoli test case.
 * <br><br>
 * Description: La classe SubList estende ListAdapter. Essa puo' essere vista come un sottoinsieme della classe principale da cui dipende strettamente:
 * Ogni modifica alla SubList si riflette sulla lista padre e viceversa. Vengono percio' testati i metodi che permettono di cogliere questo legame tra le due classi
 * apportando quindi modifiche alla SubList e verificando che queste avvengano anche nella lista genitore
 * Per semplicita' verranno inseriti nella SubList soltanto elementi di tipo String
 * <br><br>
 * Preconditions:
 * <br>Due oggetti di tipo HList devono essere inizializzati prima di ogni test
 * <br>Per verificare il corretto funzionamento dei test il contenuto delle liste e' noto a priori
 * <br><br>
 * Postconditions: i metodi implementati devono sempre modificare la SubList in modo che gli elementi contenuti siano esattamente quelli previsti
 * <br><br>
 * Execution record: ogni metodo testato è corretto se tutti i test che ne verificano il corretto funzionamento danno esito positivo.
 * Il corretto funzionamento di tutti i metodi e' esso stesso l'Execution Records
 * <br><br>
 * Execution variables:
 * <br>String[] arr - Array di stringhe contenente una serie di elementi
 * <br>HList l1 - Lista vuota in cui vengono inseriti gli elementi contenuti nell array "arr" prima di ogni test
 * <br>HList l2 - Lista vuota
 * <br><br>
 *
 * NOTA: le versioni di Junit superiori a 4.0 (come quella utilizzata) non supportano le versioni di Java inferiori a 1.5.0
 *
 * @see myAdapter.HList
 * @see myAdapter.HIterator
 * @see myAdapter.HListIterator
 * @see myAdapter.HCollection
 * @see myAdapter.ListAdapter
 *
 *
 * @version JUnit 4.13
 * @version Hamcrest: 1.3
 * @author Filippo Galli
 *
 */
public class TestSubList {
    HList l1 = null, l2 = null;
    static String[] arr = {"1", "qui", "2", "3", "qui", "4", "outSubList"};


    /**
     * Metodo che inizializza le variabili prima di ogni test
     * <p>
     * Vengono create due ListAdapter di cui una viene riempita col contenuto dell'array arr prima di ogni test
     * <br>
     * <br>
     * Preconditions: Il costruttore di ListAdapter dev'essere funzionante
     */
    @Before
    public void setup()
    {
        l1 = new ListAdapter();
        l2 = new ListAdapter();

        for(int i=0;i<arr.length;i++)
        {
            l1.add(arr[i]);
        }
    }

    /**
     * Metodo utilizzato durante la fase di scrittura del codice per verificare manualmente quel che succede all'interno della lista
     * <p>
     * la lista viene attraversata da un iteratore che stampa ogni elemento attraversato
     */
    public static void iterate(HIterator iter)
    {
        System.out.print("{");
        while(iter.hasNext())
        {
            System.out.print(iter.next() + "; ");
        }
        System.out.println("}");
    }


    /**
     * Test of {@link myAdapter.ListAdapter#subList(int, int)}
     * <p>
     * <br><br>Summary: il metodo di test verifica il corretto funzionamento del costruttore della classe SubList, invocato dal metodo subList() di ListAdapter
     * <br><br>Design test: viene invocato il metodo passando come parametri due volte lo stesso indice, si verifica che la dimensione della sublist sia zero.
     * Si ripete lo stesso con due indici differenti tale che from sia minore di to e si verifica il corretto contenuto della SubList.
     * La main list viene poi svuotata e si verifica che inserendo indici diversi da zero venga lanciata un'eccezione
     * <br><br>Description: Si invoca il metodo subList della lista passando come parametri lo stesso indice creando quindi una SubList vuota.
     * Si procede come prima passando due indici validi e si verifica la corretta dimensione e contenuto della SubList.
     * Vengono infine rimossi dalla lista tutti gli elementi e si verifica che, creando una nuova SubList passando come parametro indici maggiore della dimensione della lista
     * venga lanciata un'eccezione
     * <br><br>Preconditions: i metodi size () e toArray () devono funzionare correttamente
     * <br><br>PostConditions: la SubList e' vuota
     * <br><br>Expected results: La lista creata contiene tutti gli elementi dell'intervallo da from (compreso) a to (non compreso)
     */
    @Test
    public void subListConstructor() {

        System.out.println("subListConstructor");
        //iterate(l1.iterator());
        HList subList = l1.subList(2, 2);
        assertEquals(0, subList.size());

        subList = l1.subList(2, 4);
        assertEquals(2, subList.size());
        assertArrayEquals(new Object[]{"2", "3"}, subList.toArray());

        l1.clear();
        try {
            subList = l1.subList(2, 3);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

    }

    /**
     * Test of {@link myAdapter.SubList#remove(Object)}
     * Test of {@link myAdapter.SubList#add(Object)}
     * <p>
     * <br><br>Summary: il metodo di test verifica il corretto funzionamento dei metodi add() e remove() di una SubList
     * Verifica inoltre che un oggetto al di fuori della SubList non venga visto al proprio interno
     *
     * <br><br>Test Suite Design: Viene creata una nuova SubList, viene inserito un elemento alla fine di quest'ultima e si verifica il suo corretto inserimento
     * Si verifica poi che se si cerca di accedere a un elemento della lista genitore non presente nell'intervallo della SubList venga lanciata un eccezione.
     * Viene di seguito verificato che, invocato il metodo remove passando come parametro un elemento presente della lista genitore ma non nella SubList la rimozione non avvenga ritornando false.
     * Si verifica infine la corretta rimozione di un elemento che e' presente in due indici diversi della SubList.
     * il metodo di rimozione in questo caso viene lanciato due volte poiche' a ogni iterazione del metodo verra' eliminata la prima ricorrenza di quell'elemento
     *
     * <br><br>Pre-condition: La lista genitore dev'essere non null e deve contenere almeno sei elementi
     *
     * <br><br>Post-Condition: gli elementi passati come parametro ai metodi remove(Object) presenti nella SubList devono essere stati rimossi
     *
     * <br><br>Expected results: La SubList contiene tutti gli elementi attesi
     */
    @Test
    public void subListAddRemove()
    {
        System.out.println("SubListAddRemove");

        //iterate(l1.iterator());
        l2 = l1.subList(1,5);
        //iterate(l2.iterator());
        l2.add("3.5");
        assertEquals("3.5",l2.get(l2.size()-1));

        try {
            l2.get(l2.size());
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }


        assertFalse(l2.remove("outSubList")); //Controlla che non elimini elementi lista genitore

        assertTrue(l2.remove("qui"));
        assertTrue(l2.remove("qui"));
        assertArrayEquals(new Object[]{"2","3","3.5"},l2.toArray());

    }

    /**
     * Summary: il metodo di test controlla che la SubList, la cui lista genitore passata come parametro al costruttore e' anch'essa una sublist, funzioni correttamente
     * <br><br>
     * Test Suite Design: Viene creata una SubList e da essa viene creata una seconda SubList. Viene verificata la correttezza del contenuto della SubList e la relazione
     * con la lista genitore
     * <br><br>
     * Description: Viene creata una SubList e, invocando il metodo subList(), si crea una seconda SubList figlia.
     * Si controlla il contenuto di quest'ultima e in seguito si rimuovono tutti i suoi elementi.
     * Viene verificato infine che queste rimozioni si ripercuotano sulla lista genitore
     * <br><br>
     * Pre-condition: la lista di tipo ListAdapter non dev'essere vuota
     * <br><br>
     * Post-Condition: La lista genitore deve contenere tutti gli elementi presenti nell'array di Object
     * <br><br>
     * Expected results: le rimozioni effettuate nella subList si ripercuotono sulla lista genitore
     */
    @Test
    public void subListConstructorSubTest()
    {
        System.out.println("subListConstructorSubTest");
        l2 = l1.subList(0,6);
        //iterate(l2.iterator());
        HList subsub;
        subsub = l2.subList(2,5);
        //iterate(subsub.iterator());
        assertArrayEquals(new Object[]{"2","3","qui"},subsub.toArray()); //controlla siano uguali
        subsub.clear();
        assertArrayEquals(new Object[]{"1","qui","4"},l2.toArray()); //controlla siano backed
    }


    /**
     * Test of {@link myAdapter.SubList#isEmpty()}
     * <p>
     * Summary: il metodo di test verifica che il metodo isEmpty funzioni correttamente
     * <br><br>
     * Test Case Design:
     * Viene creata una SubList che contiene una serie di elementi e ne viene verificata la presenza
     * vengono rimossi dalla SubList tutti gli elementi e si verifica che essa sia vuota.
     * Viene poi verificata la stessa condizione per una lista con indici from == to
     *
     * <br><br>Description: dopo aver dichiarato la sublist si verifica che essa non sia vuota.
     * Dopo aver rimosso gli elementi della SubList viene controllato che non contenga alcun elemento
     * Viene poi dichiarata una lista vuota e controllato che essa non contenga elementi
     * <br><br>
     * Pre-condition: il costruttore e il metodo clear devono funzionare correttamente
     * <br><br>
     * Post-Condition: il metodo ritorna true se la lista e' vuota e non contiene quindi alcun elemento
     * <br><br>
     * Expected Results: isEmpty() ritorna true quando la SubList e' vuota. Altrimenti false
     * <br><br>
     */
    @Test
    public void isEmptyTest()
    {
        System.out.println("isEmptyTest");
        l2 = l1.subList(2,5);
        assertFalse(l2.isEmpty());
        l2.clear();
        assertTrue(l2.isEmpty());
        l2 = l1.subList(1,1);
        assertTrue(l2.isEmpty());
    }


    /**
     * Summary: il metodo di test verifica il corretto funzionamento di set, controllando inoltre che sa si prova ad accedere a elementi al di fuori della SubList questi non possano essere modificati
     * <br><br>
     * Test Suite Design: viene creata una sottolista in cui viene in seguito sostituito un elemento. Si verifica che l'elemento sostituito sia quello corretto e si controlla
     * infine il lancio di eccezioni se si tenta di sostituire un elemento al di fuori della SubList
     *<br><br>
     * Description: Si crea una sottolista di tre elementi, si sostituisce l'elemento in seconda posizione e si controlla che, sia l'elemento da sostituire, sia quello
     * presente alla fine del processo siano quelli aspettati. Viene infine controllato il lancio di eccezioni quando si prova a sostituire elementi fuori dai bordi della SubList
     * <br><br>
     * Pre-condition: Il metodo get() deve funzionare correttamente, la lista genitore non deve essere vuota
     * <br><br>
     * Post-Condition: la lista non deve contenere l'elemento che e' stato sostituito. Deve invece contenere il nuovo elemento
     * <br><br>
     * Expected results: l'elemento della SubList viene sostituito correttamente e la sostituzione si ripercuote anche nella lista genitore
     */
    @Test
    public void subListSet() //verifica metodo set e che oggetto al di fuori della SubList non venga visto al suo interno
    {
        System.out.println("SubListSet");
        l2 = l1.subList(0,3);
        //iterate(l2.iterator());
        assertEquals("qui",l2.set(1,"1.5"));
        assertEquals("1.5",l2.get(1));
        assertEquals("1.5",l1.get(1));

        try {
            l2.set(-1,"out");
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        try {
            l2.set(5,"out");
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }
    }

    /**
     * Summary: il metodo di test verifica che l'iteratore di una SubList funzioni correttamente
     * <br><br>
     * Test Suite Design: viene creata una nuova SubList e il suo iteratore che punta al suo primo elemento. Viene poi attraversata tutta la SubList
     * eliminano tutti gli elementi attraversati. Si verifica infine che la SubList sia vuota e che le rimozioni si siano ripercosse anche nella lista principale
     * <br><br>
     * Description: Si crea una nuova Sublist e il corrispondente iteratore che si trova in prima posizione. L'iteratore attraversa tutta la SubList eliminando ogni elemento attraversato
     * Viene infine controllato che tutti gli elementi della SubList siano stati rimossi sia dalla SubList stessa sia dalla lista genitore
     * <br><br>
     * Pre-condition: La lista genitore non dev'essere nulla e vuota
     * <br><br>
     * Post-Condition: La SubList alla fine del processo dev'essere vuota
     * <br><br>
     * Expected results: La rimozione degli elementi dev'essere avvenuta con successo in entrambe le liste (genitore e figlia)
     */
    @Test
    public void subListIterator()
    {
        System.out.println("subListIterator");
        //iterate(l1.iterator());
        l2 = l1.subList(2,6);
        //iterate(l2.iterator());
        HListIterator subIter = l2.listIterator();
        while(subIter.hasNext())
        {
            subIter.next();
            subIter.remove();
        }
        assertEquals(0,l2.size());
        assertArrayEquals(new Object[]{"1", "qui", "outSubList"}, l1.toArray());

    }

    /**
     * Summary: il metodo di test verifica il corretto funzionamento del costruttore di SubList che genera di volta in volta una nuova sottolista partendo dagli indici
     * e dagli elementi della SubList precedentemente generata
     * <br><br>
     * Test Suite Design: La lista viene riempita da una serie di elementi. Viene poi iterativamente creata a partire da questa lista una nuova SubList che sostituisce quella precedente.
     * Questo ciclo si ripete fino a quando la nuova sublist non ha piu' elementi.
     * Per ogni ciclo si verifica che la nuova SubList si riduca di due elementi (uno al bordo destro e una a quello sinistro) rispetto a quella precedente e che gli elementi
     * rimossi fossero appunto i bordi della lista precedente
     * <br><br>
     * Pre-condition: i metodi add, size e get devono funzionare correttamente
     * <br><br>
     * Post-Condition: La SubList alla fine del processo dev'essere vuota
     * <br><br>
     * Expected results: avvengono n-iterazioni del costruttore di SubList fino a quando la SubList creata non e' vuota
     */
    @Test
    public void testRecursiveSublist()
    {
        System.out.println("TestRecursive SubListing");

        int prev = l2.size();
        Object[] arr50 = new Object[50];
        for(int i=0;i<50;i++)
        {
            l2.add(i + "");
            arr50[i] = i + "";
        }

        int after = 0;
        int count = 0;
        while(l2.size()>=2)
        {

            prev = l2.size();
            l2 = l2.subList(1, prev-1);
            after = l2.size();
            count++;

            if (count < l2.size()/2)
            {
                assertEquals(arr50[count], l2.get(0));
                assertEquals(arr50[49-count], l2.get(l2.size()-1)+"");
            }

            assertEquals(after, (prev-2));

        }
    }



}
