// Copyright 2000, SLAC, Stanford, California, U.S.A.
package jas.util.xml.parserwrappers;

import jas.util.xml.JASDOMParser;

import java.io.IOException;
import java.io.Reader;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * An implementation of DOMParser for the Xerces XML parser
 *
 * @version $Id: XercesDOMParser.java 11553 2007-06-05 22:06:23Z duns $
 * @see org.freehep.xml.util.DOMParser
 */

public class XercesDOMParser extends JASDOMParser 
{
	public Document parse(Reader in, String fileName) throws JASDOMParser.JASXMLException
	{
		return parse(in,fileName,null);
	}
	public Document parse(Reader in, final String fileName, EntityResolver resolver) throws JASXMLException
	{
		try
		{
			org.apache.xerces.parsers.DOMParser parser = new org.apache.xerces.parsers.DOMParser();
         parser.setFeature("http://xml.org/sax/features/validation", true);            
         XMLErrorHandler errorHandler = new XMLErrorHandler(fileName);
         parser.setErrorHandler(errorHandler);

			if (resolver != null) parser.setEntityResolver(resolver);
			parser.parse(new InputSource(in));
			if (errorHandler.getLevel() > 1) throw new SAXException("Error during XML file parsing");
			return parser.getDocument();
		}
		catch (SAXException x)
		{
			throw new JASDOMParser.JASXMLException("Syntax error parsing XML file",x);
		}
		catch (IOException x)
		{
			throw new JASDOMParser.JASXMLException("IO error parsing XML file",x);
		}
	}
}
