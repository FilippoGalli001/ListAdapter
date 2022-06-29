package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.util.*;

/**
 * Classe main che esegue tutti le test suite del pacchetto {@link myTest}
 * <p>
 *
 * NOTA: le versioni di Junit superiori a 4.0 (come quella utilizzata) non supportano le versioni di Java inferiori a 1.5.0
 *
 *
 * @version JUnit 4.13
 * @version Hamcrest: 1.3
 *
 *
 */
public class TestRunner {
	private static int totalTests = 0;

	/**
	 * invoca tutti i test
	 * @param args non utilizzata
	 */
	public static void main(String[] args) {
		Result res;

		System.out.println("**Test in execution..**\n");

		System.out.println("\nTest of ListAdapter...");
		res = JUnitCore.runClasses(myTest.TestListMia.class);
		esitoTest(res);

		System.out.println("\nTest of ListIteratorAdapter...");
		res = JUnitCore.runClasses(myTest.TestIterator.class);
		esitoTest(res);

		System.out.println("\nTest of SubList...");
		res = JUnitCore.runClasses(myTest.TestSubList.class);
		esitoTest(res);


		System.out.println("\n*** All the " + totalTests + " tests have been completed +**");
	}

	/**
	 * Stampa il risultato di ogni suite case
	 * <p>
	 *
	 * Per ogni suite viene indicato quanti test case sono stati eseguiti e quanti sono falliti
	 * @param res
	 */
	private static void esitoTest(Result res)
	{
		totalTests += res.getRunCount();
		System.out.print("Of " + res.getRunCount() + " tests ");
		if (res.wasSuccessful())
		{
			System.out.println("all are with a positive result");
		}
		else
		{
	  	System.out.println("failed " + res.getFailureCount() + " tests");
			List<Failure> fails = res.getFailures();
			Iterator<Failure> iter = fails.iterator();
			while(iter.hasNext())
			{
				System.out.println(iter.next().toString());
			}
		}
	}
}
