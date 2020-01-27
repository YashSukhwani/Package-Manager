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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename:   PackageManager.java
 * Project:    p4
 * Authors:    
 * 
 * PackageManager is used to process json package dependency files
 * and provide function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own
 * entry in the json file.  
 * 
 * Package dependencies are important when building software, 
 * as you must install packages in an order such that each package 
 * is installed after all of the packages that it depends on 
 * have been installed.
 * 
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 * 
 * This program will read package information and 
 * provide information about the packages that must be 
 * installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

public class PackageManager {
    
    private Graph graph;
    private Graph graphR; // reverse of graph. Used for topological order.
    private JSONArray packages;
    
    private ArrayList<String> packageList;
    private ArrayList<String> independent;
    
    
    /*
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
        graph = new Graph();
        graphR = new Graph();
    }
    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the give file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
        
    	Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
    	JSONObject jo = (JSONObject) obj;
    	packages = (JSONArray) jo.get("packages");
    	
    	packageList = new ArrayList<>();
    	ArrayList<ArrayList<String>> dependencyList = new ArrayList<ArrayList<String>>();
    	ArrayList<String> tempList = new ArrayList<String>();
    	
    	for (int i = 0; i < packages.size(); i++) {
    		JSONObject jsonPackage = (JSONObject) packages.get(i);
    		String name = (String) jsonPackage.get("name");
    		JSONArray dependencies = (JSONArray) jsonPackage.get("dependencies");
    		
    		String dep = dependencies.toString();
    		dep = dep.substring(1, dep.length() - 1).replace("\"", "");
    		String [] depList = dep.split(",");

    		packageList.add(name.toString());
    		for (int j = 0; j < depList.length; j++) {
    			tempList.add(depList[j]);
    		}
    		dependencyList.add(tempList);
    		tempList = new ArrayList<String>();
    	}
    	
    	independent = new ArrayList<String>();
    	    	
    	for (int i = 0; i < packageList.size(); i++) {
    		for (int m = 0; m < dependencyList.get(i).size(); m++) {
    			if (!dependencyList.get(i).get(m).equals("")) {
    				graph.addEdge(packageList.get(i), dependencyList.get(i).get(m));
    				graphR.addEdge(dependencyList.get(i).get(m), packageList.get(i));
    			} else {
    				graph.addVertex(packageList.get(i));
    				graphR.addVertex(packageList.get(i));
    				independent.add(packageList.get(i));
    			}
    		}
    	}
    	
    	ArrayList<String> all = new ArrayList<String>(graph.getAllVertices());
    	for (int i = 0; i < all.size(); i++) {
    		if (!packageList.contains(all.get(i)) && !independent.contains(all.get(i))) {
    			independent.add(all.get(i));
    		}
    	}
    	
    	packageList = new ArrayList<String>(graph.getAllVertices());
    	
    }
    
    /**
     * Helper method to get all packages in the graph.
     * 
     * @return Set<String> of all the packages
     */
    public Set<String> getAllPackages() {
    	return graph.getAllVertices();
    }
    
    /**
     * Given a package name, returns a list of packages in a
     * valid installation order.  
     * 
     * Valid installation order means that each package is listed 
     * before any packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the installation order for a particular package. Tip: Cycles in some other
     * part of the graph that do not affect the installation order for the 
     * specified package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the 
     * dependency graph.
     */
    public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {
        
        if (!packageList.contains(pkg)) {
    		throw new PackageNotFoundException();
    	}
    	
    	Stack<String> order = new Stack<String>();
    	order = installOrder(pkg, order);
    	
    	ArrayList<String> toReturn = new ArrayList<>(); 
    	int limit = order.size();
    	for (int i = 0; i < limit; i++) {
    		toReturn.add(order.pop());
    	}
    	
    	return toReturn;
    }
    
    /**
     * Recursive helper for the getInstallOrder method.
     * @param pkg
     * @param order
     * @return Stack<String>
     * @throws CycleException
     */
    private Stack<String> installOrder(String pkg, Stack<String> order) throws CycleException {
    	if (graph.getAdjacentVerticesOf(pkg).size() == 0) {
    		order.push(pkg);
    		return order;
    	} else {
    		ArrayList<String> adjacent = new ArrayList<>(graph.getAdjacentVerticesOf(pkg));
    		
    		if (order.contains(pkg)) {
    			throw new CycleException();
    		}
    		
    		order.push(pkg);
    		for (int i = 0; i < adjacent.size(); i++) {
    			installOrder(adjacent.get(i), order);
    		}
    		return order;
    	}
    }

    /**
     * Helper for the getInstallationOrderForAllPackages method.
     * Performs a depth-first search.
     * @param pkg
     * @throws CycleException
     */
    private void DFS (String pkg) throws CycleException {
    	ArrayList<String> all = new ArrayList<String>(graph.getAllVertices());
    	ArrayList<Boolean> bool = new ArrayList<Boolean>();
    	for (int i = 0; i < all.size(); i++) {
    		bool.add(false);
    	}
    	Stack<String> stack = new Stack<String>();
    	int index = all.indexOf(pkg);
    	stack.push(all.get(index));
    	
    	cycles(stack, index, bool, all);
    	
    	return;
    }
    
    /**
     * Recursive helper for the DFS method. Checks for cycles.
     * @param stack
     * @param index
     * @param bool
     * @param all
     * @throws CycleException
     */
    private void cycles(Stack<String> stack, int index, ArrayList<Boolean> bool, ArrayList<String> all) throws CycleException{
    	bool.set(index, true);
    	
    	ArrayList<String> successors = new ArrayList<String>(graph.getAdjacentVerticesOf(all.get(index)));
    	
    	for (int i = 0; i < successors.size(); i++) {
    		int value = all.indexOf(successors.get(i));
    		bool.set(value, true);
    		if (stack.contains(all.get(value))) {
    			throw new CycleException();
    		}
    		stack.push(all.get(value));
    		cycles(stack, value, bool, all);
    	}
    	
    	stack.pop();
    	return;
    	
    }
    
    /**
     * Given two packages - one to be installed and the other installed, 
     * return a List of the packages that need to be newly installed. 
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") 
     * If package A needs to be installed and packageB is already installed, 
     * return the list ["A", "C"] since D will have been installed when 
     * B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the dependencies of the given packages. If there is a cycle in some other
     * part of the graph that doesn't affect the parsing of these dependencies, 
     * cycle exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed 
     * do not exist in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
        
    	ArrayList<String> installed = new ArrayList<>(this.getInstallationOrder(installedPkg));
    	ArrayList<String> toInstall = new ArrayList<>(this.getInstallationOrder(newPkg));
    	ArrayList<String> toReturn = new ArrayList<>(this.getInstallationOrder(newPkg));
    	
    	for (int i = 0; i < toInstall.size(); i++) {
    		if (installed.contains(toInstall.get(i))) {
    			toReturn.remove(toInstall.get(i));
    		}
    	}
    	
    	return toReturn;
    }
    
    /**
     * Return a valid global installation order of all the packages in the 
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install 
     * all the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException {
        
        Stack<String> topOrder = new Stack<String>(); // topological order
    	ArrayList<String> allVertices = new ArrayList<String>(graphR.getAllVertices());
    	ArrayList<Boolean> visitedList = new ArrayList<>();
    	int num = graphR.order();
    	
    	for (int i = 0; i < allVertices.size(); i++) {
    		DFS(allVertices.get(i));
    	}
    	
    	for (int i = 0; i < num; i++) {
    	
    		if (independent.contains(allVertices.get(i))) {
    			visitedList.add(true);
        		topOrder.push(allVertices.get(i));
    		} else { 
    			visitedList.add(false);
    		}
    	}
    	
    	String [] toReturn = new String[num];
    	
    	while (!topOrder.isEmpty()) {
    		String peekString = topOrder.peek();
    		ArrayList<String> successors = new ArrayList<String>(graphR.getAdjacentVerticesOf(peekString));
    		
    		for (int i = 0; i < successors.size(); i++) {
    			int index = allVertices.indexOf(successors.get(i));
    			if (visitedList.get(index)) {
    				successors.remove(i);
    				i--;
    			}
    		}
    		
    		if (successors.isEmpty()) {
        		peekString = topOrder.pop();
        		toReturn[num - 1] = peekString;
        		num--;
    		} else {
    			int index = allVertices.indexOf(successors.get(0));
    			visitedList.set(index, true);
    			topOrder.push(allVertices.get(index));
    		}
    	}
    	
    	ArrayList<String> returnStr = new ArrayList<String>();
    	for (int i = 0; i < toReturn.length; i++) {
    		returnStr.add(toReturn[i]);
    	}
    	
    	return returnStr;
    }
    
    /**
     * Find and return the name of the package with the maximum number of dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file.  
     * The number of dependencies includes the dependencies of its dependencies.  
     * But, if a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.  
     * Then,  A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     */
    public String getPackageWithMaxDependencies() throws CycleException {
    	
    	ArrayList<String> all = new ArrayList<String>(graph.getAllVertices());
    	
    	int max = -1;
    	String maxStr = null;
    	
    	try {
    	for (int i = 0; i < all.size(); i++) {
    		int size = getInstallationOrder(all.get(i)).size();
    		if (size > max) {
    			max = size;
    			maxStr = all.get(i);
    		}
    	} } catch (Exception e) {}

       return maxStr;
        
    }
        
    public static void main (String [] args) {
        System.out.println("PackageManager.main()");
        PackageManager test = new PackageManager();
        
        try {
        	test.constructGraph("valid.json");
        	System.out.println(test.getInstallationOrder("A"));
        	System.out.println(test.getPackageWithMaxDependencies());
        	System.out.println(test.getInstallationOrderForAllPackages());
        	
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
}
