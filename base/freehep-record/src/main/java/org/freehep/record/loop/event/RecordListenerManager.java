/*
 * interface: RecordListenerManager
 *
 * Version $Id: RecordListenerManager.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: March 7 2003
 *
 * (c) 2003 IceCube Collaboration
 */

package org.freehep.record.loop.event;

import java.util.TooManyListenersException;

/**
This interface should be implemented by any class that can register
single <code>{@link RecordListener}</code> objects.
 *
 * @version $Id: RecordListenerManager.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public interface RecordListenerManager
{

	// public static final member data

	// instance member function (alphabetic)

	/**
	 * Adds the specified RecordListener to this object.
	 *
	 * @param listener the RecordListener to add.
	 * @throws TooManyListenersException if a listener is already present.
	 * @see #removeRecordListener
	 */
	public void addRecordListener(RecordListener listener)
			throws TooManyListenersException;

	/**
	 * Returns this objects RecordListener if there is one.
	 *
	 * @return this objects RecordListener if there is one.
	 */
	public RecordListener getRecordListener();

	/**
	 * Removes the specified listener from this object if it is this objects
	 * listener, otherwise it does nothing.
	 *
	 * If removed, a finish event is sent to the listener if it has been
	 * configured so that is can clean up any resources it holds.
	 *
	 * @param listener the RecordListener to remove.
	 * @see #addRecordListener
	 */
	public void removeRecordListener(RecordListener listener);

	// static member functions (alphabetic)

}
