// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

/**
 * Class <code>DecayText</code> implementing <code>DecayProduct</code>
 * @see ParticleType 
 * @see DecayProduct 
 * @see Family
 * @author	Patrick Hellwig
 * @version	$Id: DecayText.java 8584 2006-08-10 23:06:37Z duns $
 * 
 */
import java.util.*;

public class DecayText extends DecayProduct
{
	public DecayText(String name)
	{
	    super(name);
    }

    public String getType()
    {
    	return "Text";
    }    	
   
    public String toString() {
        return getType()+": "+getName();
    }
}