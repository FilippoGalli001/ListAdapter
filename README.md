# ListAdapter

This is a project that contains a List Adapter made in java. It uses the Vector class from CLDC 1.1 as the adaptee of this class. The implementation allows function from the J2RE 1.4.2 only.


ListAdapter e' una classe che implementa tutti i metodi delle classi Collection e List di Java 2 Runtime Environment, J2RE 1.4.2.
Funge da adattatore di quelle interfacce che usano come adaptee la classe java.util.Vector della Java Micro Edition, CLDC1.1.
La classe permette l'inserimento di elementi doppi (ovvero gia' presenti nella lista) ed elementi di tipo null.
{<a href="https://docs.oracle.com/javame/config/cldc/ref-impl/cldc1.1/jsr139/index.html">...</a>}

La classe implementa le interfacce di {@link HCollection} e {@link HList}

La classe possiede una classe interna ListIteratorAdapter, ovvero un iteratore che permette di attraversare la lista in entrambe le direzioni
permettendo inoltre di apportare alcune modifiche alla lista
