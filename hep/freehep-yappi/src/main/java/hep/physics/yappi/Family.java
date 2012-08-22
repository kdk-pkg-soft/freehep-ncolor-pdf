// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

/**
 * Class <code>Family</code> implementing <code>DecayProduct</code>
 * @see ParticleType 
 * @see DecayProduct 
 * @see DecayText
 * @author	Patrick Hellwig
 * @version	$Id: Family.java 8584 2006-08-10 23:06:37Z duns $
 * 
 */
import java.util.*;

public class Family extends DecayProduct
{
    private Map families;
    private Map particles;
        	
    public Family(String name)
    {
    	super(name);
    	families = new HashMap();
    	particles = new HashMap();
    }
    	
    public String getType()
    {
    	return "Family";
    }
    	
	public void addParticle(ParticleType particleType)
	{
	    System.out.println("Adding"+particleType);
		particles.put(particleType.getName(), particleType);
	}
	
	public Iterator getParticles()
	{
        return particles.values().iterator();
	}
	
	public ParticleType getParticle(String name)
	{
        return (ParticleType)particles.get(name);
	}
    	
    /**
     * @return	Iterator with data 
     */
    public Iterator getFamilies()
    {
        return families.values().iterator();
    }
    
    /**
     * Search for the special data field <code>fieldName</code> and return Data
     * class
     * @return	Data class
     */
    public Family getFamily(String familyName)
    {
        return (Family)families.get(familyName);
    }
    	
    /**
     */
    public void addFamily(Family family)
    {
    	families.put(family.getName(), family);
    }
    
    /**
     * Remove a <code>data</code> entry from the HashSet
     */
    public void removeFamily(String familyName)
    {
    	families.remove(familyName); 
    }
    
    /**
     * Remove a <code>data</code> entry from the HashSet
     */
    public void removeFamily(Family family)
    {
    	removeFamily(family.getName());
    }
    
    public String toString() {
        return getType()+": "+getName();
    }
}