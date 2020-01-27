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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Filename: Graph.java Project: P4 Authors: Yash Sukhwani
 * 
 * Directed and unweighted graph implementation.
 */

public class Graph implements GraphADT {

	/**
	 * Inner class for vertices.
	 * 
	 * @author yashsukhwani
	 *
	 */
	private class Vertex {

		private ArrayList<String> edgesIn; 
		private ArrayList<String> edgesOut;

		/**
		 * Constructor for the inner class.
		 */
		Vertex(String name) {
			edgesIn = new ArrayList<>(); // maintains edges coming in.
			edgesOut = new ArrayList<>(); // maintains edges going out.
			
		}

	}

	private int numEdges = 0;

	// indexes of both these lists always refer to the same vertex
	private ArrayList<String> vertices; // list of all vertices as strings
	private ArrayList<Vertex> listVertex; // list of vertex objects

	/*
	 * Default no-argument constructor
	 */
	public Graph() {

		vertices = new ArrayList<>();
		listVertex = new ArrayList<>();
	}

	/**
	 * Add new vertex to the graph.
	 *
	 * If vertex is null or already exists, method ends without adding a vertex or
	 * throwing an exception.
	 * 
	 * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in
	 * the graph
	 */
	public void addVertex(String vertex) {

		if (vertex == null || vertices.contains(vertex)) {
			System.out.println("Error! Vertex is null or already exists.");
			System.out.print(" addVertex()");
		} else {
			vertices.add(vertex);
			listVertex.add(new Vertex(vertex)); // adding new vertex
		}
	}

	/**
	 * Remove a vertex and all associated edges from the graph.
	 * 
	 * If vertex is null or does not exist, method ends without removing a vertex,
	 * edges, or throwing an exception.
	 * 
	 * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in
	 * the graph
	 */
	public void removeVertex(String vertex) {

		if (vertex == null || !vertices.contains(vertex)) {
			System.out.println("Error! Vertex was null or not in graph.");
			System.out.print(" removeVertex()");
			return;
		} else {

			int removeIndex = vertices.indexOf(vertex); // used to access vertex object list
			listVertex.remove(removeIndex);
			vertices.remove(removeIndex);

			// removing all associated edges
			for (int i = 0; i < listVertex.size(); i++) {
				Vertex gotVertex = listVertex.get(i);

				if (gotVertex.edgesOut.contains(vertex)) {
					gotVertex.edgesOut.remove(vertex); // removing edge from edgesOut list
					numEdges--;
				}

				if (gotVertex.edgesIn.contains(vertex)) {
					gotVertex.edgesIn.remove(vertex); // removing edge from edgesIn list
					numEdges--;
				}
			}
		}
	}

	/**
	 * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and
	 * unweighted) If either vertex does not exist, add vertex, and add edge, no
	 * exception is thrown. If the edge exists in the graph, no edge is added and no
	 * exception is thrown.
	 * 
	 * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
	 * the graph 3. the edge is not in the graph
	 */
	public void addEdge(String vertex1, String vertex2) {
		Vertex v1;
		Vertex v2;

		if (vertex1 == null || vertex2 == null) { // handling nulls
			System.out.println("Error! New edge vertex was null.");
			System.out.print(" addEdge()");
			return;
		}

		if (vertices.contains(vertex1)) {
			int getIndex = vertices.indexOf(vertex1);
			v1 = listVertex.get(getIndex);
		} else {
			v1 = new Vertex(vertex1); // reference to first vertex
			listVertex.add(v1);
			vertices.add(vertex1);
		}

		if (vertices.contains(vertex2)) { // checks if 2nd one exists
			int getIndex = vertices.indexOf(vertex2);
			v2 = listVertex.get(getIndex);
		} else {
			v2 = new Vertex(vertex2); // reference to destination vertex
			listVertex.add(v2);
			vertices.add(vertex2);
		}

		if (v1.edgesOut.contains(vertex2)) {
			System.out.println("Error! Edge already exists.");
			System.out.print(" addEdge()");
			return;
		} else {
			v1.edgesOut.add(vertex2);
			v2.edgesIn.add(vertex1);
		}
		
		numEdges++;
	}

	/**
	 * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed
	 * and unweighted) If either vertex does not exist, or if an edge from vertex1
	 * to vertex2 does not exist, no edge is removed and no exception is thrown.
	 * 
	 * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
	 * the graph 3. the edge from vertex1 to vertex2 is in the graph
	 */
	public void removeEdge(String vertex1, String vertex2) {

		Vertex v1; // outgoing vertex
		Vertex v2; // target vertex

		if (vertex1 == null || vertex2 == null) {
			System.out.println("Error! Vertex for removing edge was null.");
			return;
		} else if (!vertices.contains(vertex1) || !vertices.contains(vertex2)) {
			System.out.println("Error! Vertex for removing edge was not in graph.");
			return;
		}

		int getIndex = vertices.indexOf(vertex1);
		v1 = listVertex.get(getIndex);
		getIndex = vertices.indexOf(vertex2);
		v2 = listVertex.get(getIndex);

		if (!v1.edgesOut.contains(vertex2)) {
			return;
		} else {
			v1.edgesOut.remove(vertex2); // removing vertices
			v2.edgesIn.remove(vertex1);
		}
		
		numEdges--;
	}

	/**
	 * Returns a Set that contains all the vertices
	 * 
	 */
	public Set<String> getAllVertices() {
		Set<String> all = new LinkedHashSet<String>();

		for (int i = 0; i < vertices.size(); i++) {
			all.add(vertices.get(i));
		}

		return all; // all refers to all vertices
	}

	/**
	 * Gets all the neighbor (adjacent) vertices of a vertex
	 *
	 */
	public List<String> getAdjacentVerticesOf(String vertex) {
		int getIndex = vertices.indexOf(vertex);
		Vertex getVertex = listVertex.get(getIndex);
		return getVertex.edgesOut;
	}

	/**
	 * Returns the number of edges in this graph.
	 */
	public int size() {
		return numEdges;
	}

	/**
	 * Returns the number of vertices in this graph.
	 */
	public int order() {
		return vertices.size();
	}
}
