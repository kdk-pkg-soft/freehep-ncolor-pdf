// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

/**
 * @author Mark Donszelmann
 * @version	$Id: DecayGroup.java 8584 2006-08-10 23:06:37Z duns $
 * 
 */

public class DecayGroup {
    
    private String name;
    	
    public DecayGroup(String name) {
        this.name = name;
    }

    /**
     * @return Name of the group
     */
    public String getName() {
    	return name;
    }
}