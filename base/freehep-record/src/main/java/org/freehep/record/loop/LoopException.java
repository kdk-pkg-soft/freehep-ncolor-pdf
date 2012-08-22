/*
 * class: LoopInterruptedException
 *
 * Version $Id: LoopException.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 9 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop;

/**
This class is thrown by the "loop" method of the <code>{@link
SequentialRecordLoop}</code> interface if there an exception.
 *
 * @version $Id: LoopException.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class LoopException
		extends Exception
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** the number of records that were supplied before the exception. */
	private long supplied;

	/** the number of countable records that were supplied before the
	 * exception. */
	private long countableSupplied;

	// constructors

	/**
	 * Create an instance of this class.
	 * Default constructor is declared, but private, to stop accidental
	 * creation of an instance of the class.
	 */
	private LoopException()
	{
	}

	/**
	 * Create an instance of this class without any detailed message.
	 *
	 * @param supplied the number of records that were supplied before the
	 * exception.
	 * @param countableSupplied the number of countable records that were
	 * supplied before the exception.
	 */
	public LoopException(long supplied, long countableSupplied)
	{
		this.supplied = supplied;
		this.countableSupplied = countableSupplied;
	}

	/**
	 * Create an instance of this class with a detailed message.
	 *
	 * @param s the detailed message.
	 * @param supplied the number of records that were supplied before the
	 * exception.
	 * @param countableSupplied the number of countable records that were
	 * supplied before the exception.
	 */
	public LoopException(String s, long supplied, long countableSupplied)
	{
		super(s);
		this.supplied = supplied;
		this.countableSupplied = countableSupplied;
	}

	// instance member function (alphabetic)

	/**
	 * Returns the number of countable records that were supplied before the
	 * exception.
	 *
	 * @return the number of countable records that were supplied before the
	 * exception.
	 */
	public long getCountableSupplied()
	{
		return countableSupplied;
	}

	/**
	 * Returns the number of records that were supplied before the exception.
	 *
	 * @return the number of records that were supplied before the exception.
	 */
	public long getSupplied()
	{
		return supplied;
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
