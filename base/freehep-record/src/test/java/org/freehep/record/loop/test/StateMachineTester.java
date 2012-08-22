/*
 * class: StateMachineTester
 *
 * Version $Id: StateMachineTester.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.test;

import org.freehep.record.loop.event.ConfigurationEvent;
import org.freehep.record.loop.event.RecordEvent;
import org.freehep.record.loop.event.RecordListener;
import org.freehep.record.loop.event.RecordSuppliedEvent;

import junit.framework.*;

/**
This class can be used to check that a <code>{@link
org.freehep.record.loop.SequentialRecordLoop SequentialRecordLoop}</code>
implementation follows the correct state machine.

 *
 * @version $Id: StateMachineTester.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class StateMachineTester
		implements RecordListener
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	/** An illegal state. */
	public static final byte ILLEGAL_STATE = 0;

	/** The object is contructed and ready for use. */
	public static final byte READY_STATE = ILLEGAL_STATE + 1;

	/** The object has been configured. */
	public static final byte CONFIGURED_STATE = READY_STATE + 1;

	/** The objects is processing records. */
	public static final byte PROCESSING_STATE = CONFIGURED_STATE + 1;

	/** The object has been suspended. */
	public static final byte SUSPENDED_STATE = PROCESSING_STATE + 1;

	// private static member data

	// private instance member data

	/** The current state of this class. */
	private byte state;

	// constructors

	/**
	 * Create an instance of this class.
	 */
	public StateMachineTester()
	{
		state = READY_STATE;
	}

	// instance member function (alphabetic)
	public void configure(ConfigurationEvent event)
	{
		Assert.assertTrue("Illegal configure transition",
				READY_STATE == state);
		setState(CONFIGURED_STATE);
	}

	public void finish(RecordEvent event)
	{
		Assert.assertTrue("Illegal finish transition",
				(SUSPENDED_STATE == state));
		setState(READY_STATE);
	}

	/**
	 * @return true if the state matches the specifed state
	 */
	protected boolean isState(byte state)
	{
		return state == this.state;
	}

	public void recordSupplied(RecordSuppliedEvent event)
	{
		Assert.assertTrue("Illegal recordSupplied transition",
				(CONFIGURED_STATE == state) ||
				(PROCESSING_STATE == state));
		setState(PROCESSING_STATE);
	}

	public void reconfigure(ConfigurationEvent event)
	{
		Assert.assertTrue("Illegal reconfigure transition",
				SUSPENDED_STATE == state);
		setState(CONFIGURED_STATE);
	}

	public void resume(RecordEvent event)
	{
		Assert.assertTrue("Illegal resume transition",
				SUSPENDED_STATE == state);
		setState(CONFIGURED_STATE);
	}

	/**
	 * Sets the state of the Checker
	 */
	protected void setState(byte state)
	{
		this.state = state;
	}

	public void suspend(RecordEvent event)
	{
		Assert.assertTrue("Illegal suspend transition",
				(CONFIGURED_STATE == state) ||
				(PROCESSING_STATE == state));
		setState(SUSPENDED_STATE);
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
