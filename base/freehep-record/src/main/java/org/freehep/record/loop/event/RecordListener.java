/*
 * interface: RecordListener
 *
 * Version $Id: RecordListener.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event;

import java.util.EventListener;

/**
<p>
This interface is, when added to a <code>{@link
org.freehep.record.loop.SequentialRecordLoop SequentialRecordLoop}</code>
object, notified of any <code>{@link RecordEvent}</code> that occurs
in that object. The attached diagram show the state machine that should
be supported by any implementation of the SequentialRecordLoop or
RecordListener interfaces. This machine is designed to support both
batch and interactive execution.
</p>

<p>
<img src="doc-files/ELL_States.gif">.
</p>

<p>
In a batch environment this class is configured, supplied with record,
and finishes.
</p>

<p>
An interactive envrionment is more complex as the user can change the
configuration of an instance of this class at any time that the
<code>SequentialRecordLoop</code> object is not looping. This means that
an instance of this class needs to check it is correctly configured at
the start of any loop. For the first loop of an
<code>SequentialRecordLoop</code> object its listener will receive a
 {@link #configure} event to tell them to prepare to receive new records.
After that loop the configuration may be changed before the next loop is
executed. In that case the <code>RecordListener</code> will receive a
 {@link #reconfigure} event indicating that the configuration has changes
and that the listener should prepare itself for new records using this
new configuration. If the configuration has not changed then the
listener gets a simple {@link #resume} message to indicate that it is
about to receive new records. The full state diagram for an
EventListener is shown above
</p>

<p>
Once a loop has completed, or been interrupted, a {@link #suspend} event
is sent by the <code>SequentialRecordLoop</code> object. this allows
this class to release any time sensitive resources while the user
considers their next move. Both a resume and a reconfigure event should
be used to reacquire the resources that have been released.
</p>

<p>
This class' {@link #finish} method is invoked whenever it is removed
from a <code>SequentialRecordLoop</code> object. This can be caused by a
direct invocation of the loop's <code>removeRecordListener</code> or
<code>dispose</code> method, which implicitly removes the loops listener
if there is one. This event is also be triggered when a
<code>SequentialRecordLoop</code> is reset. The finish event gives the
<code>RecordListener</code> an opportunity to do any housekeeping task
that are related to its participation in the
<code>SequentialRecordLoop</code> such as output summary information.
After handling a finish event this class should be prepared to handle a
configure event as such an event will occur for the first loop after
either the <code>SequentialRecordLoop</code> object has been reset or
after this class has been added back to a
<code>SequentialRecordLoop</code>.
</p>
 *
 * @version $Id: RecordListener.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public interface RecordListener
		extends EventListener
{

	// public static final member data

	// instance member function (alphabetic)

	/**
	 * Tells this object to configure itself in preparation for the first
	 * {@link #recordSupplied} call.
	 *
	 * @param event a ConfigurationEvent describing the configuration to use.
	 */
	public void configure(ConfigurationEvent event);

	/**
	 * Tells this object that an SequentialRecordLoop to which is has been added has
	 * been disposed of, and this object should execute any housekeeping tasks
	 * trelated to its participation in this SequentialRecordLoop.
	 *
	 * @param event the RecordEvent for this event.
	 */
	public void finish(RecordEvent event);

	/**
	 * Called every time a new record is read by the SequentialRecordLoop's
	 * SequentialRecordSource object.
	 *
	 * @param event a RecordSuppliedEvent describing the data supplied.
	 */
	public void recordSupplied(RecordSuppliedEvent event);

	/**
	 * Tells this object to reconfigure itself in preparation for a new set of
	 * {@link #recordSupplied} calls.
	 *
	 * @param event a ConfigurationEvent describing the new configuration to
	 * use.
	 */
	public void reconfigure(ConfigurationEvent event);

	/**
	 * Tells this object to prepare for a new set of  {@link #recordSupplied}
	 * calls using the its existing configuration.
	 *
	 * @param event the RecordEvent for this event.
	 */
	public void resume(RecordEvent event);

	/**
	 * Tells this object that there will be either a {@link #resume} or
	 * {@link #reconfigure} before any more {@link #recordSupplied} calls will
	 * be made.
	 *
	 * @param event the RecordEvent for this event.
	 */
	public void suspend(RecordEvent event);

	// static member functions (alphabetic)

}
