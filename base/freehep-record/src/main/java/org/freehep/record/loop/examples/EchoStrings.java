/*
 * class: EchoStrings
 *
 * Version $Id: EchoStrings.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 10 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop.examples;

import org.freehep.record.loop.LoopException;
import org.freehep.record.loop.SequentialRecordLoop;
import org.freehep.record.loop.SequentialRecordLoopImpl;
import org.freehep.record.loop.event.RecordAdapter;
import org.freehep.record.loop.event.RecordSuppliedEvent;
import org.freehep.record.source.EndOfSourceException;
import org.freehep.record.source.SequentialRecordSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TooManyListenersException;

/**
This class gives the most basic example of how the {@link
org.freehep.record.loop} package can be used. It implements, as a nested class,
 a <code>SequentialRecordSource</code> that reads
a file and supplys each line read as a record. The class itself implements a
 <code>RecordListener</code> that prints the contents of each
record, with which it is supplied, to the standard output.
 *
 * @version $Id: EchoStrings.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class EchoStrings
		extends RecordAdapter
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
	public EchoStrings()
	{
	}

	// instance member function (alphabetic)
	public void recordSupplied(RecordSuppliedEvent event)
	{
		String[] strings = ((StringsData) event.getRecord()).getStrings();
		final int finished = strings.length;
		for (int string = 0;
			 finished != string;
			 string++) {
			System.out.println(strings[string]);
		}
	}

	// static member functions (alphabetic)

	/**
	 * Class to hold line read in from file.
	 */
	public class StringsData
	{
		// private instance member data
		private String[] strings;

		/**
		 * Create an instance of this class.
		 * Default constructor is declared, but private, to stop accidental
		 * creation of an instance of the class.
		 */
		private StringsData()
		{
		}

		/**
		 * Create an instance of this class.
		 */
		public StringsData(String[] strings)
		{
			this.strings = strings;
		}

		/**
		 * @return the strings which is in this object.
		 */
		public String[] getStrings()
		{
			return strings;
		}

		/**
		 * @param strings the strings which is in this object.
		 */
		public void setStrings(String[] strings)
		{
			this.strings = strings;
		}
	}

	/**
	 * Class to read in file line by line.
	 */
	public class LineSupplier
			implements SequentialRecordSource
	{
		// private instance member data
		private BufferedReader reader;
		private IOException ioException;
		private StringsData currentLine;

		/**
		 * Create an instance of this class.
		 * Default constructor is declared, but private, to stop accidental
		 * creation of an instance of the class.
		 */
		private LineSupplier()
		{
		}

		/**
		 * Create an instance of this class that will read the specified file.
		 *
		 * @param filename the name of the file to read.
		 */
		public LineSupplier(String filename)
				throws FileNotFoundException
		{
			reader = new BufferedReader(new FileReader(filename));
		}

		public Object getCurrentRecord()
				throws EndOfSourceException, IOException
		{
			if (null != ioException) {
				throw ioException;
			}
			if (null == currentLine) {
				throw new EndOfSourceException("No more lines.");
			}
			return currentLine;
		}

		public long getEstimatedSize()
		{
			return -1;
		}

		public void next()
		{
			ioException = null;
			currentLine = null;
			String line = null;
			try {
				line = reader.readLine();
				if (null == line) {
					return;
				}
			} catch (IOException e) {
				ioException = e;
				return;
			}
			currentLine = new StringsData(new String[]{line.trim()});
		}

		public Class getRecordClass()
		{
			return StringsData.class;
		}

		public String getSourceName()
		{
			return getClass().getName();
		}

		public void rewind()
				throws IOException
		{
			reader.reset();
		}

		public void releaseRecord(Object record)
		{
			if (currentLine == record) {
				currentLine = null;
			}
		}
                
                public void close() throws IOException {
                    reader.close();
                }
	}

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
		EchoStrings listener = new EchoStrings();

		// Create object to read lines from a file.
		SequentialRecordSource supplier = listener.
		new LineSupplier(args[0]);

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
