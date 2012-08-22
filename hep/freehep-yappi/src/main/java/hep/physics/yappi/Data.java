// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

/** Stores one Data entry
 *
 * @author Patrick Hellwig
 * @version $Id: Data.java 8584 2006-08-10 23:06:37Z duns $
 *
 */

import java.util.*;

public class Data 
{
	private String name;
	String texName;
	String value;       // to keep significant digits
	String unit;
	String posError;    // to keep significant digits
	String negError;    // to keep significant digits
	double confidenceLevel;
	double scaleFactor;
	
//	private String subInfo;
//	private HashSet additionalData;          // of Data
	
    /** 
	 * Constructor, initiates the HashSet
	 */
	public Data(String name)
	{
	    this.name = name;
	}
	
    /** 
	 * @return Name of the data item
	 */
	public String getName()
	{
		return name;
	}

    /** 
	 * @return LaTeX2e encoded name of the data item
	 */
	public String getTexName()
	{
		return texName;
	}

    /** 
	 * @return double value or NaN
	 */
	public double getValue()
	{
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
		    return Double.NaN;
		}
	}

    /** 
	 * @return string value
	 */
	public String getValueAsString()
	{
		return value;
	}

    /** 
	 * @return Unit of the data
	 */
	public String getUnit()
	{
		return unit;
	}
	
    /** 
	 * @return positive Error as double
	 */
	public double getPosError()
	{
		try {
			return Double.parseDouble(posError);
		} catch (Exception e) {
			return Double.NaN;
		}
	}

    /** 
	 * @return positive Error as String
	 */
	public String getPosErrorAsString()
	{
		return posError;
	}

    /** 
	 * @return negative Error as double
	 */
	public double getNegError()
	{
		try {
			return Double.parseDouble(negError);
		} catch (Exception e) {
			return Double.NaN;
		}
	}

    /** 
	 * @return negative Error as String
	 */
	public String getNegErrorAsString()
	{
		return negError;
	}

    /** 
	 * @return confidence level as double
	 */
	public double getConfidenceLevel()
	{
		return confidenceLevel;
	}
	
    /** 
	 * @return scale factor as double
	 */
	public double getScaleFactor()
	{
		return scaleFactor;
	}
	
    /** 
     * Sub Information about a data item
     * f.e. the property "Full" Width of the item "Width"
	 * @return Subinformation about data
	 */
/*
	public String getSubInfo()
	{
		return subInfo;
	}
*/	
    /** 
     * Sub Information about a data item
     * f.e. the property "Full" Width of the item "Width"
	 * @param subInfo SubInformation
	 */
/*
	public void setSubInfo(String subInfo)
	{
		 this.subInfo = subInfo;
	}
*/	
    /** 
	 * @param data Additional Data to store
	 */
/*
	public boolean addAdditionalData(AdditionalData data)
	{
		return additionalData.add(data);
	}
*/
    /** 
	 * @return Iterator that contains all <code>additionalData</code> items
	 */
/*
	public Iterator getAdditionalData()
	{
		return additionalData.iterator();	
	}
*/
    /** 
	 * @return HashSet that contains all <code>additionalData</code> items
	 */
	// FIXME: return a Set
/*
	public HashSet getAdditionalDataSet()
	{
		return additionalData;
	}
*/
}