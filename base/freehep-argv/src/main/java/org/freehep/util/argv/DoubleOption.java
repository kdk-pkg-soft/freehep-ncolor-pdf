// Copyright 2004, FreeHEP.
package org.freehep.util.argv;

import java.math.BigDecimal;

/**
 * 
 *
 * @author Mark Donszelmann
 * @version $Id: DoubleOption.java 8584 2006-08-10 23:06:37Z duns $
 */ 
public class DoubleOption extends NumberOption {

    public DoubleOption(String flag, String name, String description) {
        super(flag, name, description);
    }
    
    public DoubleOption(String flag, String shortCut, String name, String description) {
        super(flag, shortCut, name, description);
    }
    
    public DoubleOption(String flag, String name, double defaultValue, String description) {
        super(flag, name, new BigDecimal(defaultValue), description);
    }
    
    public DoubleOption(String flag, String shortCut, String name, double defaultValue, String description) {
        super(flag, shortCut, name, new BigDecimal(defaultValue), description);
    }
    
    public double getDouble() {
        return super.getValue().doubleValue();
    }
      
}
