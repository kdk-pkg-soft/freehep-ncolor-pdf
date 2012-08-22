/*
 * class: RecordLoopAdapter
 *
 * Version $Id: RecordLoopAdapter.java 8584 2006-08-10 23:06:37Z duns $
 *
 * Date: March 10 2003
 *
 * (c) 2003 IceCube Collaboration
 */

package org.freehep.record.loop.event;

/**
It is an implementation of the <code>{@link RecordLoopListener}</code>
interface where all the methods are empty. It exists a conveniece class for
creators of <code>RecordLoopListener</code> objects.
 *
 * @version $Id: RecordLoopAdapter.java 8584 2006-08-10 23:06:37Z duns $
 * @author patton
 */
public class RecordLoopAdapter
		implements RecordLoopListener
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
    public RecordLoopAdapter()
    {
    }

    // instance member function (alphabetic)
	public void loopBeginning(LoopEvent loopEvent)
	{
	}

	public void loopEnded(LoopEvent loopEvent)
	{
	}

	public void loopReset(LoopEvent loopEvent)
	{
	}

	public void progress(LoopProgressEvent loopProgressEvent)
	{
	}
	// static member functions (alphabetic)

    // Description of this object.
    // public String toString() {}

    // public static void main(String args[]) {}
}
