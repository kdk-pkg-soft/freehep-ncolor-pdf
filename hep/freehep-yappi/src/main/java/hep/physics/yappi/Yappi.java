// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

import java.util.*;

/**
 * Yappi: Yet Another Particle Propery Interface
 *
 * @author Patrick Hellwig
 * @author Mark Donszelmann
 * @version	$Id: Yappi.java 8584 2006-08-10 23:06:37Z duns $
 */

public class Yappi
{
	private Map particlesByName;    // of ParticleType stored by Name
	private Map particlesByPDGID;   // of ParticleType stored by PDGID
	private Map families;	
	
	public Yappi()
	{
		particlesByName = new HashMap();
		particlesByPDGID = new HashMap();
		families = new HashMap();
	}
	
	public void addParticle(ParticleType particleType)
	{
		particlesByName.put(particleType.getName(), particleType);
		if (particleType.getPDGID() != null) {
		    particlesByPDGID.put(particleType.getPDGID(), particleType);
		}
	}
	
	public Iterator getParticles()
	{
        return particlesByName.entrySet().iterator();
	}
	
	public ParticleType getParticle(String name)
	{
        return (ParticleType)particlesByName.get(name);
	}	

	public ParticleType getParticle(PDGID pdgid)
	{
        return (ParticleType)particlesByPDGID.get(pdgid);
	}	

    /**
     * @return	Iterator with data 
     */
    public Iterator getFamilies()
    {
        return families.entrySet().iterator();
    }

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
     */
    public void removeFamily(String familyName)
    {
        families.remove(familyName);
    }

    /**
     */
    public void removeFamily(Family family)
    {
        removeFamily(family.getName());
    }

    public Family[] getFamilies(ParticleType particle) {
        Vector result = new Vector();
        
        getFamilies(families.values().iterator(), particle, result);
        Family[] f = new Family[result.size()];
        result.copyInto(f);
        return f;  
    }
    
    private static void getFamilies(Iterator familyIterator, ParticleType particle, Vector result) {
        while (familyIterator.hasNext()) {
            Family f = (Family)familyIterator.next();
            System.out.println(f);
            
            // look for particle type
            ParticleType p = f.getParticle(particle.getName());
            System.out.println(p);
            if (p != null) {
                result.addElement(f);
            }
            
            // handle subfamilies
            getFamilies(f.getFamilies(), particle, result);
        }
    }
}