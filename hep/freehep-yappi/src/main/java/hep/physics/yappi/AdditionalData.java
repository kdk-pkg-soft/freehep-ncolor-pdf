// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

/** 
 * Subpart of <code>Data</code> class. Here additional data will be stored.
 *
 * @author Patrick Hellwig
 * @version $Id: AdditionalData.java 8584 2006-08-10 23:06:37Z duns $
 *
 * @see Data
 */

public class AdditionalData
{
	private String name;
	private String value;
	
	/**
	 * @param name String defining the Name of the additional data
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return String giving the Name of the additional data
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param value String defining the value of the additional data
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/**
	 * @return String giving the value of the additional data
	 */
	public String getValue()
	{
		return value;
	}
}
