/*
 * class: RecordAdapter
 *
 * Version $Id: RecordAdapter.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event;

/**
It is an implementation of the <code>{@link RecordListener}</code> interface
where all the methods are empty. It exists a conveniece class for creators of
<code>RecordListener</code> objects.
 *
 * @version $Id: RecordAdapter.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class RecordAdapter
		implements RecordListener
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
	 */
	public RecordAdapter()
	{
	}

	// instance member function (alphabetic)

	public void configure(ConfigurationEvent event)
	{
	}

	public void finish(RecordEvent event)
	{
	}

	public void recordSupplied(RecordSuppliedEvent event)
	{
	}

	public void reconfigure(ConfigurationEvent event)
	{
	}

	public void resume(RecordEvent event)
	{
	}

	public void suspend(RecordEvent event)
	{
	}
	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
