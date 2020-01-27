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

import org.junit.jupiter.api.Test;
//
//Title: 		P4 Package Manager
//Course: 		CS 400, Fall , 2019
//
//Author: 		Yash Sukhwani
//Email:  		sukhwani@wisc.edu
//Lecturer: 	Deb Deppler
//Due Date: 	Nov 14, 2019
//
//Known Bugs:	None
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Students who get help from sources other than their partner must fully
//acknowledge and credit those sources of help here. Instructors and TAs do
//not need to be credited here, but tutors, friends, relatives, room mates,
//strangers, and others do. If you received no outside help from either type
//of source, then please explicitly indicate NONE.
//
//Persons: NO PERSONS
//Online Sources: NO SOURCES
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PackageManagerTest {

// field that will be used to test the class
	PackageManager packagemanager;

	/**
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
// inialize field to a new PackageManager
		packagemanager = new PackageManager();
// construct new graph with my file path
		packagemanager.constructGraph("/Users/yashsukhwani/Documents/Eclipse Projects/P4 Package Manager/test.json");
	}

	/**
	 * @throws Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
// set field to null
		packagemanager = null;
	}

	/**
	 * size install checker
	 */
	@Test
	void test00_toInstall_size() {
// create a new field
		ArrayList<String> pkg;
		try {
// set field to hold the values returned from the method call
			pkg = new ArrayList<String>(packagemanager.toInstall("A", "B"));

// if the size of packages is not 1, fail the test
			if (pkg.size() != 1) {
				fail("Should be 1");
			}
// if the exceptions are thrown, fail the test
		} catch (CycleException | PackageNotFoundException e) {
			fail("Should not be thrown");
		}
	}

	/**
	 * tests getting all packages
	 * 
	 */
	@Test
	void test01_getAllPackages_size() {
		ArrayList<String> packageManageList = new ArrayList<String>(packagemanager.getAllPackages());

		if (packageManageList.size() != 5) {
			fail("value qat this point should be equal to 5");
		}
	}

	/**
	 * tests getting order for all packages
	 */
	@Test
	void test02_getInstallationOrderForAllPackages_size() {
// create a new field
		ArrayList<String> packageList;
		try {
			packageList = new ArrayList<String>(packagemanager.getInstallationOrderForAllPackages());

			if (packageList.size() != 5) {
				fail("ERROR : value Should be equal 5");
			}
		} catch (Exception e) {
			fail("exception caught when not expected");
		}
	}

	/**
	 * Method to confirm if getInstallationOrderForAllPackages returns the correct
	 * list
	 */
	@Test
	void test03_getInstallationOrderForAllPackages_position() {
// create a new field
		ArrayList<String> packageList;
		try {
// set field to hold the values returned from the method call
			packageList = new ArrayList<String>(packagemanager.getInstallationOrderForAllPackages());

			if (!packageList.get(0).equals("C") || !packageList.get(1).equals("D") || !packageList.get(2).equals("B")
					|| !packageList.get(3).equals("A") || !packageList.get(4).equals("F")) {
				fail("Not in correct position");
			}
		} catch (Exception e) {
			fail("Unexpected exception caught");
		}
	}

	/**
	 * Checks whether test06_getPackageWithMaxDependencies returns the correct and
	 * expected package.
	 */
	@Test
	void test04_checks_method_getPackageWithMaxDependencies() {
		try {

			String packageName = packagemanager.getPackageWithMaxDependencies();

// checks if it returns the fail condition
			if (!packageName.equals("F")) {

				fail("Incorrect package, Expected 'F.");
			}

// checks whether it doesn't throw an exception
		} catch (CycleException e) {

			fail("Exception was not expected to be thrown");
		}

	}

	/**
	 * Checks if this method returns the correct list and order
	 */
	@Test
	void test05_getInstallationOrder() {

		ArrayList<String> test_package;

		try {
// assigns the returned value
			test_package = new ArrayList<String>(packagemanager.getInstallationOrder("A"));

// fails if the size returned isn't 4
			if (test_package.size() != 4) {
				fail("Size should have been 4 but wasn't");
			}

// if any exceptions are thrown, it fails the test.
		} catch (Exception e) {
			fail("No exceptions should have been thrown");
		}
	}

	/**
	 * Test to check if an invalid file path is constructed
	 */
	@Test
	void test06_constructGraph_invalid_filepath() {

		try {
// tries to construct through an invalid file path
			packagemanager.constructGraph("failPath");

// checks if correct exception is thrown
		} catch (FileNotFoundException e) {
			return;

// fails the test if any other exception is thrown
		} catch (Exception e) {
			fail("No other exception other than FileNotFoundException should have been thrown ");
		}
	}
}
