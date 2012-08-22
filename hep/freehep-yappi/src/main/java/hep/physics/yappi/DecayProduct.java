// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

/**
 * General description of a <code>DecayProduct</code>. This class is extended
 * by the classes @link ParticleType , @link Family , @link DecayText 
 * @see ParticleType 
 * @see Family 
 * @see DecayText
 * @author	Patrick Hellwig
 * @version	$Id: DecayProduct.java 8584 2006-08-10 23:06:37Z duns $
 * 
 */
import java.util.*;

public abstract class DecayProduct
{
    private String name;
    String texName;
    
    private List comments;
    	
    /**
     * Constructs the ParticleType class
     */
    public DecayProduct(String name)
    {
        this.name = name;
    	comments = new ArrayList();
    }

    public abstract String getType();
    	
    /**
     * @return Name of the decay product
     */
    public String getName()
    {
    	return name;
    }
    
    /**
     * @return Name of the decay product in LaTeX2e format (math-mode encoding)
     */
    public String getTexName()
    {
    	return texName;
    }
    
    /**
     * @return	Iterator with all comments
     */
    public Iterator getComment()
    {
        return comments.iterator();
    }
    
    /**
     * Add a <code>comment</code> entry to the HashSet
     * @param	comment a comment to add
     * @return	true if operation was successful
     */
    public boolean addComment(String comment)
    {
    	return comments.add(comment);
    }
    	
    /**
     * Remove a <code>comment</code> entry from the HashSet
     * @param	comment Comment which should be removed
     * @return	true if operation was successful
     */
    public boolean removeComment(String comment)
    {
        comments.remove(comment);
        return true; 
    }
}