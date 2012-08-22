/*
 * Stoppable.java
 *
 * Created on February 27, 2001, 5:51 PM
 */

package org.freehep.application;
import javax.swing.BoundedRangeModel;

/**
 * A interface to be implemented by things that can be stopped.
 * @author  tonyj
 * @version $Id: Stoppable.java 8584 2006-08-10 23:06:37Z duns $
 * @see StatusBar
 */
public interface Stoppable 
{
    BoundedRangeModel getModel();
    void stop();
}
