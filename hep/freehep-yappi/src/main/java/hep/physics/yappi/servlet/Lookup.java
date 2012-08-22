// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi.servlet;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.xml.sax.*;

import hep.physics.yappi.*;
import hep.physics.yappi.io.*;

/**
 * YaPPI Servlet
 *
 * This class implements a servlet for the following URLs:
 *
 * ..../Lookup?particle=eta(c)(1S)
 * ..../Lookup?pdgid=448
 * ..../Lookup?family=leptons
 *
 * @author Patrick Hellwig
 * @author Mark Donszelmann
 * @version $Id: Lookup.java 8584 2006-08-10 23:06:37Z duns $
 */
public class Lookup extends HttpServlet {

    private final XMLYappi yappi = new XMLYappi();

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
        
        String configfile = config.getInitParameter("YappiConfig");
        
        if (configfile == null) {
            configfile = "/hep/physics/yappi/servlet/YappiServlet.cfg";
        }
        
        try {
		    loadXML(config.getServletContext(), configfile);
        } catch (Exception e) {
            throw new ServletException("Yappi Particle Lookup", e);
        }
    }

    private void loadXML(ServletContext context, String configfile) throws IOException, SAXException
    {
        URL url = context.getResource(configfile);
        if (url != null) {
            System.out.println("Loading configfile from URL: "+url);
            BufferedReader infile = new BufferedReader(
                                    new InputStreamReader(url.openStream()));
            
            String line;
            
            while ((line = infile.readLine()) != null) {
                if (!line.startsWith("#")) {
                    line = line.trim();
                    url = context.getResource(line);
                    if (url != null) {
                        System.out.println("Loading XML file from URL: "+url);
                        yappi.read(new BufferedReader(
                                   new InputStreamReader(url.openStream())));
                    } else {
                        System.out.println("Resource: "+line+" not available");
                    }
                }
            }
            
            infile.close();
        } else {
            System.out.println("Resource: "+configfile+" not available");
        }
    }


    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HTMLYappiWriter writer = new HTMLYappiWriter(out);
                        
        if (request.getParameter("particle") != null) {
            String name = request.getParameter("particle");
            writeParticle(writer, yappi.getParticle(name), "Particle: "+name+" not found.");
            
        } else if (request.getParameter("pdgid") != null) {
            PDGID pdgid = new PDGID(Integer.parseInt(request.getParameter("pdgid")));
            writeParticle(writer, yappi.getParticle(pdgid), "Particle with pdgid: "+pdgid+" not found.");

        } else if (request.getParameter("family") != null) {
            String family = request.getParameter("family");
            writeParticle(writer, null, "Family: "+family+" not found.");
            
        } else {
            
            writer.writeHead("Yappi lookup error.");
            writer.openTag("body");
            writer.openTag("h1");
            writer.println("Error, no particle, pdgid or family supplied.");
            writer.closeTag();  // h1
            writer.closeTag();  // body  
        } 
        writer.close();        
        out.close();
    }
    
    private void writeParticle(HTMLYappiWriter writer, ParticleType particle, String msg) {
        if (particle == null) {
            writer.writeHead(msg);
            writer.openTag("body");
            writer.openTag("h1");
            writer.println(msg);
            writer.closeTag();  // h1
            writer.closeTag();  // body
        } else {
            writer.writeHead(particle.getName());
            writer.openTag("body");

            Family[] families = yappi.getFamilies(particle);
            for (int i=0; i<families.length; i++) {
                writer.write(families[i]);
            }
            
            writer.write(particle);

            writer.closeTag();  // body
        }
    }
    
}
