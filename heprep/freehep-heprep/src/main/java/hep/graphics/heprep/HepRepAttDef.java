// AID-GENERATED
// =========================================================================
// This class was generated by AID - Abstract Interface Definition          
// DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
// =========================================================================

// Copyright 2000-2005, FreeHEP.
package hep.graphics.heprep;


/**
 * HepRepAttDef interface.
 *
 * @author Mark Donszelmann
 */
public interface HepRepAttDef {

    /**
     * Returns the mixed case name of this attdef.
     *
     * @return name.
     */
    public String getName();

    /**
     * Returns the lowercased name of this attdef.
     *
     * @return lowercased name.
     */
    public String getLowerCaseName();

    /**
     * Returns a description of this attdef.
     *
     * @return description.
     */
    public String getDescription();

    /**
     * Returns category of this attdef.
     *
     * @return category.
     */
    public String getCategory();

    /**
     * Returns any extra information of this attdef.
     *
     * @return extra info.
     */
    public String getExtra();

    /**
     * Returns a deep copy of this attdef.
     *
     * @return copy of this attdef.
     * @throws CloneNotSupportedException if copying is not possible.
     */
    public HepRepAttDef copy() throws CloneNotSupportedException;
} // class or interface

