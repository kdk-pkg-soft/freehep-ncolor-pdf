/*
 * interface: SequentialRecordLoop
 *
 * Version $Id: SequentialRecordLoop.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop;

import org.freehep.record.loop.event.RecordListenerManager;
import org.freehep.record.loop.event.RecordLoopListener;
import org.freehep.record.source.SequentialRecordSource;

import java.io.IOException;

/**
<p>
This interface defines the methods available to any framework which
wishes to make used of a sequential record loop.
</p>

<p>
The basic premise of this interface is that when the "loop" method is
invoked each record that is supplied by this object's <code>{@link
org.freehep.record.source.SequentialRecordSource
SequentialRecordSource}</code> is passed to its <code>{@link
org.freehep.record.loop.event.RecordListener RecordListener}</code>
using its <code>{@link
org.freehep.record.loop.event.RecordListener#recordSupplied
recordSupplied}</code>
event.
</p>

<p>
This class has a standard add/removed mechanism for handling a single
<code>RecordListener</code>. Only one <code>RecordListener</code> can be
registered at a time as this avoids any implementation of this interface
enacting an ordering policy that may not match that expected by its
client. A Client can impose an ordering policy by providing a suitable
<code>RecordListener</code> implementation which passes the <code>{@link
org.freehep.record.loop.event.RecordEvent
RecordEvent}s</code> it receives onto other <code>RecordListeners</code>.
<code>SequentialAnalysis</code> and
<code>ThreePhaseAnalysis</code> are two examples of such a class.
</p>

<p>
The <code>{@link #loop}</code> method of this class will processes the
specified number of "countable" records. By default all records are
countable, however if the {@link #doNotCount} method is called and
its argument is a record that is currently being supplied by this class
then that record will not count toward the requested number of records,
not will it contribute to the return value of the <code>loop</code>
method.
</p>

<p>
This looping can be interrupted by using the <code>{@link
#setInterruptRequested}</code> method to set the appropriate flag to
<code>true</code>. This will cause the loop to terminate once all data
with which it is dealing has been handled. It is important to realize
that simply interrupting a loop does not directly terminate any
processing that is already underway, it just stops new records from
being requested. This means that if some part of the implementation of
the <code>recordSupplied</code> listener method is waiting for
something, such as networks access or a "next record" click from the
user, the <code>loop</code> method will continue to block until that
code completes. Any loop that is interrupted this way will throw a
<code>{@link org.freehep.record.loop.LoopInterruptedException
LoopInterruptedException}</code> even if all of the requested number of
records have been handled.
</p>
 *
 * @version $Id: SequentialRecordLoop.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public interface SequentialRecordLoop
		extends RecordListenerManager
{

	// public static final member data

	// instance member function (alphabetic)

	/**
	 * Adds the specified RecordLoopListener to this object.
	 *
	 * @param listener the RecordLoopListener to add.
	 * @see #removeRecordLoopListener
	 */
	public void addRecordLoopListener(RecordLoopListener listener);

	/**
	 * Tells this object that if it is currently supplying the specified
	 * record then it should not be added to the "countable" record total.
	 *
	 * @param record the recornd that should not be counted.
	 */
	public void doNotCount(Object record);

	/**
	 * Signals that this object in no longer going to be used. If this
	 * object has a RecordListener, then the listener is passed a
	 * {@link org.freehep.record.loop.event.RecordListener#finish finish}
	 * event.
	 */
	public void dispose();

	/**
	 * Returns the SequentialRecordSource used by this object.
	 *
	 * @return the SequentialRecordSource used by this object.
	 */
	public SequentialRecordSource getRecordSource();

	/**
	 * Returns the total number of records supplied.
	 *
	 * @return the total number of records supplied.
	 */
	public long getTotalSupplied();

	/**
	 * Returns the total number of countable records supplied.
	 *
	 * @return the total number of countable records supplied.
	 */
	public long getTotalCountableSupplied();

	/**
	 * Returns true if an interrupt has been requested and the loop method has
	 * not yet returned.
	 *
	 * As the interrupt may come for another thread this method must always be
	 * implemented in a thread-safe manner.
	 *
	 * @return true if an interrupt has been requested and the loop method has
	 * not yet returned.
	 */
	public boolean isInterruptRequested();

	/**
	 * Processes the specified number of records through the record loop. If
	 * the number of records is specified to be less than zero then the loop
	 * will continue until this objects <code>SequentialRecordSource</code>
	 * throws a <code>LoopSourceExhaustedException</code> respose to its
	 * <code>getCurrentRecord</code> method.
	 *
	 * If the {@link #doNotCount} method is called while this method is
	 * executing and its argument is a record that is currently being supplied
	 * by this object then that record will not count towards the specified
	 * number of records requested. Nor will it be counted in the return value
	 * of this method.
	 *
	 * If the loop is interrupted before it is completed then a
	 * <code>LoopInterruptedException</code> is thrown, even if the requested
	 * number of records have been supplied. The number of completed countable
	 * records is contained in the <code>LoopInterruptedException</code> so\
	 * this value can be used to determine whether all requested records where
	 * supplied on not.
	 *
	 * @param number the number of countable records to supply.
	 * @return the number of countable records that have been supplied.
	 * @throws LoopInterruptedException if the loop ended due to an
	 * interruption being requested.
	 * @throws LoopSourceExhaustedException if number is non-negative and the source
	 * runs out of records.
	 * @throws IllegalStateException if this method is called before a
	 * <code>setRecordSource</code> is set.
	 * @throws IOException if there is problems reading a record.
	 * @throws NoLoopRecordException if the record to supply could not be
	 * found.
	 */
	public long loop(long number)
			throws LoopException, IOException;

	/**
	 * Removes the specified listener from this object if it is this objects
	 * listener, otherwise it does nothing.
	 *
	 * @param listener the RecordLoopListener to remove.
	 * @see #addRecordLoopListener
	 */
	public void removeRecordLoopListener(RecordLoopListener listener);

	/**
	 * Resets the loop so that the next time loop is called this object will
	 * behave as if the previous calls to loop never happened. This means that
	 * this object's SequentialRecordSource will have its <code>rewing</code>
	 * method called while the objects listener, if it exists, will be sent a
	 * {@link org.freehep.record.loop.event.RecordListener#finish
	 * finish} event. At the next loop call the listener will receive a
	 * {@link org.freehep.record.loop.event.RecordListener#configure
	 * configure} event rather than a {@link
	 * org.freehep.record.loop.event.RecordListener#reconfigure reconfigure} or
	 * {@link org.freehep.record.loop.event.RecordListener#resume
	 * resume} event.
	 *
	 * @throws IOException if there is problems rewinding this object's source.
	 */
	public void reset()
			throws IOException;

	/**
	 * Sets the configuration object which should be passed to this objects
	 * RecordListener next time the {@link #loop} method is invoked. If this
	 * method is called any time after the first loop invocation (even with the
	 * same configuration object) then a {@link
	 * org.freehep.record.loop.event.RecordListener#reconfigure reconfigure}
	 * event will be used to start the next loop. Otherwise the next loop will
	 * start with a {@link org.freehep.record.loop.event.RecordListener#resume
	 * resume} event.
	 *
	 * @param configuration the configuration object to pass to this object's
	 * RecordListener.
	 */
	public void setConfiguration(Object configuration);

	/**
	 * Set the progress reporting interval in terms of records.
	 *
	 * This number is only a guideline to the the loop, which can choose to
	 * ignore it wishes.
	 *
	 * If the loop if it is observing both guidelines and the interval has
	 * been specified by both time and number of records, the interval which
	 * expired first should be the one used by the loop. If neither method
	 * has been used to specify an interval the the loop will use a
	 * sensible defaualt.
	 *
	 * A value of zero or less with effectively remove any guideline that has
	 * been set.
	 *
	 * @param records the number of records between progress reports.
	 */
	public void setProgessByRecords(long records);

	/**
	 * Set the progress reporting interval in terms of milliseconds.
	 *
	 * This number is only a guideline to the the loop, which can choose to
	 * ignore it wishes.
	 *
	 * If the loop if it is observing both guidelines and the interval has
	 * been specified by both time and number of records, the interval which
	 * expired first should be the one used by the loop. If neither method
	 * has been used to specify an interval the the loop will use a
	 * sensible defaualt.
	 *
	 * A value of zero or less with effectively remove any guideline that has
	 * been set.
	 *
	 * @param milliseconds the number of milliseconds between progress reports.
	 */
	public void setProgessByTime(long milliseconds);

	/**
	 * Sets this objects SequentialRecordSource to the one that is specified.
	 *
	 * If this object already has a SequentialRecordSource it will be replaced
	 * by the new one.
	 *
	 * @param source the SequentialRecordSource to use with this object.
	 */
	public void setRecordSource(SequentialRecordSource source);

	/**
	 * This method can either request that the <code>loop</code> method
	 * terminate when it has finished dealing with all its current data or
	 * clear such a request.
	 *
	 * For a meaningful interrupt to happen it must come from a thread other
	 * than the one executing the <code>loop</code> method, therefore this
	 * method must always be implemented in a thread-safe manner.
	 *
	 * @param interruptRequested true if the loop should be interrupted.
	 */
	public void setInterruptRequested(boolean interruptRequested);

	// static member functions (alphabetic)

}
