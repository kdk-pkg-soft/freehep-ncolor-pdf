// Copyright 2003-2005, FreeHEP.
package org.freehep.util.io.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;

import junit.framework.AssertionFailedError;

import org.freehep.util.io.XMLSequence;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * Test for XML Sequence.
 * 
 * @author Mark Donszelmann
 * @version $Id: XMLSequenceTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class XMLSequenceTest extends AbstractStreamTest {

    /**
     * Test XMLSequence reading using XML Parsers.
     * 
     * @throws Exception if something goes wrong
     */
    public void testXMLSequence() throws Exception {
        File testFile = new File(testDir, "XMLSequence.txt");
         
        XMLSequence sequence = new XMLSequence(new BufferedInputStream(
                new FileInputStream(testFile)));
        
        if (!sequence.markSupported()) throw new AssertionFailedError("Mark is not supported for XMLSequence");
        
        sequence.mark((int) testFile.length() + 1);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XMLReader xmlReader = factory.newSAXParser().getXMLReader();

        int i = 0;
        while (sequence.hasNext()) {
            InputStream input = sequence.next();
            InputSource source = new InputSource(input);
            xmlReader.parse(source);
            input.close();
            i++;
        }

        sequence.reset();
        i = 0;
        while (sequence.hasNext()) {
            InputStream input = sequence.next();
            InputSource source = new InputSource(input);
            xmlReader.parse(source);
            input.close();
            i++;
        }
        sequence.close();
    }
}
