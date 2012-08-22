// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

import java.util.*;

public class DecayChannel implements Comparable {
    
	private String name;
	DecayGroup group;
	String texName;
	String fraction;        // to keep significant digits
	String posError;        // to keep significant digits
	String negError;        // to keep significant digits
	double confidenceLevel;
	double scaleFactor;
	String P;
	String PUnit;
	
	private List decayParticles;     // of DecayProducts
	
	public DecayChannel(String name, String fraction)
	{
	    this.name = name;
	    this.fraction = fraction;
		decayParticles = new ArrayList();
	}
	
	public String getName()
	{
		return name;
	}

	public String getTexName()
	{		
		return texName;
	}
	
	public DecayGroup getDecayGroup() {
	    return group;
	}

    public double getFraction() {
        try {
            return Double.parseDouble(fraction);
        } catch (Exception nfe) {
            return Double.NaN;
        }
    }

	public String getFractionAsString()
	{
		return fraction;
	}
	
    public double getPosError() {
        try {
            return Double.parseDouble(posError);
        } catch (Exception nfe) {
            return Double.NaN;
        }
    }

	public String getPosErrorAsString()
	{
		return posError;
	}

    public double getNegError() {
        try {
            return Double.parseDouble(negError);
        } catch (Exception nfe) {
            return Double.NaN;
        }
    }

	public String getNegErrorAsString()
	{
		return negError;
	}
	
	public double getConfidenceLevel()
	{
		return confidenceLevel;
	}
	
	public double getScaleFactor()
	{
		return scaleFactor;
	}
	
	public String getP()
	{
		return P;
	}
		
	public String getPUnit()
	{
		return PUnit;
	}
	
	public void addDecayParticle(DecayProduct decayProduct)
	{
		decayParticles.add(decayProduct);
	}

	public Iterator getDecayParticles()
	{
		return decayParticles.iterator();	
	}
	
	public int compareTo(Object obj) {
        DecayChannel channel = (DecayChannel)obj;
        
        double f1 = getFraction();
        double f2 = channel.getFraction();
        
        if (Double.isNaN(f1)) {
            if (Double.isNaN(f2)) {
                // both NaN
                String s1 = getFractionAsString();
                String s2 = channel.getFractionAsString();
                if ((s1 == null) && (s2 == null)) return 0;
                if (s1 == null) return -1;
                if (s2 == null) return +1;

                if (s1.startsWith("<")) {
                    if (s2.startsWith("<")) {
                        // both start with <
                        Double d = new Double(s1.substring(1));
                        return -d.compareTo(new Double(s2.substring(1)));
                    } else {
                        // s1 starts with <
                        return +1;
                    }
                } else if (s2.startsWith("<")) {
                    return -1;
                }
            
                // real strings
                return -(s1.compareTo(s2));
            } else {
                // f1 is NaN
                return +1;
            }
        } else if (Double.isNaN(f2)) {
            // f2 is NaN
            return -1;
        }
        
//        System.out.println(f1+", "+f2);
          
        // both are real numbers
        return (f1 < f2) ? +1 : (f1 == f2) ? 0 : -1;
	}
}