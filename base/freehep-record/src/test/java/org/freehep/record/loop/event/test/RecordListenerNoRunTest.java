/*
 * class: RecordListenerNoRunTest
 *
 * Version $Id: RecordListenerNoRunTest.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event.test;

import org.freehep.record.loop.event.ConfigurationEvent;
import org.freehep.record.loop.event.RecordEvent;
import org.freehep.record.loop.event.RecordListener;
import org.freehep.record.loop.event.RecordSuppliedEvent;

import junit.framework.*;

/**
 * This class defines the tests that any RecordListener object should pass.
 *
 * @version $Id: RecordListenerNoRunTest.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public abstract class RecordListenerNoRunTest
		extends TestCase
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** The object being tested. */
	private RecordListener testObject;

	/** The configuration object for any configure or reconfigure message. */
	private Object configuration;

	/** The record object for any recordSupplied message. */
	private Object record;

	/** Standard RecordEvent for this object to send out. */
	private final RecordEvent recordEvent;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private RecordListenerNoRunTest()
	{
		this(null);
	}

	/**
	 * Constructs and instance of this test.
	 * @param name
	 */
	public RecordListenerNoRunTest(String name)
	{
		super(name);
		recordEvent = new RecordEvent(this);
	}

	// instance member function (alphabetic)

	/**
	 * @return the configuration object for any configure or reconfigure
	 * message.
	 */
	protected Object getConfiguration()
	{
		return configuration;
	}

	/**
	 * @return the record for any nrecordSupplied message.
	 */
	protected Object getRecord()
	{
		return record;
	}

	/**
	 * Prepare the test for a configure transition
	 */
	protected abstract void prepareConfiguration();

	/**
	 * Prepare the test for a configure transition
	 */
	protected abstract void prepareRecordSupplied();

	/**
	 * Prepare the test for a reconfigure transition
	 */
	protected abstract void prepareReconfiguration();

	/**
	 * Sets the object to be tested.
	 *
	 * @param testObject object to be tested.
	 */
	protected void setRecordListener(RecordListener testObject)
	{
		this.testObject = testObject;
	}

	/**
	 * @param the configuration object for a configure or reconfigure
	 * transition.
	 */
	protected void setConfiguration(final Object configuration)
	{
		this.configuration = configuration;
	}

	/**
	 * @param the record for any recordSuppiled transition.
	 */
	protected void setRecord(Object record)
	{
		this.record = record;
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
	 * Test the transition from Ready to Configured
	 */
	public void testConfigureFromReady()
	{
		prepareConfiguration();
		testObject.configure(new ConfigurationEvent(this,
				configuration));
	}

	/**
	 * Test the transition from Configured to Finished
	 */
	public void testFinishFromSuspended()
	{
		testSuspendFromProcessing();

		testObject.finish(recordEvent);
	}

	/**
	 * Test the transition from Configured to Processing Frames
	 */
	public void testProcessingFromConfigured()
	{
		testConfigureFromReady();

		prepareRecordSupplied();
		testObject.recordSupplied(new RecordSuppliedEvent(this,
				record));
	}

	/**
	 * Test the transition from Processing Frames to Processing Frames
	 */
	public void testProcessingFromProcessing()
	{
		testProcessingFromConfigured();

		prepareRecordSupplied();
		testObject.recordSupplied(new RecordSuppliedEvent(this,
				record));
	}

	/**
	 * Test the transition from Suspended to Configured
	 */
	public void testReconfigureFromSuspended()
	{
		testSuspendFromProcessing();

		prepareReconfiguration();
		testObject.reconfigure(new ConfigurationEvent(this,
				configuration));
	}

	/**
	 * Test the transition from Suspended to Configured
	 */
	public void testResumeFromSuspended()
	{
		testSuspendFromProcessing();

		testObject.resume(recordEvent);
	}

	/**
	 * Test the transition from Configured to Suspended
	 */
	public void testSuspendFromConfigured()
	{
		testConfigureFromReady();

		testObject.suspend(recordEvent);
	}

	/**
	 * Test the transition from Processing Frames to Suspended
	 */
	public void testSuspendFromProcessing()
	{
		testProcessingFromConfigured();

		testObject.suspend(recordEvent);
	}

	// static member functions (alphabetic)

	/**
	 * Create test suite for this class.
	 */
	public static Test suite()
	{
		return new TestSuite(RecordListenerNoRunTest.class);
	}

	// Description of this object.
	// public String toString() {}
}
