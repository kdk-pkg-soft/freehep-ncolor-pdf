package org.freehep.record.source;

/**
 * An exception thrown when attempting to access a record beyong the end of
 * a source.
 */
public class EndOfSourceException extends Exception
{
	/**
	 * Create an instance of this class without any detailed message.
	 */
	public EndOfSourceException()
	{
	}

	/**
	 * Create an instance of this class with a detailed message.
	 *
	 * @param s the detailed message.
	 */
	public EndOfSourceException(String s)
	{
			super(s);
	}
}
