// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

import java.io.*;
import java.util.*;

import org.freehep.xml.util.*;

import hep.physics.yappi.*;

/**
 * @author Mark Donszelmann
 * @version $Id: XMLYappiWriter.java 8584 2006-08-10 23:06:37Z duns $
 */

public class XMLYappiWriter extends XMLWriter {	
    
    private static String nameSpace = "ppml";
    private DecayGroup currentGroup = null;
    
    public XMLYappiWriter(String filename) throws IOException {
        this(new BufferedWriter(new FileWriter(filename)));
    }
    
    public XMLYappiWriter(Writer writer) {
        super(writer);
        openDoc();
    }
    
    public void close() throws IOException {
        closeDoc();
        super.close();
    }
    
    public void write(Yappi yappi) {
        setAttribute("xmlns", nameSpace, "http://www.freehep.org/Yappi");
        setAttribute("xmlns", "xsi", "http://www.w3.org/1999/XMLSchema-instance");
        setAttribute("xsi", "schemaLocation", "PPML.xsd");
        openTag(nameSpace, "yappi");
        
        // Families
        for (Iterator i = yappi.getFamilies(); i.hasNext(); ) {
            Map.Entry entry = (Map.Entry)i.next();
            write((Family)entry.getValue());
        }
        
        // Particles
        for (Iterator i = yappi.getParticles(); i.hasNext(); ) {
            Map.Entry entry = (Map.Entry)i.next();
            write((ParticleType)entry.getValue());
        }
                
        closeTag();
    }
    
    public void write(Family family) {
        setAttribute("name", family.getName());
        setAttribute("texName", family.getTexName());
        openTag(nameSpace, "Family");
        
        // Sub-families
        for (Iterator i = family.getFamilies(); i.hasNext(); ) {
            Map.Entry entry = (Map.Entry)i.next();
            write((Family)entry.getValue());
        }
        closeTag();
    }
    
    public void write(ParticleType particle) {
        currentGroup = null;
        
        setAttribute("name", particle.getName());
        setAttribute("texName", particle.getTexName());
        setAttribute("antiName", particle.getAntiName());
        setAttribute("antiTexName", particle.getAntiTexName());
        setAttribute("PDGID", particle.getPDGID().getID());
        openTag(nameSpace, "ParticleType");
        
        // Data
        for (Iterator i = particle.getData(); i.hasNext(); ) {
            Map.Entry entry = (Map.Entry)i.next();
            write((Data)entry.getValue());
        }
        
        // Decays
        for (Iterator i = particle.getDecayChannels(); i.hasNext(); ) {
            write((DecayChannel)i.next());
        }
                
        closeTag();
    }
    
    public void write(Data data) {
        setAttribute("name", data.getName());
        setAttribute("texName", data.getTexName());
        setAttribute("value", data.getValueAsString());
        setAttribute("posError", data.getPosErrorAsString());
        setAttribute("negError", data.getNegErrorAsString());
        setAttribute("confidenceLevel", data.getConfidenceLevel());
        setAttribute("scaleFactor", data.getScaleFactor());
        printTag(nameSpace, "Data");
    }
    
    // FIXME: add reference
    public void write(DecayChannel decay) {
        DecayGroup group = decay.getDecayGroup();
        if (currentGroup != group) {
            if (currentGroup != null) {
                closeTag();
            }
            if (group != null) {
                setAttribute("name", group.getName());
                openTag(nameSpace, "DecayGroup");
            }
            currentGroup = group;
        }
        setAttribute("name", decay.getName());
        setAttribute("texName", decay.getTexName());
        setAttribute("fraction", decay.getFractionAsString());
        setAttribute("posError", decay.getPosErrorAsString());
        setAttribute("negError", decay.getNegErrorAsString());
        setAttribute("confidenceLevel", decay.getConfidenceLevel());
        setAttribute("scaleFactor", decay.getScaleFactor());
        setAttribute("P", decay.getP());
        setAttribute("PUnit", decay.getPUnit());
        openTag(nameSpace, "Decay");
        closeTag();
    }
}