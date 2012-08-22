// Copyright 2004, FreeHEP.
package org.freehep.util.argv;


/**
 * 
 *
 * @author Mark Donszelmann
 * @version $Id: IntParameter.java 8584 2006-08-10 23:06:37Z duns $
 */ 
public class IntParameter extends NumberParameter {

    public IntParameter(String name, String description) {
        super(name, description);
    }
        
    public int getInt() {
        return super.getValue().intValue();
    }
      
}
