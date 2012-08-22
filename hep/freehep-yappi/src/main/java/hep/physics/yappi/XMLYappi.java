// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

import java.io.*;
import java.util.*;

import org.xml.sax.*;

public class XMLYappi extends Yappi //implements ParticlePropertyProvider 
{
	boolean debug = false;

	public XMLYappi(String fileName) throws Exception
	{
	    read(fileName);
	}

    public XMLYappi() {
    }
	
	private void println(String text)
	{
		if (debug) System.out.println(text);
	}

	private void print(String text)
	{
		if (debug) System.out.print(text);
	}
		
    public void read(String fileName) throws SAXException, IOException {
        XMLYappiReader xmlReader = new XMLYappiReader(this);
		xmlReader.read(fileName);
    }
    
    public void read(Reader reader) throws SAXException, IOException {
        XMLYappiReader xmlReader = new XMLYappiReader(this);
		xmlReader.read(reader);
    }    
    
    public void write(String fileName) throws IOException {
        XMLYappiWriter xmlWriter = new XMLYappiWriter(fileName);
        xmlWriter.write(this);
    }
    
    public void displayFamilyTree() {
	    displayFamilyTree(getFamilies(), 0);
    }
	
	public static void displayFamilyTree(Iterator familyEntries, int indent)
	{
		while (familyEntries.hasNext())
		{
			for (int i=0; i<indent; i++) System.out.print(" ");
            Map.Entry familyEntry = (Map.Entry)familyEntries.next();
			Family family = (Family)familyEntry.getValue();
			System.out.println(family.getName());
    
            // particles
		    Iterator particleEntries = family.getParticles();
            while (particleEntries.hasNext()) {
                Map.Entry particleEntry = (Map.Entry)particleEntries.next();
                ParticleType particle = (ParticleType)particleEntry.getValue();
			    for (int i=0; i<indent; i++) System.out.print(" ");
			    System.out.println(particle.getName());
            }
            
            // subfamilies
			displayFamilyTree(family.getFamilies(), indent+3);
		}
	}
	
}
