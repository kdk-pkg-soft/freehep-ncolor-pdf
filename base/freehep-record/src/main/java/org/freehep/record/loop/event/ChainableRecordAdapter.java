/*
 * class: ChainableRecordListener
 *
 * Version $Id: ChainableRecordAdapter.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 19 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event;

import java.util.TooManyListenersException;

/**
This class implements the <code>ChainableRecordListener</code> interface
so that the methods of another <code>RecordListener</code> object are
correctly executed in succession to its own processing, thus creating a
chain of listeners. To function correctly any standard
<code>RecordListener</code> event method which is overridden should, as
its last step, call this classes implementation of that method. For
example:

<pre>
	public void recordSupplied(RecordSuppliedEvent event)
	{
		// Do this objects processing here.

		super.recordSupplied(event);
	}
</pre>
 *
 * @version $Id: ChainableRecordAdapter.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
class ChainableRecordAdapter
		implements ChainableRecordListener
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	/** Listener to use if no other has been added. */
	private static final RecordListener NULL_LISTENER =
			new RecordAdapter();

	// private static member data

	// private instance member data

	/** The RecordListener which should be called after this object. */
	private RecordListener listener = NULL_LISTENER;

	/** True if the listener has not yet been supplied with a record. */
	private boolean newListener;

	/** Standard RecordEvent for this object to send out. */
	private final RecordEvent recordEvent;

	// constructors

	/**
	 * Create an instance of this class.
	 */
	protected ChainableRecordAdapter()
	{
		recordEvent = new RecordEvent(this);
	}

	// instance member function (alphabetic)

	public void addRecordListener(RecordListener listener)
			throws TooManyListenersException
	{
		if (NULL_LISTENER != this.listener) {
			throw new TooManyListenersException("An RecordListener is" +
					" already registered with this ChainableRecordListener");
		}
		if (null == listener) {
			listener = NULL_LISTENER;
		} else {
			this.listener = listener;
		}
		newListener = true;
	}

	public void configure(ConfigurationEvent event)
	{
		listener.configure(event);
		newListener = false;
	}

	public void finish(RecordEvent event)
	{
		listener.finish(event);
	}

	/**
	 * Returns the RecordListener which is executed after this object. null
	 * is returned if no listener succeeds this object
	 * listener.
	 *
	 * @return the RecordListener which succeeds this object.
	 */
	public RecordListener getRecordListener()
	{
		if (NULL_LISTENER == listener) {
			return null;
		}
		return listener;
	}

	public void recordSupplied(RecordSuppliedEvent event)
	{
		listener.recordSupplied(event);
	}

	public void reconfigure(ConfigurationEvent event)
	{
		if (newListener) {
			listener.configure(event);
			newListener = false;
		} else {
			listener.reconfigure(event);
		}
	}

	public void removeRecordListener(RecordListener listener)
	{
		if (this.listener == listener) {
			if (!newListener) {
				listener.finish(recordEvent);
			}
			this.listener = NULL_LISTENER;
		}
	}

	/**
	 * Tells this object to prepare for a new set of {@link #recordSupplied}
	 * calls using the its existing configuration.
	 *
	 * @param event the RecordEvent for this event.
	 * @throws IllegalStateException if the listener is new and has not been
	 * handed at least one record already.
	 */
	public void resume(RecordEvent event)
	{
		if (newListener) {
			throw new IllegalStateException("Chained Listener was changed" +
					" but SequentialRecordLoop configuration was not reset");
		}
		listener.resume(event);
	}

	public void suspend(RecordEvent event)
	{
		listener.suspend(event);
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
