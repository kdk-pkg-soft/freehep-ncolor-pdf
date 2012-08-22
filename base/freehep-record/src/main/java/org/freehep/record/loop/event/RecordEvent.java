/*
 * class: RecordEvent
 *
 * Version $Id: RecordEvent.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event;

import org.freehep.record.loop.SequentialRecordLoop;

import java.util.EventObject;

/**
This class is used to notify {@link RecordListener} objects that there has
been a transition in a <code>{@link
org.freehep.record.loop.SequentialRecordLoop SequentialRecordLoop}</code>.
 *
 * @version $Id: RecordEvent.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class RecordEvent
		extends EventObject
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** The SequentialRecordLoop which is the source if it is one */
	private SequentialRecordLoop recordLoop;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private RecordEvent()
	{
		super(null);
	}

	/**
	 * Create an instance of this class with the specified Object as the
	 * source.
	 *
	 * @param source the object which is the source of this event.
	 */
	public RecordEvent(Object source)
	{
		super(source);
	}

	/**
	 * Create an instance of this class with the specified
	 * SequentialRecordLoop as the source.
	 *
	 * @param source the object which is the source of this event.
	 */
	public RecordEvent(SequentialRecordLoop source)
	{
		super(source);
		recordLoop = source;
	}

	// instance member function (alphabetic)

	/**
	 * Returns the SequentialRecordLoop which is the source of this event. If
	 * the source of this event is not a SequentialRecordLoop object then
	 * 'null' is returned.
	 *
	 * @return the SequentialRecordLoop which is the source of this event.
	 */
	public SequentialRecordLoop getRecordLoop()
	{
		return recordLoop;
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
