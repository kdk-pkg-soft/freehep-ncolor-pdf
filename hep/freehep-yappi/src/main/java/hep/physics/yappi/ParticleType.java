// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

/**
 * Implement a Particle. This implements the @link DecayProduct interface.
 *
 * @see DecayProduct
 * @see Family 
 * @see DecayText
 *
 * @author	Patrick Hellwig
 * @version	$Id: ParticleType.java 8584 2006-08-10 23:06:37Z duns $
 * 
 */
import java.util.*;

public class ParticleType extends DecayProduct
{
    String antiName;
    String antiTexName;
    PDGID pdgid;
    	
    private Map data;
    private Map decaysByName;
    private Map decays;
    private List groups;
    	
    /**
     * Constructs the ParticleType class
     */
    public ParticleType(String name)
    {
    	super(name);
    	data = new HashMap();
    	decaysByName = new HashMap();
    	decays = new TreeMap();
    	groups = new ArrayList();
    }
    
    public String getType()
    {
    	return "ParticleType";
    }
    	
    /**
     * @return Name of the antiparticle of the decay product
     */
    public String getAntiName()
    {
    	return antiName;
    }
    	
    /**
     * @return Name of the antiparticle of the decay product in LaTeX2e format (math-mode encoding)
     */
    public String getAntiTexName()
    {
    	return antiTexName;
    }
    
    public PDGID getPDGID() {
        return pdgid;
    }
    
    /**
     * @return Iterator with data 
     */
    public Iterator getData()
    {
        return data.values().iterator();
    }
    
    /**
     * Search for the special data field <code>fieldName</code> and return Data
     * class
     * @return Data
     */
    public Data	getData(String fieldName)
    {
        return (Data)data.get(fieldName);
    }
    	
    /**
     * Add a <code>data</code> entry to the HashSet
     */
    public void addData(Data data)
    {
        // FIXME: take the return out
        this.data.put(data.getName(), data);
    }
    
    /**
     * Remove a <code>data</code> entry from the HashSet
     */
    public void removeData(String fieldName)
    {
        data.remove(fieldName);
    }

    /**
     * Return all decay channels in an iterator
     * @return	Iterator with all decay channels
     */
    public Iterator getDecayChannels()
    {
        return decays.values().iterator();
    }
    
    /**
     * Add a <code>DecayChannel</code> entry to the HashSet
     */
    public void addDecayChannel(DecayChannel decayChannel)
    {
    	decaysByName.put(decayChannel.getName(), decayChannel);
    	decays.put(decayChannel, decayChannel);
    }
    
    public DecayChannel getDecayChannel(String name)
    {
    	return (DecayChannel)decaysByName.get(name);
    }
    
    /**
     * Remove a <code>decayChannel</code> entry from the HashSet
     * @param	decayChannel DecayChannel which should be removed
     */
    public void removeDecayChannel(DecayChannel decayChannel)
    {
        decaysByName.remove(decayChannel);
        decays.remove(decayChannel);
    }
    
    // groups are preserved in order
    public void addDecayGroup(DecayGroup group) {
        groups.add(group);
    }
    
    public DecayGroup getDecayGroup(String name) {
        for (Iterator i=groups.iterator(); i.hasNext(); ) {
            DecayGroup group = (DecayGroup)i.next();
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;   
    }
    
    public Iterator getDecayGroups() {
        return groups.iterator();
    }
    
    public String toString() {
        return getType()+": "+getName() + ": " + getAntiName();
    }
}