// Copyright 2004, FreeHEP.
package org.freehep.util.argv;


/**
 * 
 *
 * @author Mark Donszelmann
 * @version $Id: DoubleParameter.java 8584 2006-08-10 23:06:37Z duns $
 */ 
public class DoubleParameter extends NumberParameter {

    public DoubleParameter(String name, String description) {
        super(name, description);
    }
        
    public double getDouble() {
        return super.getValue().doubleValue();
    }
      
}
