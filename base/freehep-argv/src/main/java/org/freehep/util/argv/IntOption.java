// Copyright 2004, FreeHEP.
package org.freehep.util.argv;

import java.math.BigDecimal;

/**
 * 
 *
 * @author Mark Donszelmann
 * @version $Id: IntOption.java 8584 2006-08-10 23:06:37Z duns $
 */ 
public class IntOption extends NumberOption {

    public IntOption(String flag, String name, String description) {
        super(flag, name, description);
    }
    
    public IntOption(String flag, String shortCut, String name, String description) {
        super(flag, shortCut, name, description);
    }
    
    public IntOption(String flag, String name, int defaultValue, String description) {
        super(flag, name, new BigDecimal(defaultValue), description);
    }
    
    public IntOption(String flag, String shortCut, String name, int defaultValue, String description) {
        super(flag, shortCut, name, new BigDecimal(defaultValue), description);
    }
    
    public int getInt() {
        return super.getValue().intValue();
    }
      
}
