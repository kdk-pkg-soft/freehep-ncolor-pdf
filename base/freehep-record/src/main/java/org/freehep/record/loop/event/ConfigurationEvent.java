/*
 * class: ConfigurationEvent
 *
 * Version $Id: ConfigurationEvent.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.event;

import org.freehep.record.loop.SequentialRecordLoop;


/**
This class is used to notify <code>{@link RecordListener}</code>
objects that there is new
configuration information and that this should be processed before
processing any records are handled.
 *
 * @version $Id: ConfigurationEvent.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class ConfigurationEvent
		extends RecordEvent
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** The configuration object associated with this event. */
	private Object configuration;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private ConfigurationEvent()
	{
		super(null);
	}

	/**
	 * Create an instance of this class with the specified object
	 * as the source and the specified configuration.
	 */
	public ConfigurationEvent(Object source,
							  Object configuration)
	{
		super(source);
		this.configuration = configuration;
	}

	/**
	 * Create an instance of this class with the specified
	 * SequentialRecordLoop as the source and the specified configuration.
	 */
	public ConfigurationEvent(SequentialRecordLoop source,
							  Object configuration)
	{
		super(source);
		this.configuration = configuration;
	}

	// instance member function (alphabetic)

	/**
	 * Returns the configuration object associated with this event.
	 *
	 * @return the configuration object associated with this event.
	 */
	public Object getConfiguration()
	{
		return configuration;
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
