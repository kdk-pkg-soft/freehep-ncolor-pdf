/*
 * class: RejectEvenStrings
 *
 * Version $Id: RejectEvenStrings.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 20 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.examples;

import org.freehep.record.loop.LoopException;
import org.freehep.record.loop.SequentialRecordLoop;
import org.freehep.record.loop.SequentialRecordLoopImpl;
import org.freehep.record.loop.event.ChainableRecordListener;
import org.freehep.record.loop.event.ChainableRecordListenerDecorator;
import org.freehep.record.loop.event.RecordAdapter;
import org.freehep.record.loop.event.RecordSuppliedEvent;
import org.freehep.record.source.SequentialRecordSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TooManyListenersException;

/**
This class is an example of how to <code>{@link
org.freehep.record.loop.event.RecordListener RecordListener}</code>
objects can be used to do
conditional processing of events by rejecting certain records that are
supplied. This example uses the listener in the EchoStrings example, but
this listener is chained behind an instance of this class which is an
<code>RecordListener</code> that can reject, if specified, every other
record with which is suppiled to it.
 *
 * @version $Id: RejectEvenStrings.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class RejectEvenStrings
		extends RecordAdapter
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	// private static member data

	// private instance member data

	/** The count of records with which this object has been supplied. */
	private long count;

	// constructors

	/**
	 * Create an instance of this class.
	 */
	public RejectEvenStrings()
	{
	}

	// instance member function (alphabetic)
	public void recordSupplied(RecordSuppliedEvent event)
	{
		count++;
		if (0 == count % 2) {
			event.getRecordLoop().doNotCount(event.getRecord());
		}
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	public static void main(String args[])
			throws FileNotFoundException
	{
		// Check at least a filename is specified.
		if (0 == args.length) {
			System.err.println("Filename must be specified.");
			return;
		}

		// Create object which prints strings that are read in form a file.
		EchoStrings echoStrings = new EchoStrings();

		// Create object that rejects every other string.
		RejectEvenStrings rejectEvenStrings = new RejectEvenStrings();

		// Chain the echo listener after this one.
		ChainableRecordListener listener =
				new ChainableRecordListenerDecorator(rejectEvenStrings);
		try {
			listener.addRecordListener(echoStrings);
		} catch (TooManyListenersException e) {
			// Ignore as can not happen(!)
		}

		// Create object to read lines from a file.
		SequentialRecordSource supplier =
				echoStrings.new LineSupplier(args[0]);

		// Create basic event loop
		SequentialRecordLoop recordLoop =
				new SequentialRecordLoopImpl(supplier);
		try {
			recordLoop.addRecordListener(listener);

			// Loop over the number of line requested or all of the file.
			long count;
			if (1 < args.length) {
				count = Integer.parseInt(args[1]);
			} else {
				count = -1;
			}
			long processed = recordLoop.loop(count);
			System.out.println("Processed " +
					processed +
					" lines.");
		} catch (TooManyListenersException e) {
			// Ignore as can not happen(!)
		} catch (LoopException e1) {
			System.err.println("Loop failed:\n" + e1.toString());
		} catch (IOException e3) {
			System.err.println("An IOException was thrown.");
		}
	}
}
