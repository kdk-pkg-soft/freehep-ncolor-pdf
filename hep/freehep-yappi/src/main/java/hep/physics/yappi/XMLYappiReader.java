// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

import java.io.*;
import java.util.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import hep.physics.yappi.*;

public class XMLYappiReader extends DefaultHandler
{
	boolean debug = false;

	private static boolean setValidation    = false; //defaults
    private static boolean setNameSpaces    = true;
    private static boolean setSchemaSupport = true;

	private XMLReader xmlReader = null;
	private Yappi yappi = null;
    private Family family = null;
	private ParticleType particle = null;
    private DecayGroup group = null;

    public XMLYappiReader(Yappi yappi)
    {
        this.yappi = yappi;


        try {
    		// Create SAX2 parser ...
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xmlReader = factory.newSAXParser().getXMLReader();

    		// Set the ContentHandler ...
    		xmlReader.setContentHandler(this);

	        xmlReader.setFeature( "http://xml.org/sax/features/validation", setValidation);
	    } catch (Exception e) {}

	    try {
	        xmlReader.setFeature( "http://xml.org/sax/features/namespaces", setNameSpaces );
	    } catch (Exception e) {}

	    try {
	        xmlReader.setFeature( "http://apache.org/xml/features/validation/schema", setSchemaSupport );
	    } catch (Exception e) {}
    }

	/**
	 * XMLReader
	 *
	 * Read another XML file
	 */
	public void read(String filename) throws SAXException, FileNotFoundException, IOException {
		read(new FileReader(filename));
	}

	public void read(Reader reader) throws SAXException, FileNotFoundException, IOException {
        family = null;
        particle = null;
	    group = null;
		// Parse the file ...
		xmlReader.parse(new InputSource(reader));
    }

	public void startDocument() throws SAXException
	{
		// START DOCUMENT
	}

	public void endDocument() throws SAXException
	{
		// END DOCUMENT
	}

	public void startElement(String namespaceURI, String tag, String qName, Attributes attr) throws SAXException
	{
		if (tag.equals("ParticleType")) {

			String name = attr.getValue("name");
            particle = yappi.getParticle(name);
            if (particle == null) {
                particle = new ParticleType(name);
            }

			if (attr.getValue("texName") != null) particle.texName = attr.getValue("texName");
			if (attr.getValue("antiName") != null) particle.antiName = attr.getValue("antiName");
			if (attr.getValue("antiTexName") != null) particle.antiTexName = attr.getValue("antiTexName");
			try {
			    PDGID pdgid = new PDGID(Integer.parseInt(attr.getValue("PDGID")));
			    if (pdgid != null) particle.pdgid = pdgid;
			} catch (NumberFormatException nfe) {}

			yappi.addParticle(particle);

			if (family != null) {
			    family.addParticle(particle);
			}

		} else if (tag.equals("Data")) {

			String name = attr.getValue("name");
            Data data = particle.getData(name);
            if (data == null) {
                data = new Data(name);
                particle.addData(data);
            }

			if (attr.getValue("texName") != null) data.texName = attr.getValue("texName");
			if (attr.getValue("value") != null) data.value = attr.getValue("value");
			if (attr.getValue("unit") != null) data.unit = attr.getValue("unit");
            if (attr.getValue("posError") != null) data.posError = attr.getValue("posError");
            if (attr.getValue("negError") != null) data.negError = attr.getValue("negError");
            if (attr.getValue("confidenceLevel") != null) data.confidenceLevel = Double.parseDouble(attr.getValue("confidenceLevel"));
            if (attr.getValue("scaleFactor") != null) data.scaleFactor = Double.parseDouble(attr.getValue("scaleFactor"));

		} else if (tag.equals("PPML")) {
			// ignored
/*
		} else if (tag.equals("AdditionalData")) {
			additionalData = new AdditionalData();
			println("AdditionalData="+Name);
			for (int i=0;i<attr.getLength();i++)
			{
				if (attr.getLocalName(i).equals("name")) additionalData.setName(attr.getValue(i));
				if (attr.getLocalName(i).equals("value")) additionalData.setName(attr.getValue(i));
			}
			if (!isNewParticle)
			{
				println("   Looking for existing additional Data");
				Iterator searchData = data.getAdditionalData();
				if (searchData != null) {
    				while (searchData.hasNext())
    				{
    					AdditionalData searchAdd = (AdditionalData) searchData.next();
    					if (searchAdd.getName().equals(additionalData.getName()))
    					{
    						searchAdd.setValue(additionalData.getValue());
    					}
    				}
				} else {
					println("   No Additional Data field was found");
				}
			}
*/
		} else if (tag.equals("Decay")) {

			String name = attr.getValue("name");
			String fraction = attr.getValue("fraction");
            DecayChannel decay = particle.getDecayChannel(name);
            if (decay == null) {
                decay = new DecayChannel(name, fraction);
                particle.addDecayChannel(decay);
            } else {
                // change fraction and reindex
                decay.fraction = fraction;
                particle.addDecayChannel(decay);
            }

            if (group != null) {
                decay.group = group;
            }

			if (attr.getValue("texName") != null) decay.texName = attr.getValue("texName");
            if (attr.getValue("posError") != null) decay.posError = attr.getValue("posError");
            if (attr.getValue("negError") != null) decay.negError = attr.getValue("negError");
            if (attr.getValue("confidenceLevel") != null) decay.confidenceLevel = Double.parseDouble(attr.getValue("confidenceLevel"));
            if (attr.getValue("scaleFactor") != null) decay.scaleFactor = Double.parseDouble(attr.getValue("scaleFactor"));
			if (attr.getValue("P") != null) decay.P = attr.getValue("P");
			if (attr.getValue("PUnit") != null) decay.PUnit = attr.getValue("PUnit");

	    } else if (tag.equals("DecayGroup")) {
	        String groupName = attr.getValue("name");
	        group = particle.getDecayGroup(groupName);
	        if (group == null) {
	            group = new DecayGroup(groupName);
	            particle.addDecayGroup(group);
	        }
	    } else if (tag.equals("ParticleRef")) {
            // FIXME: ignored for now
		} else if (tag.equals("Source")) {
		} else if (tag.equals("Author")) {
		} else if (tag.equals("Name")) {
		} else if (tag.equals("Link")) {
		} else if (tag.equals("Date")) {
		} else if (tag.equals("Type")) {
		} else if (tag.equals("Reliability")) {
		} else if (tag.equals("Family")) {

			String name = attr.getValue("name");
            family = yappi.getFamily(name);
            if (family == null) {
                family = new Family(name);
                yappi.addFamily(family);
            }

			if (attr.getValue("texName") != null) family.texName = attr.getValue("texName");

		} else {
			System.err.println("Unknown tag: "+tag);
	    }
	}

	public void endElement(String namespaceURI, String tag, String qName) throws SAXException
	{
		// END ELEMENT
		if (tag.equals("ParticleType")) {
		    particle = null;
		} else if (tag.equals("Data")) {
            // ignored
		} else if (tag.equals("PPML")) {
            // ignored
		} else if (tag.equals("AdditionalData")) {
            // ignored
		} else if (tag.equals("DecayGroup")) {
		    group = null;
	    } else if (tag.equals("Decay")) {
	    } else if (tag.equals("ParticleRef")) {
		} else if (tag.equals("Source")) {
		} else if (tag.equals("Author")) {
		} else if (tag.equals("Name")) {
		} else if (tag.equals("Link")) {
		} else if (tag.equals("Date")) {
		} else if (tag.equals("Type")) {
		} else if (tag.equals("Reliability")) {
		} else if (tag.equals("Family")) {
            family = null;
		} else {
			System.err.println("Unknown end tag: "+tag);
        }
	}

	public void characters (char[] ch, int start, int length) throws SAXException
	{
        // ignored
	}

	private void println(String text)
	{
		if (debug) System.out.println(text);
	}

	private void print(String text)
	{
		if (debug) System.out.print(text);
	}
}