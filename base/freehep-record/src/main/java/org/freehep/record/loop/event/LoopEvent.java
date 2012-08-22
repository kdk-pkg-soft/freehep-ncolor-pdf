/*
 * class: LoopEvent
 *
 * Version $Id: LoopEvent.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: March 7 2003
 *
 * (c) 2003 IceCube Collaboration
 */

package org.freehep.record.loop.event;

import java.util.EventObject;

/**
This class is used to notify {@link RecordLoopListener} objects that there has
been a transition in a <code>{@link
org.freehep.record.loop.SequentialRecordLoop SequentialRecordLoop}</code>.
 *
 * @version $Id: LoopEvent.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class LoopEvent
		extends EventObject
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private LoopEvent()
	{
		super(null);
	}

	/**
	 * Create an instance of this class.
	 *
	 * @param source the SequentialRecordLoop object which generated this event.
	 */
	public LoopEvent(Object source)
	{
		super(source);
	}

	// instance member function (alphabetic)

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
