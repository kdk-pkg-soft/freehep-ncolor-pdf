/*
 * class: SequentialRecordLoopImpl
 *
 * Version $Id: SequentialRecordLoopImpl.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: February 10 2003
 *
 * (c) 2003 LBNL
 */

package org.freehep.record.loop;

import org.freehep.record.loop.event.ConfigurationEvent;
import org.freehep.record.loop.event.LoopEvent;
import org.freehep.record.loop.event.LoopProgressEvent;
import org.freehep.record.loop.event.RecordAdapter;
import org.freehep.record.loop.event.RecordEvent;
import org.freehep.record.loop.event.RecordListener;
import org.freehep.record.loop.event.RecordLoopListener;
import org.freehep.record.loop.event.RecordSuppliedEvent;
import org.freehep.record.source.EndOfSourceException;
import org.freehep.record.source.NoSuchRecordException;
import org.freehep.record.source.SequentialRecordSource;

import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.Vector;

/**
This class is the default implementation of the
<code>SequentialRecordLoop</code> interface.
This class is minimally thread safe i.e. only the "setInterruptResquested"
and "isInterruptRequested" methods are considered thread safe. The rest
of the implementation is designed to run in a single thread. When its
RecordListener responses to any RecordEvents, a return from that
method signals that the listener has completed all responsibilities
related to that message.
 *
 * @version $Id: SequentialRecordLoopImpl.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class SequentialRecordLoopImpl
		implements SequentialRecordLoop
{

	// public static final member data

	// protected static final member data

	// static final member data

	// private static final member data

	/** Listener to use if no other has been added. */
	private static final RecordListener NULL_LISTENER =
			new RecordAdapter();

	/** The maximum value of the progress fraction. */
	private static final double MAX_PROGRESS = (double) 1.0;

	/** The value to set to ignore the progress interval. */
	private static final long IGNORE_PROGRESS = (long) 1;

	/** The default progress interval, number of millisseconds, if no
	 * guideline have been set. */
	private static final long DEFAULT_MILLIS_INTERVAL = (long) 2000;

	// private static member data

	// private instance member data

	/** The SequentialRecordSource in use with this object. */
	private SequentialRecordSource source;

	/** The RecordListener currently listening to this object. */
	private RecordListener listener = NULL_LISTENER;

	/** The list of RecordLoopListeners listeninf to this object. */
	private Vector loopListeners = new Vector();

	/** The total number of records supplied. */
	private long totalSupplied;

	/** The total number countable of records supplied. */
	private long totalCountableSupplied;

	/** The number of countable countable records to supply. */
	private long target;

	/** The number of records supplied by the current or last loop. */
	private long supplied;

	/** The number of countable records supplied by the current or last
	 * loop. */
	private long countableSupplied;

	/** Standard RecordEvent for this object to send out. */
	private final RecordEvent recordEvent;

	/** Standard LoopEvent for this object to send out. */
	private final LoopEvent loopEvent;

	/** True if the loop should be interrupted. */
	private boolean interruptRequested;

	/** True if interruptRequested was true at last attempt to
	 * getCurrentRecord. */
	private boolean stoppedDueToInterrupt;

	/** True if the RecordListener should be configured rather than
	 * reconfigured. */
	private boolean newListener;

	/** True if the current configuration object has been set since the last
	 * loop. */
	private boolean newConfiguration;

	/** The current configureation object. */
	private Object configuration;

	/** True if this object's source is been exhausted. */
	private boolean sourceExhausted;

	/** True if this object's source can not read the expected record. */
	private boolean noRecord;

	/** The current record being supplied by this object. */
	private Object currentRecord;

	/** True is the current record is a countable record. */
	private boolean countableRecord;

	/** The the number of records between progress reports. */
	private long recordInterval;

	/** The value of totalSupplied at the last progress report. */
	private long lastProgressSupplied;

	/** The the number of milliseconds between progress reports. */
	private long millisInterval;

	/** The time of the last progress report. */
	private long lastProgressTime;

	// constructors

	/**
	 * Create an instance of this class.
	 */
	public SequentialRecordLoopImpl()
	{
		this(null);
	}

	/**
	 * Create an instance of this class with the specified
	 * SequentialRecordSource.
	 *
	 * @param supplier the SequentialRecordSource to use with this object.
	 * @see #setRecordSource
	 */
	public SequentialRecordLoopImpl(SequentialRecordSource supplier)
	{
		this.source = supplier;
		recordEvent = new RecordEvent(this);
		loopEvent = new LoopEvent(this);
	}

	// instance member function (alphabetic)

	public void addRecordListener(RecordListener listener)
			throws TooManyListenersException
	{
		// Test whether this object already has a listener
		if (NULL_LISTENER != this.listener) {
			throw new TooManyListenersException("An RecordListener is" +
					" already registers with this SequentialRecordLoop");
		}

		// If no listener is specified set listener to be NULL_LISTENER
		if (null == listener) {
			listener = NULL_LISTENER;

			// Otherwise set listener to that specified
		} else {
			this.listener = listener;
		}

		// Flag listener to be configured
		newListener = true;
	}

	public void addRecordLoopListener(RecordLoopListener listener)
	{
		// If loopListener should not be added then don't
		if ((null == listener) || (loopListeners.contains(listener))) {
			return;
		}

		// Add loopListener to list
		loopListeners.add(listener);
	}

	/**
	 * This method signals the listener that new loop is about to begin.
	 *
	 * @param target the number of records that should have been supplied.
	 * @throws IllegalStateException if this is called when this object has no
	 * SequentialRecordSource.
	 * @see #setRecordSource
	 */
	void beginLoop(long target)
			throws IllegalStateException
	{
		// Throw exception if not source is specified
		if (null == source) {
			throw new IllegalStateException("No SequentialRecordSource is set");
		}

		// Set up intial variable for this loop
		this.target = target;
		countableSupplied = 0;
		sourceExhausted = false;
		noRecord = false;
		stoppedDueToInterrupt = false;

		// Tell the loopListeners that a loop is about to begin.
		final int finished = loopListeners.size();
		for (int loopListener = 0;
			 finished != loopListener;
			 loopListener++) {
			((RecordLoopListener)
					loopListeners.get(loopListener)).loopBeginning(loopEvent);
		}

		// Tranistion this objects listener into its configured state.
		if (newListener) {
			listener.configure(new ConfigurationEvent(this,
					configuration));
			newConfiguration = false;
			newListener = false;
		} else {
			if (newConfiguration) {
				listener.reconfigure(new ConfigurationEvent(this,
						configuration));
				newConfiguration = false;
			} else {
				listener.resume(recordEvent);
			}
		}

		// Tell the loopListeners none of the loop has yet been done.
		fireProgress(0.0);
	}

	public void dispose()
	{
		removeRecordListener(listener);
	}

	public void doNotCount(Object record)
	{
		// If the specified record in not the current record do nothing
		if (currentRecord != record) {
			return;
		}

		countableRecord = false;
	}

	/**
	 * This method signals the listener that the current loop has ended.
	 *
	 * @return the number of countable records that have been supplied.
	 * @throws LoopInterruptedException if the loop is interrupted.
	 * @throws LoopSourceExhaustedException if number is non-negative and the
	 * source runs out of records.
	 * @throws NoLoopRecordException if the record to supply could not be
	 * found.
	 */
	long endLoop()
			throws LoopException
	{
		// Tranistion this objects listener into its suspended state.
		listener.suspend(recordEvent);

		// Tell the looplisteners the final progress through the loop
		fireProgress(getProgress());

		// Tell the loopListeners that a loop has ended
		final int finished = loopListeners.size();
		for (int loopListener = 0;
			 finished != loopListener;
			 loopListener++) {
			((RecordLoopListener)
					loopListeners.get(loopListener)).loopEnded(loopEvent);
		}

		// Throw exception if an interrupt existed before the loop completed
		if (stoppedDueToInterrupt || isInterruptRequested()) {
			setInterruptRequested(false);
			throw new LoopInterruptedException(
					getClass().getName() +
					" was interrupted after " +
					countableSupplied +
					" records supplied.",
					supplied,
					countableSupplied);
		}

		// Throw exception if the end of the source was detected and a limited
		// number of records was requested
		if (sourceExhausted && (!(0 > target))) {
			throw new LoopSourceExhaustedException(
					"Source was exhausted after " +
					countableSupplied +
					" records supplied.",
					supplied,
					countableSupplied);
		}

		// Throw exception if no record could be read by the source
		if (noRecord) {
			throw new NoLoopRecordException(
					"Filed to find a reocrd after " +
					countableSupplied +
					" records supplied.",
					supplied,
					countableSupplied);
		}

		return countableSupplied;
	}

	/**
	 * Tells any loopListeners the progress of the loop.
	 */
	private void fireProgress(double fraction)
	{
		lastProgressSupplied = totalSupplied;
		lastProgressTime = System.currentTimeMillis();
		LoopProgressEvent progressEvent = new LoopProgressEvent(this,
				fraction);
		final int finished = loopListeners.size();
		for (int loopListener = 0;
			 finished != loopListener;
			 loopListener++) {
			((RecordLoopListener)
					loopListeners.get(loopListener)).progress(progressEvent);
		}
	}

	/**
	 * This attempts to get a new record object from the source. Before
	 * invoking the getCurrentRecord method, this routine checks to see if an
	 * interrupt has been requested, and if so set the internal flag to
	 * indicate this is the case.
	 *
	 * @return the record object supplied or null if no object is supplied.
	 */
	Object getNextRecord()
			throws IOException
	{
		Object result = null;
		if (isInterruptRequested()) {
			stoppedDueToInterrupt = true;
		} else {
			source.next();
			try {
				result = source.getCurrentRecord();
				if (null == result) {
					throw new NullPointerException("'null' returned by" +
							" SequentialRecordSource getCurrentRecord()" +
							" method");
				}
			} catch (EndOfSourceException e) {
				sourceExhausted = true;
				result = null;
			} catch (NoSuchRecordException e) {
				noRecord = true;
				result = null;
			}
		}
		return result;
	}

	/**
	 * Returns the fraction of the loop which has been completed.
	 *
	 * @return the fraction of the loop which has been completed.
	 */
	private double getProgress()
	{
		// If no records should be supplied the progress in 100%
		if (0 == target) {
			return (double) 1.0;
		}

		// If limited loop requested the report progress
		if (0 < target) {
			return countableSupplied / ((double) target);
		}

		// If unlimited loop and no countable records have been supplied,
		// progress is 0%
		if (0 == countableSupplied) {
			return 0.0;
		}

		// If unlimited loop try to guess progress if possible
		long estimatedSize = source.getEstimatedSize();

		// If no guess is possible simply report 50%
		if (0 > estimatedSize) {
			return 0.5;
		}

		double progress = supplied / ((double) estimatedSize);
		if (MAX_PROGRESS < progress) {
			progress = MAX_PROGRESS;
		}
		return progress;
	}

	public RecordListener getRecordListener()
	{
		return listener;
	}

	public SequentialRecordSource getRecordSource()
	{
		return source;
	}

	public long getTotalSupplied()
	{
		return totalSupplied;
	}

	public long getTotalCountableSupplied()
	{
		return totalCountableSupplied;
	}

	/**
	 * Returns true if there may be more records available and the number
	 * supplied has not reached the number requested.
	 *
	 * @return true if more records are needed to complete the loop.
	 */
	boolean hasMoreRecords()
	{
		return ((!stoppedDueToInterrupt) &&
				(target != countableSupplied) &&
				(!sourceExhausted));
	}

	public synchronized boolean isInterruptRequested()
	{
		return interruptRequested;
	}

	public long loop(final long number)
			throws LoopException, IOException
	{
		beginLoop(number);

		while (hasMoreRecords()) {
			supplyRecord(getNextRecord());
		}

		return endLoop();
	}

	public void removeRecordListener(RecordListener listener)
	{
		// If specified listener is this object's then remove it
		if (this.listener == listener) {
			if (!newListener) {
				listener.finish(recordEvent);
			}
			this.listener = NULL_LISTENER;
		}
	}

	public void removeRecordLoopListener(RecordLoopListener listener)
	{
		// If loopListener is not is list then do nothing
		if ((null == listener) || (!loopListeners.contains(listener))) {
			return;
		}

		// Remove loopListener from list
		loopListeners.remove(listener);
	}

	public void reset()
			throws IOException
	{
		// Reset to source by reqinding it
		source.rewind();

		// If the listener has had a record supplied to it then reset it by
		// sending a finish event.
		if (!newListener) {
			listener.finish(recordEvent);
			newListener = true;
		}

		// Tell the loopListeners that a loop has been reset.
		final int finished = loopListeners.size();
		for (int loopListener = 0;
			 finished != loopListener;
			 loopListener++) {
			((RecordLoopListener)
					loopListeners.get(loopListener)).loopReset(loopEvent);
		}
	}

	public void setConfiguration(Object configuration)
	{
		this.configuration = configuration;
		newConfiguration = true;
	}

	public synchronized void setInterruptRequested(boolean interruptRequested)
	{
		this.interruptRequested = interruptRequested;
	}

	public void setProgessByRecords(long records)
	{
		this.recordInterval = records;
	}

	public void setProgessByTime(long milliseconds)
	{
		this.millisInterval = milliseconds;
	}

	public void setRecordSource(SequentialRecordSource supplier)
	{
		this.source = supplier;
	}

	/**
	 * Supplies the specified record object, if it is not null, to the
	 * listener of this object.
	 *
	 * @param record the record object to be supplied.
	 */
	void supplyRecord(Object record)
	{
		// If no record to supply, do nothing
		if (null == record) {
			return;
		}

		// Set the record to be supplied as countable
		currentRecord = record;
		countableRecord = true;

		// Supply the record to the listener
		listener.recordSupplied(new RecordSuppliedEvent(this,
				record));
		currentRecord = null;
		totalSupplied++;

		// If supplied record is countable then count it
		if (countableRecord) {
			totalCountableSupplied++;
			supplied++;
			countableSupplied++;
		}

		// Tell the source that the loop has finished with the record.
		source.releaseRecord(record);

		// If there are loopListeners see if a progress report is needed.
		if (0 != loopListeners.size()) {

			// Check to see if the record progress update has expired.
			long recordsRemaining;
			if (0 < recordInterval) {
				recordsRemaining =
						recordInterval + lastProgressSupplied - totalSupplied;
			} else {
				recordsRemaining = IGNORE_PROGRESS;
			}

			// Check to see if the time progress update has expired.
			long millisRemaining;
			if (0 < millisInterval) {
				millisRemaining =
						millisInterval + lastProgressTime -
						System.currentTimeMillis();
			} else {
				millisRemaining = IGNORE_PROGRESS;
			}

			// If either interval has expired or no interval has be specified
			// and the default interval has expired, issue progress report
			if ((!(0 < recordsRemaining)) || (!(0 < millisRemaining)) ||
					((!(0 < recordInterval)) && (!(0 < millisInterval)) &&
					(0 > DEFAULT_MILLIS_INTERVAL -
					System.currentTimeMillis()))) {
				fireProgress(getProgress());
			}
		}
	}

	// static member functions (alphabetic)

	// Description of this object.
	// public String toString() {}

	// public static void main(String args[]) {}
}
