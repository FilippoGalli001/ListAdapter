package myTest;

import myAdapter.*;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Classe Test dei metodi di {@link myAdapter.ListAdapter.ListIteratorAdapter}
 * <br>
 * Summary:
 * Questa classe verifica la correttezza dei metodi di ListIteratorAdapter, classe interna di ListAdapter.
 * Viene verificato che tutti i metodi funzionino nel modo progettato e che generino le eccezioni appropriate.
 * La classe ListIteratorAdapter implementa HListIterator e HIterator
 * La classe ListIteratorAdapter si occupa del test di tutti i metodi relativi a HListIterator raccogliendo talvolta piu' metodi in un unico test case quando questi sono logicamente simili
 * Per verificare senza troppi problemi se i metodi testati presentano degli errori la lista su cui si basa l'iteratore conterrà solo oggetti di tipo Integer e che,
 * provenendo dalla superclasse Object, non causeranno problemi di compatibilita'
 * <br><br>
 * Test Suite Design: in questa classe viene testato ogni metodo di ListIteratorAdapter.
 * <br><br>
 * <br><br>
 * Preconditions:
 * <br>Due nuovi oggetti di tipo HList (uno vuoto e uno contenente elementi) devono essere inizializzati prima di ogni test allo stesso modo di un iteratore di tipo HListIterator
 * <br><br>
 * Postconditions: I metodi implementati devono sempre agire sulla lista coerentemente al design di ciascun metodo
 * <br><br>
 * Test Suite Execution Records: Ogni metodo testato è corretto se tutti i test che ne verificano il corretto funzionamento danno esito positivo.
 * Il corretto funzionamento di tutti i metodi e' esso stesso l'Execution Records
 * <br><br>
 * Execution variables:
 * <br>HList emptyList - Lista vuota
 * <br>HList listWithData - Lista contenente elementi di tipo int
 * <br>HListIterator listIterator - iteratore utilizzato per attraversare le liste
 * <br><br>
 *
 * NOTA: le versioni di Junit superiori a 4.0 (come quella utilizzata) non supportano le versioni di Java inferiori a 1.5.0
 *
 * @see myAdapter.HList
 * @see myAdapter.HIterator
 * @see myAdapter.HListIterator
 * @see myAdapter.HCollection
 *
 * @version JUnit 4.13
 * @version Hamcrest: 1.3
 * @author Filippo Galli
 *
 */
public class TestIterator
{
    private HList listWithData, emptyList;
    private HListIterator listIterator;

    /**
     * Summary: Metodo utilizzato per inizializzare le variabili d'esecuzione prima di ogni test
     * <p><br>
     * Test Case Design: due liste di tipo HList vengono inizializzate, una viene riempita. Viene inoltre inizializzato l'iteratore
     * <br><br>Description: dopo l'inizializzazione delle due liste una di esse viene riempita con 10 interi da 0 a 9 e un HListIterator punta a prima della sua cella iniziale
     * <br><br>
     * Post-Condition: Le diverse variabili vengono inizializzate
     */

    @Before
    public void setup() {
        listWithData = new ListAdapter();
        emptyList = new ListAdapter();
        for(int i = 0; i<10; i++ )
        {
            listWithData.add(i);
        }
        listIterator = listWithData.listIterator(0);
    }

    /**
     * Summary: Il metodo di test attraversa avanti e indietro la lista utilizzando un iteratore e, dopo aver verificato che essa sia stata attraversata interamente,
     * verifica il lancio di eccezioni al tentativo di attraversamento dei bordi
     * <br><br>
     * Test Case Design: La lista viene attraversata dalla prima posizione fino all'ultima e viceversa controllando che alla fine dei due attraversamenti l'iteratore si trovi
     * in ultima, e in seguito prima, posizione.
     * Viene inoltre verificato che venga lanciata NoSuchElementException al tentativo di attraversamento del bordo destro e sinistro
     *
     * <br><br>Description: Viene attraversata la lista dal primo all'ultimo indice e viene verificato che l'ultimo elemento che e' stato attraversato sia l'ultimo elemento della lista.
     * Viene poi invocato next() per verificare che venga lanciato NoSuchElementException per poi riattraversare la lista in senso contrario e verificare nuovamente il bordo opposto.

     * <br><br>
     * Pre-condition:  i metodi previous, next, previousIndex, nextIndex, hasNext, hasPrevious e size devono essere implementati correttamente
     * <br><br>
     * Post-Condition: La lista e' stata attraversata interamente in entrambi i sensi e viene lanciato NoSuchElementException al tentativo di attraversamento dei bordi
     * <br><br>
     * Expected Results: Alla fine dell'esecuzione la lista e' stata interamente attraversata avanti e indietro tornando alla posizione iniziale
     * <br>
     */
    @Test
    public void iteratorMoveTest() //PERCORRE LISTA E CONTROLLA BORDI
    {
        System.out.println("IteratorMoveTest");

        for (int i = 0; listIterator.hasNext(); i++)
        {
            assertEquals(i,listIterator.next());
        }
        assertEquals(9,listIterator.previousIndex());
        assertEquals(listWithData.size(),listIterator.nextIndex());

        try {
            listIterator.next();
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NoSuchElementException.class, e.getClass());
        }

        listIterator = listWithData.listIterator(listWithData.size()); //iteratore punta alla fine della lista
        while(listIterator.hasPrevious())
        {
            listIterator.previous();
        }
        assertEquals(0,listIterator.nextIndex());

        try {
            listIterator.previous();
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NoSuchElementException.class, e.getClass());
        }
    }

    /**
     * Summary: il metodo di test verifica che l'iteratore di una HCollection funzioni correttamente. Poiche' i metodi di HIterator sono ridotti, vengono eseguiti
     * tutti all'interno di questo test
     * <br><br>
     * Test Case Design: Viene creata una nuova HCollection di tipo ListAdapter. Dopo averla riempita con una serie di elementi e aver creato il corrispettivo iteratore
     * la HCollection viene attraversata interamente rimuovendo ogni elemento attraversato.
     * Si controlla infine che la lista sia vuota
     *
     * <br><br>Description: Viene creata una nuova HColletion, viene riempita con cinque elementi e viene creato il corrispettivo iteratore che punta in prima posizione.
     * L'iteratore attraversa la collection controllando che l'oggetto attraversato sia quanto aspettato. in seguito elimina tale elemento.
     * Arrivato alla fine della HCollection si verifica che la lista sia vuota

     * <br><br>
     * Pre-condition: i metodi next e hasNext devono essere implementati correttamente
     * <br><br>
     * Post-Condition: La lista e' stata attraversata interamente e tutti gli elementi sono stati rimossi
     * <br><br>
     * Expected Results: Alla fine dell'esecuzione coll dovra' essere vuota
     * <br>
     */
    @Test
    public void hCollectionIteratorMoveTest()
    {
        System.out.println("hCollectionIteratorTest");
        HCollection coll = new ListAdapter();
        for(int i = 0; i < 5; i++)
        {
            coll.add(i);
        }
        HIterator  collIterator = coll.iterator();

        for (int i = 0; collIterator.hasNext(); i++)
        {
            assertEquals(i,collIterator.next());
            collIterator.remove();
        }

        assertArrayEquals(new Object[]{}, coll.toArray());
    }


    /**
     * Summary: Il test attraversa una lista non vuota rimuovendo ogni elemento appena attraversato
     * Alla fine alla lista ormai vuota viene aggiunto un nuovo elemento e viene poi invocato remove() (senza prima invocare next() o previous() ) lanciando quindi un eccezione.
     * <br><br>
     * Test Case Design: inizialmente l'iteratore prova a rimuovere un elemento dalla lista senza prima invocare next o previous causando il lancio di un'eccezione
     * L'iteratore attraversa poi la lista eliminando ciascun elemento appena attraversato
     * Viene infine aggiunto un nuovo elemento tramite l'iteratore e invocato il metodo remove() che lancera' un' eccezione.
     *
     * <br><br>Description: Viene inizialmente provato a rimuovere un elemento senza l'invocazione di next o previous lanciando cosi' IllegalStateException
     * la lista viene attraversata e ogni elemento attraversato viene rimosso rendendo alla fine del ciclo la lista vuota.
     * Alla fine viene aggiunto un nuovo elemento all'elenco, viene poi invocato remove() generando un IllegalStateException poiche' add viene invocato dopo
     * l'ultima chiamata a next (o previous)
     * <br><br>
     * Pre-condition: I metodi previous, next, add, remove, hasNext, hasPrevious e size devono funzionare correttamente
     * <br><br>
     * Post-Condition: L'iteratore deve aver eliminato tutti gli elementi della lista. Quest'ultima alla fine conterra'
     * un solo elemento che e' stato aggiunto con iil metodo add() e che non e' stato possibile eliminare con remove() che avra' lanciato un eccezione
     * <br><br>
     * Expected Results: la lista conterra' un solo elemento
     * <br>
     */
    @Test
    public void iteratorRemoveTest()
    {
        System.out.println("IteratorRemoveTest");

        try {
            listIterator.remove();
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IllegalStateException.class, e.getClass());
        }

        int i=0;
        Object[] arr = listWithData.toArray();
        while(listIterator.hasNext())
        {
            assertEquals(arr[i],listIterator.next());
            listIterator.remove();
            i++;
        }
        assertEquals(0,listWithData.size());

        try {
            listIterator.add(1);
            listIterator.remove();
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IllegalStateException.class, e.getClass());
        }
        assertEquals(1,listWithData.size());
    }

    /**
     * Summary: il metodo di test verifica il corretto funzionamento del metodo set() di HListIterator controllando inoltre il corretto lancio di eccezioni da parte del metodo
     *
     * <br><br>Design test: Viene creato un nuovo iteratore che punta al primo indice di una lista non vuota. Si verifica prima di tutto che il metodo lanci un'eccezione
     * se questo viene invocato senza prima aver invocato next o previous.
     * Vengono poi attraversati tutti gli elementi della lista invocando a ogni attraversamento il metodo set() che sostituisce l'elemento
     * appena attraversato. Si verifica poi che tutti gli elementi siano stati sostituiti con successo
     *
     * <br><br>
     * Description: Si crea un nuovo iteratore che punta all'indice zero di una lista di 10 elementi. Si verifica che il metodo lanci un'eccezione se questo viene invocato
     * senza prima aver invocato il metodo next o previous.
     * Si attraversano tutti gli elementi della lista invocando il metodo set(1) a ogni attraversamento sostituendo ognuno di questi elementi con l'elemento 1.
     * Viene infine verificato che la lista finale abbia la stessa dimensione della lista iniziale e che ogni sostituzione sia stata eseguita con successo
     * <br><br>
     * Precondition: i metodi  size() e toArray() devono funzionare correttamente
     * <br><br>
     * PostCondition: La lista contiene al suo interno solamente elementi Integer "1"
     * <br><br>
     * Expected Results: per ogni indice della lista l'elemento in esso contenuto deve essere 1
     * <br><br>
     */
    @Test
    public void iteratorSetTest() //SET, PROVA ECCEZIONI SET
    {
        System.out.println("IteratorSetTest");
        HListIterator listIterator = listWithData.listIterator(0);

        try {
            listIterator.set(1);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IllegalStateException.class, e.getClass());
        }

        while (listIterator.hasNext())
        {
            listIterator.next();
            listIterator.set(1);
        }
        assertEquals(10,listWithData.size());

        Object[] arr = listWithData.toArray();
        listIterator = listWithData.listIterator(0);

        for(int i = 0; i<arr.length; i++)
        {
            assertEquals(1,arr[i]);
        }

    }

    /**
     * Test of
     * {@link myAdapter.ListAdapter.ListIteratorAdapter})
     * <p>
     * <br><br>Summary: il test verifica che il metodo add(Object) di iterator inserisca l'elemento nella posizione esatta, controllando che dopo l'inserimento l'iteratore
     * si trovi nella posizione corretta
     * <br><br>Design test: Viene inizialmente verificato che la lista sia vuota. Si aggiungono poi una serie di elementi in diverse posizioni della lista
     * e si verifica infine che gli elementi inseriti (e di conseguenza l'iteratore) si trovino nella posizione attesa
     * <br><br>Description: prima di tutti si verifica attraverso i metodi hasNext e hasPrevious che la lista fornita sia vuota.
     * Viene aggiunto un elemento, di seguito si sposta l'iteratore a sinistra di una posizione.
     * Si aggiungono altri due elementi e si verifica anche di questi il corretto posizionamento
     * Viene poi attraversata la lista fino all'ultimo elemento e vengono inseriti gli ultimi due elementi.
     * Si confrontano infine gli elementi della lista con un array che contiene gli elementi attesi
     * <br><br>Preconditions: i metodi toArray, previous, next, hasNext, hasPrevious devono funzionare correttamente e la lista fornita dev'essere vuota e non nulla
     * <br><br>Postconditions: la lista deve contenere tutti gli elementi inseriti
     * <br><br>Expected results: La lista contiene esattamente gli elementi contenuti nell'array di Object confrontato
     */
    @Test
    public void iteratorAddTest() {
        System.out.println("iteratorAddTest");
        HListIterator listIterator = emptyList.listIterator();
        assertFalse(listIterator.hasNext());
        assertFalse(listIterator.hasPrevious());

        listIterator.add(2);
        assertEquals(2, listIterator.previous());
        listIterator.add(0);
        listIterator.add(1);
        assertEquals(1, listIterator.previous());
        assertEquals(0, listIterator.previous());
        while (listIterator.hasNext())
        {
            listIterator.next();
        }
        listIterator.add(3);
        listIterator.add(4);

        Object[] arr = emptyList.toArray();

        assertArrayEquals(new Object[]{0, 1, 2, 3, 4}, arr);

    }
}
