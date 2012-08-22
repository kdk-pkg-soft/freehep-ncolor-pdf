/*
 * class: LoopEvent
 *
 * Version $Id: LoopProgressEvent.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: March 7 2003
 *
 * (c) 2003 IceCube Collaboration
 */

package org.freehep.record.loop.event;

import java.util.EventObject;

/**
This class is used to update {@link RecordLoopListener} objects on the progress
of a <code>{@link
org.freehep.record.loop.SequentialRecordLoop SequentialRecordLoop}</code>.
 *
 * @version $Id: LoopProgressEvent.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class LoopProgressEvent
		extends EventObject
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** The fraction of a loop that has been completed. */
	private double fraction;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private LoopProgressEvent()
	{
		super(null);
	}

	/**
	 * Create an instance of this class with the specified progress.
	 *
	 * @param source the SequentialRecordLoop object which generated this event.
	 * @param fraction the fraction of a loop that has been completed.
	 */
	public LoopProgressEvent(Object source,
							 double fraction)
	{
		super(source);
		this.fraction = fraction;
	}

	// instance member function (alphabetic)

	/**
	 * Returns the fraction of the loop that has been completed.
	 *
	 * @return the fraction of the loop that has been completed.
	 */
	public double getFraction()
	{
		return fraction;
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
