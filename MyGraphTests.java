
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: 		P4 Package Manager
// Course: 		CS 400, Fall , 2019
//
// Author: 		Yash Sukhwani
// Email:  		sukhwani@wisc.edu
// Lecturer: 	Deb Deppler
// Due Date: 	Nov 14, 2019
//
// Known Bugs:	None
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NO PERSONS
// Online Sources: NO SOURCES
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for Graph.java file
 * 
 * @author yashsukhwani
 *
 */
class MyGraphTests {

	/**
	 * This method tests that the correct adjacencies are received.
	 */
	@Test
	void test01_adjacencies() {
		Graph testGraph = new Graph();
		testGraph.addVertex("1");
		testGraph.addEdge("1", "2");
		testGraph.addVertex("3");
		testGraph.addEdge("1", "3");
		testGraph.addEdge("2", "4");
		testGraph.addEdge("4", "1");
		testGraph.addEdge("5", "1");
		testGraph.addEdge("6", "5");

		testGraph.addEdge("3", "2");
		testGraph.addEdge("3", "6");
		testGraph.addEdge("6", "2");
		testGraph.addEdge("4", "5");

		ArrayList<String> testList = (ArrayList<String>) testGraph.getAdjacentVerticesOf("1");
		if (!testList.get(0).equals("2") || !testList.get(1).equals("3")) {
			fail();
		}

		testList = (ArrayList<String>) testGraph.getAdjacentVerticesOf("3");
		if (!testList.get(0).equals("2") || !testList.get(1).equals("6")) {
			fail();
		}

		testList = (ArrayList<String>) testGraph.getAdjacentVerticesOf("6");
		if (!testList.get(0).equals("5") || !testList.get(1).equals("2")) {
			fail();
		}

		testList = (ArrayList<String>) testGraph.getAdjacentVerticesOf("4");
		if (!testList.get(0).equals("1") || !testList.get(1).equals("5")) {
			fail();
		}

	}

	/**
	 * This method tests that vertces are removed correctly.
	 */
	@Test
	void test02_removeVertex() {

		Graph testGraph = new Graph();
		testGraph.addVertex("1");
		testGraph.addEdge("1", "2");
		testGraph.addVertex("3");
		testGraph.addEdge("1", "3");
		testGraph.addEdge("2", "4");
		testGraph.addEdge("4", "1");
		testGraph.addEdge("5", "1");
		testGraph.addEdge("6", "5");

		testGraph.addEdge("3", "2");
		testGraph.addEdge("3", "6");
		testGraph.addEdge("6", "2");
		testGraph.addEdge("4", "5");

		if (testGraph.size() != 10)
			fail();

		if (testGraph.order() != 6)
			fail();

		testGraph.removeEdge("1", "3");

		if (testGraph.size() != 9)
			fail();

		testGraph.removeVertex("1");
		testGraph.removeEdge("6", "5");

		if (testGraph.size() != 5)
			fail();

		if (testGraph.order() != 5)
			fail();
	}

	/**
	 * This method tests getAllVertices
	 */
	@Test
	void test03_getAllVertices() {

		Graph testGraph = new Graph();
		testGraph.addVertex("1");
		testGraph.addEdge("1", "2");
		testGraph.addVertex("3");
		testGraph.addEdge("1", "3");
		testGraph.addEdge("2", "4");
		testGraph.addEdge("4", "1");
		testGraph.addEdge("5", "1");
		testGraph.addEdge("6", "5");

		testGraph.addEdge("3", "2");
		testGraph.addEdge("3", "6");
		testGraph.addEdge("6", "2");
		testGraph.addEdge("4", "5");

		testGraph.removeVertex("1");
		testGraph.removeEdge("6", "5");

		System.out.println(testGraph.getAllVertices());

		Set<String> t = testGraph.getAllVertices();

		if (t.contains("1"))
			fail();

		if (!t.contains("2"))
			fail();

		if (!t.contains("3"))
			fail();

		if (!t.contains("4"))
			fail();

		if (!t.contains("5"))
			fail();

		if (!t.contains("6"))
			fail();
	}

	/**
	 * This method tests a variety of applications where null is used.
	 */
	@Test
	void test04_nulls() {
		Graph testGraph = new Graph();
		testGraph.addVertex("1");
		testGraph.addEdge("1", "2");
		testGraph.addVertex("3");
		testGraph.addEdge("1", "3");
		testGraph.addEdge("2", "4");
		testGraph.addEdge("4", "1");
		testGraph.addEdge("5", "1");
		testGraph.addEdge("6", "5");

		testGraph.addEdge("3", "2");
		testGraph.addEdge("3", "6");
		testGraph.addEdge("6", "2");
		testGraph.addEdge("4", "5");

		testGraph.addEdge("3", null);
		testGraph.addEdge(null, "9");

		if (testGraph.size() != 10)
			fail();

		if (testGraph.order() != 6)
			fail();

		testGraph.removeEdge("1", "3");

		testGraph.addEdge(null, null);
		testGraph.addVertex(null);

		if (testGraph.size() != 9)
			fail();

		testGraph.removeVertex("1");

		testGraph.removeEdge("1", "2");
		testGraph.removeEdge(null, "4");

		testGraph.removeEdge("6", "5");

		testGraph.removeEdge("6", "5");
		testGraph.removeEdge("3", null);
		testGraph.removeEdge(null, null);

		if (testGraph.size() != 5)
			fail();

		if (testGraph.order() != 5)
			fail();
	}

}
