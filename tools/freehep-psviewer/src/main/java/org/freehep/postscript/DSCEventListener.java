// Copyright 2004, FreeHEP.
package org.freehep.postscript;


/**
 * Class for signalling DSC comments
 *
 * @author Mark Donszelmann
 * @version $Id: DSCEventListener.java 10178 2006-12-08 09:03:07Z duns $
 */
public interface DSCEventListener {

    public void dscCommentFound(DSCEvent event);
}
