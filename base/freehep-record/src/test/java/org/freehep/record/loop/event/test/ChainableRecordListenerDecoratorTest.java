/*
 * class: ChainableRecordListenerDecoratorTest
 *
 * Version $Id: ChainableRecordListenerDecoratorTest.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 19 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event.test;

import org.freehep.record.loop.event.ChainableRecordListenerDecorator;
import org.freehep.record.loop.event.ConfigurationEvent;
import org.freehep.record.loop.event.RecordEvent;
import org.freehep.record.loop.event.RecordListener;
import org.freehep.record.loop.event.RecordSuppliedEvent;

import junit.framework.*;

import java.util.TooManyListenersException;

/**
 * This class defines the tests that any ChainableRecordListener object should
 * pass.
 *
 * @version $Id: ChainableRecordListenerDecoratorTest.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class ChainableRecordListenerDecoratorTest
		extends RecordListenerNoRunTest
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	/** The configuration object to use. */
	private static final Object TEST_CONFIGURATION =
			new Object();

	/** The record object to use. */
	private static final Object TEST_RECORD = new Object();

	/** The reconfiguration object to use. */
	private static final Object TEST_RECONFIGURATION =
			new Object();

	/** Used to record last MockListener that was executed. */
	private int lastId;

	// private instance member data

	/** The object being tested. */
	private ChainableRecordListenerDecorator testObject;

	/** The object which succeeds the listener in the test object. */
	private RecordListener successor;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private ChainableRecordListenerDecoratorTest()
	{
		this(null);
	}

	/**
	 * Constructs and instance of this test.
	 * @param name
	 */
	public ChainableRecordListenerDecoratorTest(String name)
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
		testObject = new ChainableRecordListenerDecorator(new MockListener(1, 0));
		setRecordListener(testObject);
		successor = new ChainableRecordListenerDecorator(new MockListener(2, 1));
		try {
			testObject.addRecordListener(successor);
		} catch (TooManyListenersException e) {
			// Ignored as can not happen.
		}
	}

	/**
	 * Tears down the fixture, for example, close a network connection.
	 * This method is called after a test is executed.
	 */
	protected void tearDown()
	{
		setRecordListener(null);
		testObject = null;
	}

	public void testConfigureFromReady()
	{
		super.testConfigureFromReady();
		lastId = 0;
	}

	public void testFinishFromSuspended()
	{
		super.testFinishFromSuspended();
		lastId = 0;
	}

	public void testProcessingFromConfigured()
	{
		super.testProcessingFromConfigured();
		lastId = 0;
	}

	public void testProcessingFromProcessing()
	{
		super.testProcessingFromProcessing();
		lastId = 0;
	}

	public void testReconfigureFromSuspended()
	{
		super.testReconfigureFromSuspended();
		lastId = 0;
	}

	public void testResumeFromSuspended()
	{
		super.testResumeFromSuspended();
		lastId = 0;
	}

	public void testSuspendFromConfigured()
	{
		super.testSuspendFromConfigured();
		lastId = 0;
	}

	public void testSuspendFromProcessing()
	{
		super.testSuspendFromProcessing();
		lastId = 0;
	}
	// static member functions (alphabetic)

	private class MockListener
			implements RecordListener
	{
		private int id;
		private int precedingId;

		public MockListener(int id,
							int precedingId)
		{
			this.id = id;
			this.precedingId = precedingId;
		}

		public void configure(ConfigurationEvent event)
		{
			Assert.assertEquals(lastId, precedingId);
			Assert.assertEquals(TEST_CONFIGURATION,
					event.getConfiguration());
			lastId = id;
		}

		public void finish(RecordEvent event)
		{
			Assert.assertEquals(lastId, precedingId);
			lastId = id;
		}

		public void recordSupplied(RecordSuppliedEvent event)
		{
			Assert.assertEquals(lastId, precedingId);
			Assert.assertEquals(TEST_RECORD,
					event.getRecord());
			lastId = id;
		}

		public void reconfigure(ConfigurationEvent event)
		{
			Assert.assertEquals(lastId, precedingId);
			Assert.assertEquals(TEST_RECONFIGURATION,
					event.getConfiguration());
			lastId = id;
		}

		public void resume(RecordEvent event)
		{
			Assert.assertEquals(lastId, precedingId);
			lastId = id;
		}

		public void suspend(RecordEvent event)
		{
			Assert.assertEquals(lastId, precedingId);
			lastId = id;
		}
	}

	/**
	 * Create test suite for this class.
	 */
	public static Test suite()
	{
		return new TestSuite(ChainableRecordListenerDecoratorTest.class);
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
