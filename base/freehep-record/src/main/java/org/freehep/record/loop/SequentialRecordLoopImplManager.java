/*
 * class: SequentialRecordLoopImplManager
 *
 * Version $Id: SequentialRecordLoopImplManager.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 20 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop;

import java.io.IOException;

/**
The class is an implementation of the
<code>SequentialRecordLoopManager</code> class that can manage the
default <code>SequentialRecordLoop</code> implementation.
 *
 * @version $Id: SequentialRecordLoopImplManager.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class SequentialRecordLoopImplManager
		implements SequentialRecordLoopManager
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** The SequentialRecordLoopImpl object whose loop is being managed via this
	 * object. */
	private SequentialRecordLoopImpl recordLoop;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private SequentialRecordLoopImplManager()
	{
	}

	/**
	 * Create an instance of this class which is going to manage the specified
	 * SequentialRecordLoopImpl object.
	 *
	 * @param recordLoop the object those loop is to be managed.
	 */
	public SequentialRecordLoopImplManager(SequentialRecordLoopImpl recordLoop)
	{
		if (null == recordLoop) {
			throw new IllegalArgumentException(
					"Must specify a SequentialRecordLoopImpl object to manage.");
		}
		this.recordLoop = recordLoop;
	}

	// instance member function (alphabetic)

	public void beginLoop(long target)
			throws IllegalStateException
	{
		recordLoop.beginLoop(target);
	}

	public long endLoop()
			throws LoopException
	{
		return recordLoop.endLoop();
	}

	public SequentialRecordLoop getRecordLoop()
	{
		return recordLoop;
	}

	public Object nextRecord()
			throws IOException
	{
		return recordLoop.getNextRecord();
	}

	public boolean hasMoreRecords()
	{
		return (recordLoop.hasMoreRecords());
	}

	public void supplyRecord(Object record)
	{
		recordLoop.supplyRecord(record);
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
