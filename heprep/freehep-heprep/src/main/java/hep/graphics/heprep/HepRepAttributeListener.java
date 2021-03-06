// AID-GENERATED
// =========================================================================
// This class was generated by AID - Abstract Interface Definition          
// DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
// =========================================================================

// Copyright 2000-2005, FreeHEP.
package hep.graphics.heprep;

import java.awt.Color;

/**
 * HepRepAttributeListener interface. The implementor is called back for changes
 * of attributes while using the HepRepIterator to iterate over all the HepRepInstances.
 *
 * All names are lowercased.
 *
 * @author Mark Donszelmann
 */
public interface HepRepAttributeListener {

    /**
     * Called if attribute key changes its value.
     *
     * @param instance instance for which this attribute is set.
     * @param key name of the changed attribute.
     * @param value value of the changed attribute.
     * @param lowerCaseValue lower case value of the changed attribute.
     * @param showLabel value of showLabel.
     */
    public void setAttribute(HepRepInstance instance, String key, String value, String lowerCaseValue, int showLabel);

    /**
     * Called if attribute key changes its value.
     *
     * @param instance instance for which this attribute is set.
     * @param key name of the changed attribute.
     * @param value value of the changed attribute.
     * @param showLabel value of showLabel.
     */
    public void setAttribute(HepRepInstance instance, String key, Color value, int showLabel);

    /**
     * Called if attribute key changes its value.
     *
     * @param instance instance for which this attribute is set.
     * @param key name of the changed attribute.
     * @param value value of the changed attribute.
     * @param showLabel value of showLabel.
     */
    public void setAttribute(HepRepInstance instance, String key, long value, int showLabel);

    /**
     * Called if attribute key changes its value.
     *
     * @param instance instance for which this attribute is set.
     * @param key name of the changed attribute.
     * @param value value of the changed attribute.
     * @param showLabel value of showLabel.
     */
    public void setAttribute(HepRepInstance instance, String key, int value, int showLabel);

    /**
     * Called if attribute key changes its value.
     *
     * @param instance instance for which this attribute is set.
     * @param key name of the changed attribute.
     * @param value value of the changed attribute.
     * @param showLabel value of showLabel.
     */
    public void setAttribute(HepRepInstance instance, String key, double value, int showLabel);

    /**
     * Called if attribute key changes its value.
     *
     * @param instance instance for which this attribute is set.
     * @param key name of the changed attribute.
     * @param value value of the changed attribute.
     * @param showLabel value of showLabel.
     */
    public void setAttribute(HepRepInstance instance, String key, boolean value, int showLabel);

    /**
     * Called if attribute key is removed from the attribute set.
     *
     * @param instance instance for which this attribute is set.
     * @param key name of the removed attribute.
     */
    public void removeAttribute(HepRepInstance instance, String key);
} // class or interface

