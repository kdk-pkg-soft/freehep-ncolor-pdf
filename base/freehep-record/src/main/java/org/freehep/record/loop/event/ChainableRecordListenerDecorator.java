/*
 * class: ChainableRecordListener
 *
 * Version $Id: ChainableRecordListenerDecorator.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 19 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event;


/**
This class decorates an <code>{@link RecordListener}</code> object so
that the methods of another <code>RecordListener</code> object are
executed in succession to its own method, thus creating a chain of listeners.
 *
 * @version $Id: ChainableRecordListenerDecorator.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class ChainableRecordListenerDecorator
		extends ChainableRecordAdapter
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** The RecordListener that is decorated by this object. */
	private RecordListener decorated;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private ChainableRecordListenerDecorator()
	{
	}

	/**
	 * Create an instance of this class that decorates the specified object.
	 *
	 * @param decorated the RecordListener to be decorated.
	 * @throws IllegalArgumentException if decorated is null.
	 */
	public ChainableRecordListenerDecorator(RecordListener decorated)
	{
		if (null == decorated) {
			throw new IllegalArgumentException(
					"Must specify an RecordListener to be decorated.");
		}
		this.decorated = decorated;
	}

	// instance member function (alphabetic)

	public void configure(ConfigurationEvent event)
	{
		decorated.configure(event);
		super.configure(event);
	}

	public void finish(RecordEvent event)
	{
		decorated.finish(event);
		super.finish(event);
	}

	/**
	 * Returns the RecordListener that this object decorates.
	 *
	 * @return the RecordListener that this object decorates.
	 */
	public RecordListener getDecoratedListener()
	{
		return decorated;
	}

	public void recordSupplied(RecordSuppliedEvent event)
	{
		decorated.recordSupplied(event);
		super.recordSupplied(event);
	}

	public void reconfigure(ConfigurationEvent event)
	{
		decorated.reconfigure(event);
		super.reconfigure(event);
	}

	/**
	 * Tells this object to prepare for a new set of  {@link #recordSupplied}
	 * calls using the its existing configuration.
	 *
	 * @param event the RecordEvent for this event.
	 * @throws IllegalStateException if the listener is new and has not been
	 * handed at least on record already.
	 */
	public void resume(RecordEvent event)
	{
		decorated.resume(event);
		super.resume(event);
	}

	public void suspend(RecordEvent event)
	{
		decorated.suspend(event);
		super.suspend(event);
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
