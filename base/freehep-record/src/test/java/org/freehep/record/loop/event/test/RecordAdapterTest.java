/*
 * class: RecordAdapterTest
 *
 * Version $Id: RecordAdapterTest.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 10 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event.test;

import org.freehep.record.loop.event.RecordAdapter;

import junit.framework.*;

/**
 * This class defines the tests that any RecordAdapter object should pass.
 *
 * @version $Id: RecordAdapterTest.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class RecordAdapterTest
		extends RecordListenerNoRunTest
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	/** The configuration object to use. */
	private static final Object TEST_CONFIGURATION =
			new Object();

	/** The record object to use. */
	private static final Object TEST_RECORD = new Object();

	/** The reconfiguration object to use. */
	private static final Object TEST_RECONFIGURATION =
			new Object();

	// private static member data

	// private instance member data

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private RecordAdapterTest()
	{
		this(null);
	}

	/**
	 * Constructs and instance of this test.
	 * @param name
	 */
	public RecordAdapterTest(String name)
	{
		super(name);
	}

	// instance member function (alphabetic)
	protected void prepareConfiguration()
	{
		setConfiguration(TEST_CONFIGURATION);
	}

	protected void prepareRecordSupplied()
	{
		setRecord(TEST_RECORD);
	}

	protected void prepareReconfiguration()
	{
		setConfiguration(TEST_RECONFIGURATION);
	}

	/**
	 * Sets up the fixture, for example, open a network connection.
	 * This method is called before a test is executed.
	 */
	protected void setUp()
	{
		setRecordListener(new RecordAdapter());
	}

	/**
	 * Tears down the fixture, for example, close a network connection.
	 * This method is called after a test is executed.
	 */
	protected void tearDown()
	{
		setRecordListener(null);
	}

	// static member functions (alphabetic)

	/**
	 * Create test suite for this class.
	 */
	public static Test suite()
	{
		return new TestSuite(RecordAdapterTest.class);
	}

	// Description of this object.
	// public String toString() {}

	/**
	 * Main routine which runs text test in standalone mode.
	 */
	public static void main(String args[])
	{
		junit.textui.TestRunner.run(suite());
	}
}
