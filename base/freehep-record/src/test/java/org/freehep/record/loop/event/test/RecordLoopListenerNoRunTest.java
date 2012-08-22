/*
 * class: RecordLoopListenerNoRunTest
 *
 * Version $Id: RecordLoopListenerNoRunTest.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: March 7 2003
 *
 * (c) 2003 IceCube Collaboration
 */

package org.freehep.record.loop.event.test;

import org.freehep.record.loop.event.RecordLoopListener;

import junit.framework.*;

/**
 * This class defines the tests that any RecordLoopListener object should pass.
 *
 * @version $Id: RecordLoopListenerNoRunTest.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class RecordLoopListenerNoRunTest
		extends TestCase
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** The object being tested. */
	protected RecordLoopListener testObject;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private RecordLoopListenerNoRunTest()
	{
		this(null);
	}

	/**
	 * Constructs and instance of this test.
	 * @param name
	 */
	public RecordLoopListenerNoRunTest(String name)
	{
		super(name);
	}

	// instance member function (alphabetic)

	/**
	 * Sets the object to be tested.
	 *
	 * @param testObject object to be tested.
	 */
	protected void setRecordLoopListener(RecordLoopListener testObject)
	{
		this.testObject = testObject;
	}

	/**
	 * Sets up the fixture, for example, open a network connection.
	 * This method is called before a test is executed.
	 */
//    protected void setUp()
//    {
//    }

	/**
	 * Tears down the fixture, for example, close a network connection.
	 * This method is called after a test is executed.
	 */
	protected void tearDown()
	{
		testObject = null;
	}

	/**
	 * Explanation of test.
	 */
	public void testSomething()
	{
// MD: we cannot test this unless we have a listener
//        assertNotNull(testObject);
	}

	// static member functions (alphabetic)

	/**
	 * Create test suite for this class.
	 */
	public static Test suite()
	{
		return new TestSuite(RecordLoopListenerNoRunTest.class);
	}

	// Description of this object.
	// public String toString() {}
}
