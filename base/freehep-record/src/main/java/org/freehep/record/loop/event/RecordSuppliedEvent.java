/*
 * class: RecordSuppliedEvent
 *
 * Version $Id: RecordSuppliedEvent.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event;

import org.freehep.record.loop.SequentialRecordLoop;

/**
This class is used to notify <code>{@link RecordListener}</code> that a new
record has been supplied by the <code>SequentialRecordSource</code>.
 *
 * @version $Id: RecordSuppliedEvent.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class RecordSuppliedEvent
		extends RecordEvent
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** The record associated with this event. */
	private Object record;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private RecordSuppliedEvent()
	{
		super(null);
	}

	/**
	 * Create an instance of this class with the specified record as the
	 * source and specified record.
	 *
	 * @param source the SequentialRecordLoop record which generated this
	 * event.
	 * @param record the new record that was supplied.
	 */
	public RecordSuppliedEvent(Object source,
							   Object record)
	{
		super(source);
		this.record = record;
	}

	/**
	 * Create an instance of this class with the specified
	 * SequentialRecordLoop as the source.
	 *
	 * @param source the SequentialRecordLoop record which generated this event.
	 * @param object the new record that was supplied.
	 */
	public RecordSuppliedEvent(SequentialRecordLoop source,
							   Object object)
	{
		super(source);
		this.record = object;
	}

	// instance member function (alphabetic)

	/**
	 * Returns the record associated with this event.
	 *
	 * @return the record associated with this event.
	 */
	public Object getRecord()
	{
		return record;
	}

	// static member functions (alphabetic)

	// Description of this record.
	// public String toString() {}

	// public static void main(String args[]) {}
}
