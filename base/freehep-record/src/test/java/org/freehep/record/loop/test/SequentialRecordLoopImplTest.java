/*
 * class: SequentialRecordLoopImplTest
 *
 * Version $Id: SequentialRecordLoopImplTest.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 10 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.test;

import org.freehep.record.loop.LoopException;
import org.freehep.record.loop.LoopInterruptedException;
import org.freehep.record.loop.SequentialRecordLoopImpl;
import org.freehep.record.loop.event.RecordAdapter;
import org.freehep.record.loop.event.RecordSuppliedEvent;
import org.freehep.record.loop.event.RecordLoopAdapter;
import org.freehep.record.loop.event.LoopProgressEvent;

import junit.framework.*;

import java.io.IOException;
import java.util.TooManyListenersException;

/**
 * This class defines the tests that any SequentialRecordLoopImpl object should pass.
 *
 * @version $Id: SequentialRecordLoopImplTest.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class SequentialRecordLoopImplTest
		extends SequentialRecordLoopNoRunTest
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	private static final int EVENT_PAUSE = 1000;

	private static final int INTERRUPT_COUNT = 3;

	private static final long PROGESS_BY_RECORDS = (long) 2;
//	private static final long PROGESS_BY_TIME = (long) EVENT_PAUSE * 2;
	private static final double[] PROGRESS_FRACTIONS =
			new double[]{ 0.0, 0.4, 0.8, 1.0 };

	// private static member data

	// private instance member data

	/** The object being tested. */
	private SequentialRecordLoopImpl testObject;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private SequentialRecordLoopImplTest()
	{
		super(null);
	}

	/**
	 * Constructs and instance of this test.
	 * @param name
	 */
	public SequentialRecordLoopImplTest(String name)
	{
		super(name);
	}

	// instance member function (alphabetic)

	/**
	 * Sets up the fixture, for example, open a network connection.
	 * This method is called before a test is executed.
	 */
	protected void setUp()
	{
		testObject = new SequentialRecordLoopImpl();
		setRecordLoop(testObject);
		super.setUp();
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
	 * Test that this object can be interrupted.
	 */
	public void testInterrupt()
	{
		try {
			testObject.addRecordListener(new MockPausingListener());
		} catch (TooManyListenersException e) {
			fail("Threw TooManyListener Exception\n" + e.toString());
		}
		MockInterruptor interruptor = new MockInterruptor();
		interruptor.start();
		try {
			testObject.loop(-1);
			fail("Loop was not interrupted");
		} catch (LoopException e1) {
			if (e1 instanceof LoopInterruptedException) {
			} else {
				fail("Threw LoopException Exception\n" + e1.toString());
			}
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Tests that the progress reports are being made.
	 */
	public void testProgessByRecord()
	{
		testListenerAdd();
		testObject.addRecordLoopListener(
				new MockLoopListener(PROGRESS_FRACTIONS));
		testObject.setProgessByRecords(PROGESS_BY_RECORDS);
		try {
			/* long result = */ testObject.loop(-1);
//            System.out.println(result);
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	/**
	 * Tests that the progress reports are being made.
	 */
	public void testProgessByTime()
	{
		testObject.addRecordLoopListener(
				new MockLoopListener(PROGRESS_FRACTIONS));
		testObject.setProgessByRecords(PROGESS_BY_RECORDS);
		try {
			testObject.addRecordListener(new MockPausingListener());
			/* long result = */ testObject.loop(-1);
//            System.out.println(result);
		} catch (TooManyListenersException e) {
			fail("Threw TooManyListener Exception\n" + e.toString());
		} catch (LoopException e1) {
			fail("Threw LoopException Exception\n" + e1.toString());
		} catch (IOException e2) {
			fail("Threw IOException Exception\n" + e2.toString());
		}
	}

	// static member functions (alphabetic)

	private class MockInterruptor
			extends Thread
	{
		public void run()
		{
			synchronized (this) {
				try {
					wait(EVENT_PAUSE * INTERRUPT_COUNT);
				} catch (InterruptedException e) {
					fail("Threw InterruptedException Exception\n" +
							e.toString());
				}
			}
			testObject.setInterruptRequested(true);
		}
	}

	private class MockLoopListener
			extends RecordLoopAdapter
	{
		private final static double DOUBLE_DELTA = (double) 0.001;

		private double[] fractions;

		private int fractionIndex;

		private MockLoopListener(double[] fractions)
		{
			this.fractions = fractions;
		}

		public void progress(LoopProgressEvent loopProgressEvent)
		{
			assertEquals(fractions[fractionIndex],
					loopProgressEvent.getFraction(),
					DOUBLE_DELTA);
			fractionIndex++;
		}
	}

	private class MockPausingListener
			extends RecordAdapter
	{
		public void recordSupplied(RecordSuppliedEvent event)
		{
			synchronized (this) {
				try {
					wait(EVENT_PAUSE);
				} catch (InterruptedException e) {
					fail("Threw InterruptedException Exception\n" +
							e.toString());
				}
			}
			super.recordSupplied(event);
		}
	}

	/**
	 * Create test suite for this class.
	 */
	public static Test suite()
	{
		return new TestSuite(SequentialRecordLoopImplTest.class);
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
