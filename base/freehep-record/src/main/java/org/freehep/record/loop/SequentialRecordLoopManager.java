/*
 * class: SequentialRecordLoopImplManager
 *
 * Version $Id: SequentialRecordLoopManager.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 20 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop;

import java.io.IOException;

/**
This interface allows control of a <code>{@link SequentialRecordLoop}</code>
object to be managed by an external object. The external object is
responsible for making sure the methods of this object are called in the
correct order. The following how the methods should be invoked, where
<code>number</code> is the number of records that the external object is
trying to supply.

<pre>
    manager.beginLoop(number);

    while (manager.hasMoreRecords()) {
        manager.supplyRecord(manager.nextRecord());
    }

    long result = manager.endLoop();
</pre>
 *
 * @version $Id: SequentialRecordLoopManager.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public interface SequentialRecordLoopManager
{

	// public static final member data

	// instance member function (alphabetic)

	/**
	 * This method should be called before any loop begins.
	 *
	 * @param target the number of records that should have been supplied.
	 * @throws IllegalStateException if this is called before a
	 * source is set in the SequentialRecordLoopImpl object being managed.
	 */
	public void beginLoop(long target);

	/**
	 * This method should be called after the loop has ended.
	 *
	 * @return the number of countable records that have been supplied.
	 * @throws LoopInterruptedException if the loop is interrupted.
	 * @throws LoopSourceExhaustedException if target is non-negative and the
	 * source runs out of records.
	 * @throws NoLoopRecordException if the record to supply could not be
	 * found.
	 */
	public long endLoop()
			throws LoopException;

	/**
	 * @return the SequentialRecordLoop object this object is managing.
	 */
	public SequentialRecordLoop getRecordLoop();

	/**
	 * This attempts to get a new record object from the source. Before
	 * invoking the getCurrentRecord method, this routine checks to see if an
	 * interrupt has been requested, and if so set the internal flag to
	 * indicate this is the case.
	 *
	 * @return the record object supplied or null if no object is supplied.
	 * @throws IOException if there is a problem reading the next record.
	 */
	public Object nextRecord()
			throws IOException;

	/**
	 * Returns true if there may be more records available and the number
	 * supplied has not reached the target.
	 */
	public boolean hasMoreRecords();

	/**
	 * Supplies the specified record object, if it is not null, to the
	 * listener of the SequentialRecordLoopImpl object being managed.
	 *
	 * @param record the record object to be supplied.
	 */
	public void supplyRecord(Object record);

	// static member functions (alphabetic)

}
