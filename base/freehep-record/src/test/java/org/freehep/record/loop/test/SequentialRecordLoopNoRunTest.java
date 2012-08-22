/*
 * class: SequentialRecordLoopNoRunTest
 *
 * Version $Id: SequentialRecordLoopNoRunTest.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.test;

import org.freehep.record.loop.LoopException;
import org.freehep.record.loop.LoopSourceExhaustedException;
import org.freehep.record.loop.SequentialRecordLoop;
import org.freehep.record.loop.event.ConfigurationEvent;
import org.freehep.record.loop.event.RecordSuppliedEvent;
import org.freehep.record.source.EndOfSourceException;
import org.freehep.record.source.SequentialRecordSource;

import junit.framework.*;

import java.io.IOException;
import java.util.TooManyListenersException;

/**
 * This class defines the tests that any SequentialRecordLoop object should pass.
 *
 * @version $Id: SequentialRecordLoopNoRunTest.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public abstract class SequentialRecordLoopNoRunTest
		extends TestCase
{

	// public static final member data

	// protected static final member data

	protected static final Object[] TEST_OBJECTS = new Object[]{
		new Object(),
		new Object(),
		new Object(),
		new Object(),
		new Object()
	};

	protected static final boolean[] TEST_MIXED_RECORDS = new boolean[]{
		false,
		true,
		true,
		false,
		true,
	};

	protected static final long TEST_MIXED_EVENT_COUNT = 3;

	// static final member data

	// private static final member data

	private static final Object TEST_CONFIGURATION =
			new Object();

	private static final Object TEST_RECONFIGURATION =
			new Object();

	// private static member data

	// private instance member data

	/** The object being tested. */
	private SequentialRecordLoop testObject;

	/** The listener being used in testing. */
	private MockListener listener;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private SequentialRecordLoopNoRunTest()
	{
		super(null);
	}

	/**
	 * Constructs and instance of this test.
	 * @param name
	 */
	public SequentialRecordLoopNoRunTest(String name)
	{
		super(name);
	}

	// instance member function (alphabetic)

	/**
	 * Sets the object to be tested.
	 *
	 * @param testObject object to be tested.
	 */
	protected void setRecordLoop(SequentialRecordLoop testObject)
	{
		this.testObject = testObject;
		testObject.setRecordSource(new MockRecordSource(TEST_OBJECTS));
	}

	/**
	 * Sets up the fixture, for example, open a network connection.
	 * This method is called before a test is executed.
	 */
	protected void setUp()
	{
		testObject.setConfiguration(TEST_CONFIGURATION);
	}

	/**
	 * Tears down the fixture, for example, close a network connection.
	 * This method is called after a test is executed.
	 */
	protected void tearDown()
	{
		testObject = null;
	}

	/**
	 * Test that the configure message is correctly handled.
	 */
	public void testConfigure()
	{
		testSuspend();
	}

	/**
	 * Test that default sequence runs correctly.
	 */
	public void testDefaultSequence()
	{
		testListenerAdd();
		try {
			testObject.loop(-1);
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" +
					e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Test that it is not possible to add two RecordListener this object.
	 */
	public void testDoubleAdd()
	{
		testListenerAdd();
		try {
			testObject.addRecordListener(new MockListener());
			fail("Was able to add two RecordListener objects");
		} catch (TooManyListenersException e) {
		}
	}

	/**
	 * Test that the end of source condition is correctly reported.
	 */
	public void testEndOfSource()
	{
		testListenerAdd();
		try {
			testObject.loop(TEST_OBJECTS.length + 1);
			fail("LoopSourceExhaustedException was not thrown!");
		} catch (LoopException e1) {
			if (e1 instanceof LoopSourceExhaustedException) {
			} else {
				fail("Threw LoopException Exception\n" + e1.toString());
			}
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Test that the finish message is correctly handled.
	 */
	public void testFinish()
	{
		byte[] stateSequence = {StateMachineTester.READY_STATE,
								StateMachineTester.CONFIGURED_STATE,
								StateMachineTester.SUSPENDED_STATE,
								StateMachineTester.READY_STATE};

		testListenerAdd();
		listener.setStates(stateSequence);
		try {
			testObject.loop(0);
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
		testObject.dispose();
	}

	/**
	 * Test that this object can be interrupted. This is dependent on the
	 * implementation of the SequentialRecordLoop so must be provided by its test class.
	 */
	public abstract void testInterrupt();

	/**
	 * Test that the interrupt flag gets cleared after an interrupt has
	 * been serviced.
	 */
	public void testInterruptCleared()
	{
		testInterrupt();
		assertTrue(!testObject.isInterruptRequested());
	}

	/**
	 * Test that an RecordListener can be added to this object.
	 */
	public void testListenerAdd()
	{
		listener = new MockListener();
		try {
			testObject.addRecordListener(listener);
		} catch (TooManyListenersException e) {
			fail("Threw TooManyListener Exception\n" + e.toString());
		}
	}

	/**
	 * Test that an RecordListener can be removed from this object.
	 */
	public void testListenerRemove()
	{
		testListenerAdd();
		testObject.removeRecordListener(listener);
		try {
			testObject.addRecordListener(new MockListener());
		} catch (TooManyListenersException e) {
			fail("Threw TooManyListener Exception\n" + e.toString());
		}
	}

	/**
	 * Test that the event count for mixed records correct.
	 */
	public void testMixedCount()
	{
		testListenerAdd();
		listener.setRecords(TEST_OBJECTS, TEST_MIXED_RECORDS);
		try {
			long result = testObject.loop(TEST_MIXED_EVENT_COUNT);
			assertEquals(TEST_MIXED_EVENT_COUNT, result);
			assertEquals(TEST_OBJECTS.length, testObject.getTotalSupplied());
			assertEquals(TEST_MIXED_EVENT_COUNT,
					testObject.getTotalCountableSupplied());
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Test that the event count for pure records correct.
	 */
	public void testPureCount()
	{
		testListenerAdd();
		listener.setRecords(TEST_OBJECTS);
		try {
			long result = testObject.loop(TEST_OBJECTS.length);
			assertEquals(TEST_OBJECTS.length, result);
			assertEquals(TEST_OBJECTS.length, testObject.getTotalSupplied());
			assertEquals(TEST_OBJECTS.length,
					testObject.getTotalCountableSupplied());
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Test that the recordSupplied message is correctly handled.
	 */
	public void testRecordSupplied()
	{
		byte[] stateSequence = {StateMachineTester.READY_STATE,
								StateMachineTester.CONFIGURED_STATE,
								StateMachineTester.PROCESSING_STATE,
								StateMachineTester.SUSPENDED_STATE};

		testListenerAdd();
		listener.setStates(stateSequence);
		try {
			testObject.loop(1);
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Test that the reconfigure message is correctly handled.
	 */
	public void testReconfigure()
	{
		byte[] stateSequence = {StateMachineTester.READY_STATE,
								StateMachineTester.CONFIGURED_STATE,
								StateMachineTester.PROCESSING_STATE,
								StateMachineTester.SUSPENDED_STATE,
								StateMachineTester.CONFIGURED_STATE,
								StateMachineTester.SUSPENDED_STATE};

		testListenerAdd();
		listener.setStates(stateSequence);
		try {
			testObject.loop(1);
			testObject.setConfiguration(TEST_RECONFIGURATION);
			testObject.loop(0);
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Test test the null Listener can be handled.
	 */
	public void testNullListener()
	{
		try {
			testObject.loop(-1);
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
		assertEquals(TEST_OBJECTS.length,
				testObject.getTotalCountableSupplied());
	}

	/**
	 * Test that removal of the null RecordListener does nothing wrong.
	 */
	public void testNullListenerRemove()
	{
		testListenerAdd();
		testObject.removeRecordListener(null);
	}

	/**
	 * Test that the resume message is correctly handled.
	 */
	public void testResume()
	{
		byte[] stateSequence = {StateMachineTester.READY_STATE,
								StateMachineTester.CONFIGURED_STATE,
								StateMachineTester.PROCESSING_STATE,
								StateMachineTester.SUSPENDED_STATE,
								StateMachineTester.CONFIGURED_STATE,
								StateMachineTester.SUSPENDED_STATE};

		testListenerAdd();
		listener.setStates(stateSequence);
		try {
			testObject.loop(1);
			testObject.loop(0);
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Test that the suspend message is correctly handled.
	 */
	public void testSuspend()
	{
		byte[] stateSequence = {StateMachineTester.READY_STATE,
								StateMachineTester.CONFIGURED_STATE,
								StateMachineTester.SUSPENDED_STATE};

		testListenerAdd();
		listener.setStates(stateSequence);
		try {
			testObject.loop(0);
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Test that removal of the wrong RecordListener does nothing wrong.
	 */
	public void testWrongListenerRemove()
	{
		testListenerAdd();
		testObject.removeRecordListener(new MockListener());
	}

	// static member functions (alphabetic)

	protected class MockRecordSource
			implements SequentialRecordSource
	{
		/** The sequence of object to serve out. */
		private Object[] objects;

		/** Index the the current object in the sequence */
		private int objectIndex = -1;

		/**
		 * @param records an sequence of object to serve out.
		 */
		MockRecordSource(Object[] objects)
		{
			this.objects = objects;
		}

		public Class getRecordClass()
		{
			return Object.class;
		}

		public Object getCurrentRecord()
				throws EndOfSourceException
		{
			if (objectIndex >= objects.length) {
				throw new EndOfSourceException("Ran out of records in array");
			}

			Object object = null;
			if (null != objects) {
				object = objects[objectIndex];
			}
			return object;
		}

		public long getEstimatedSize()
		{
			return objects.length;
		}

		public String getSourceName()
		{
			return "MockSource";
		}

		public void next()
		{
			objectIndex++;
		}

		public void releaseRecord(Object object)
		{
		}

		public void rewind()
		{
			objectIndex = 0;
		}
                
                public void close() throws IOException {
                }
                
	}

	private class MockListener
			extends StateMachineTester
	{
		/** The sequence of expected states. */
		private byte[] states;

		/** Index the the current state in its sequence */
		private int stateIndex;

		/** The sequence of expected records. */
		private Object[] records;

		/** True if the record with the matching index should be counted. */
		private boolean[] countibility;

		/** Index the the current Object in its sequence */
		private int recordIndex;

		public void configure(ConfigurationEvent event)
		{
			Assert.assertEquals(TEST_CONFIGURATION,
					event.getConfiguration());
			super.configure(event);
		}

		public void recordSupplied(RecordSuppliedEvent event)
		{
			if (null != records) {
				Assert.assertSame(records[recordIndex],
						event.getRecord());
				if ((null != countibility) && (!countibility[recordIndex])) {
					event.getRecordLoop().doNotCount(event.getRecord());
				}

				recordIndex++;
			}
			super.recordSupplied(event);
		}

		public void reconfigure(ConfigurationEvent event)
		{
			Assert.assertEquals(TEST_RECONFIGURATION,
					event.getConfiguration());
			super.reconfigure(event);
		}

		/**
		 * @param sets the sequence of expected records.
		 */
		public void setRecords(Object[] records)
		{
			this.records = records;
		}

		/**
		 * @param sets the sequence of expected records with their associated
		 * countablilty flags.
		 */
		public void setRecords(Object[] records, boolean[] countability)
		{
			this.records = records;
			this.countibility = countability;
		}

		/**
		 * @param sets the sequence of expected transitions.
		 */
		public void setStates(byte[] states)
		{
			this.states = states;
		}

		/**
		 * Check each transition matches the set list.
		 */
		protected void setState(byte state)
		{
			if (null != states) {
				Assert.assertTrue(isState(states[stateIndex]));
				stateIndex++;
				Assert.assertEquals(state,
						states[stateIndex]);
			}
			super.setState(state);
		}
	}

	/**
	 * Create test suite for this class.
	 */
	public static Test
			suite()
	{
		return new TestSuite(SequentialRecordLoopNoRunTest.class);
	}

	// Description of this object.
	// public String toString() {}
}
