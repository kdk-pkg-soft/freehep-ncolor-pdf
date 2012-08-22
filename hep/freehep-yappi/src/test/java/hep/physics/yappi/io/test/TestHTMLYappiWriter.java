// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi.io.test;

import java.io.*;

import org.xml.sax.*;

import hep.physics.yappi.*;
import hep.physics.yappi.io.*;

/**
 * @author Mark Donszelmann
 * @version $Id: TestHTMLYappiWriter.java 8584 2006-08-10 23:06:37Z duns $
 */

public class TestHTMLYappiWriter {


    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: TestHTMLYappiWriter particleName outFile.html");
            System.exit(1);
        }
        
        try {
            XMLYappi yappi = new XMLYappi();

/*            
            yappi.read("hep/physics/yappi/io/test/Test-Family.xml");
            yappi.read("hep/physics/yappi/io/test/Test-Properties.xml");
            yappi.read("hep/physics/yappi/io/test/Test-NormalDecayChannels.xml");
            yappi.read("hep/physics/yappi/io/test/Test-RareDecayChannels.xml");
            yappi.read("hep/physics/yappi/io/test/Test-LimitDecayChannels.xml");
*/
            yappi.read("hep/physics/yappi/xml/2000/PDG-Family.xml");
            yappi.read("hep/physics/yappi/xml/2000/PDG-Properties.xml");
            yappi.read("hep/physics/yappi/xml/2000/PDG-NormalDecayChannels.xml");
            yappi.read("hep/physics/yappi/xml/2000/PDG-RareDecayChannels.xml");
            yappi.read("hep/physics/yappi/xml/2000/PDG-LimitDecayChannels.xml");
    
            long time = System.currentTimeMillis();      
            ParticleType particle = yappi.getParticle(args[0]);  
    
            if (particle == null) {
                System.out.println("Particle: "+args[0]+" not found.");
                System.exit(1);
            }
    
            HTMLYappiWriter writer = new HTMLYappiWriter(new FileWriter(args[1]));
            writer.writeHead(args[0]);
            writer.openTag("body");
            
            Family[] families = yappi.getFamilies(particle);
            for (int i=0; i<families.length; i++) {
                writer.write(families[i]);
            }
            
            writer.write(particle);
            writer.closeTag();  // body
            
            writer.close();        
            System.out.println("Generated page in "+((System.currentTimeMillis()-time)/1000.0)+" seconds");
            System.exit(0);
        } catch (SAXException saxe) {
            saxe.getException().printStackTrace();
        }
        System.exit(1);   
    }
    
    
}

