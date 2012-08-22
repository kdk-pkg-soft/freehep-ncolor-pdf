// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi.servlet;

/**
 * Create a LaTeX file out of XMLParticlePropertyProvider
 *
 * @author Patrick Hellwig
 */

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import hep.physics.yappi.*;

public class CreateTexFile extends HttpServlet {

// FIXME: to be redone in a more XMLWriter way
/*
    PrintWriter out;
    Yappi yappi;
    String searchParameter;
    String lookup4;
    String dummyPara;		// dummy Parameter

    private String Eto10times(String eString)
    {
    	if (eString.lastIndexOf('E') == -1) return eString;
        	String tempstr = "$"+eString.substring(0,eString.indexOf('E'));
        	tempstr = tempstr + "\\cdot 10^{" + eString.substring(eString.indexOf('E')+1)+"}$";
        	return tempstr;
        }

    private String Eto10times(double eString)
    {
        	if (String.valueOf(eString).lastIndexOf('E') == -1) return String.valueOf(eString);
        	String tempstr = "$"+String.valueOf(eString).substring(0,String.valueOf(eString).indexOf('E'));
        	tempstr = tempstr + "\\cdot 10^{" + String.valueOf(eString).substring(String.valueOf(eString).indexOf('E')+1)+"}$";
        	return tempstr;
        }

        private String ErrorPrint(double posError, double negError, String unit)
        {
        	String printString = "";
    	if (posError == negError)
            {
            	if (posError != 0)
            		printString = " $\\pm$ "+Eto10times(posError)+" "+unit;
            	else
            		if (unit != null)
            			printString = unit;
          	}
    	else
            {
            	//printString = "\\array{+"+Eto10times(posError)+"\\\\-"+Eto10times(negError)+"}"+" ");
            	if (posError != 0)
         			printString = " \\begin{scriptsize}$\\begin{array}{l}+"+Eto10times(posError)+"\\\\-"+Eto10times(negError)+"\\end{array}$\\end{scriptsize}"+"\\"+unit;
         		else
         			if (unit != null)
         				printString = unit;
            }
            return printString;
    }

    private void printParticleInfo(ParticleType testp)
    {
      try{
      	out.println("\\begin{tabular}{lll}<br>");
            out.println("\\fbox{$"+testp.getTeXName()+"$}&\\makebox[5.0cm]{}&$$\\\\<br>");
            out.println("\\vspace{0.2cm}$$\\\\<br>");

    	Iterator data2 = testp.getData();
    	while (data2.hasNext())
    	{
    		out.print("&\\multicolumn{2}{l}{");

    		Data data2e = (Data) data2.next();
    		if (testp.getData(data2e.getName()).getPosError() != 0)
    		{
    			out.print(data2e.getTeXName()+" = ("+Eto10times(data2e.getValue()));
           			out.print(ErrorPrint(testp.getData(data2e.getName()).getPosError(),testp.getData(data2e.getName()).getNegError(),") "+testp.getData(data2e.getName()).getUnit()));
           		}
            	else
    		{
    				out.print(data2e.getTeXName()+" = "+Eto10times(data2e.getValue()));
            			out.print(ErrorPrint(testp.getData(data2e.getName()).getPosError(),testp.getData(data2e.getName()).getNegError(),testp.getData(data2e.getName()).getUnit()));
    		}
            	if (testp.getData(data2e.getName()).getScaleFactor() != 0)
            		out.print(" (S = "+testp.getData(data2e.getName()).getScaleFactor()+")");

            	out.println("}\\\\<br>");
    	}
    	out.println("\\end{tabular}<br>");
        	out.println("\\linebreak<br>");

    	out.println("");
    	Iterator decay2 = testp.getDecayChannels();
    	if (decay2.hasNext())
    	{
    		out.println("\\begin{tabular}{llrr}<br>&&Scale factor/&\\\\<br>\\textbf{$"+testp.getTeXName()+"$ DECAY MODES}&Fraction ($\\Gamma_{i}/\\Gamma$)&Confidence level&p\\\\<br>");
    		out.println("\\hline<br>");
            	while (decay2.hasNext())
    		{
    			DecayChannel decay2e = (DecayChannel) decay2.next();
    	        	// print out decay name
    	        	out.print("$");
    	        	Iterator decPart = decay2e.getDecayParticles();
    	        	while (decPart.hasNext())
    	        	{
    	        		DecayProduct decPart2 = (DecayProduct) decPart.next();
    	        		if (decPart2.getType().equals("Particle"))
    	        			out.print(yappi.lookupName(decPart2.getName()).getTeXName());
    	        		else
    	        			out.print("$ "+decPart2.getName()+"$");
    	        	}
    	        	out.print("$&");
            		out.print("("+decay2e.getFraction());
            		//out.print(" \\begin{scriptsize}$\\begin{array}{l}+"+decay2e.getPosError()+"\\\\-"+decay2e.getNegError()+"\\end{array}$\\end{scriptsize}"+")\\%&");
            		out.print(ErrorPrint(decay2e.getPosError(),decay2e.getNegError()," ")+")\\%&");
        			if (decay2e.getScaleFactor()!= 0)
        				out.print("S="+decay2e.getScaleFactor());
        			out.print("&");
            		out.println(decay2e.getP()+" "+decay2e.getPUnit()+"\\\\<BR>");
    		}
        		out.println("\\end{tabular}<br>");
        	}
        	out.println("\\rule{15.0cm}{1mm}<br>");
         } catch (Exception e) {}
    }

    private void displayFamilyTree() {
		try {
            Iterator families = yappi.getFamilies();
			while (families.hasNext())
			{
				Family famdat = (Family) families.next();
				System.out.println(famdat.getName());
				displayFamilyTree(famdat,3);
			}
		} catch (Exception e)
		{
		}
    }

    private void displayFamilyTree(Family fatree, int cha)
    {
          try{
    	Iterator fa = fatree.getFamilies();
    	while (fa.hasNext())
    	{
    		Family famdat = (Family) fa.next();
    		for (int i=0;i<cha;i++) out.print("&nbsp;");
    		out.println("<font color=\"#ff0000\">"+famdat.getName()+"</font><br>");
    		displayFamilyTree(famdat,cha+3);
    	}
         } catch (Exception e) {}

          try{
    	Iterator fa2 = fatree.getParticles();
    	while (fa2.hasNext())
    	{
    		DecayProduct famdat2 = (DecayProduct) fa2.next();
    		for (int i=0;i<cha;i++)
    			out.print("&nbsp;");

    		//ParticleType testpart = yappi.lookupName(famdat2.getName());

    		//out.println("<a href=ParticleLookup?PDGID="+testpart.getData("PDGID").getValue()+">");
    		out.println("<a href=ParticleLookup?Name="+famdat2.getName()+">");
    		out.println(famdat2.getName());
    		out.println(" (PDGID: "+famdat2.getData("PDGID").getValue()+") </a><br>");
    		//out.println(" (PDGID: "+testpart.getData("PDGID").getValue()+") </a><br>");
    	}
        } catch(Exception e) {}
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
            response.setContentType("text/html");
            out = response.getWriter();
            System.out.println("_____________________________________________ new request ");
            System.out.println("Header: "+request.getHeader(""));
            System.out.println("QueryString: "+request.getQueryString());
            System.out.println("Method: "+request.getMethod());
            System.out.println("PathInfo: "+request.getPathInfo());
            System.out.println("PathTranslated: "+request.getPathTranslated());
            System.out.println("RemoteUser: "+request.getRemoteUser());
            System.out.println("RequestURI: "+request.getRequestURI());

    	yappi = new Yappi("/xml/ppml3.xml");

            ////////////////////////////////
            // Determine the URI:
            // 1. ParticleLookup/PDGID/2123
            // 2. ParticleLookup?PDGID=2123
            //

    	lookup4 = null;
    	searchParameter = null;
            if (request.getPathInfo() == null)
            {
            	// 2. ParticleLookup?...
            	if (request.getParameter("PDGID")!=null)
            	{
            		dummyPara = request.getParameter("PDGID").toUpperCase();
            		if (dummyPara.length()>0)
            		{
            		 	searchParameter = dummyPara;
            		    	lookup4 = "PDGID";
            		}
            	}
            	if (request.getParameter("Name")!=null)
            	{
            		dummyPara = request.getParameter("Name").toUpperCase();
            	    	if (dummyPara.length()>0)
            	    	{
            	    		searchParameter = dummyPara;
            	    		lookup4 = "Name";
            	    	}
            	}
            }
            else
            	{
            		// 1. ParticleLookup/PDGID/2123
            		String rawParameter = request.getPathInfo().toUpperCase();
            		if (rawParameter.startsWith("/PDGID/"))
           			{
           				//lookup for PDGID Number
           				// cut out /PDGID/ string
           				dummyPara = rawParameter.substring(7);
           				if (dummyPara.length()>0)
    				{
    					searchParameter = dummyPara;
    					lookup4 = "PDGID";
    				}
           			}
            		if (rawParameter.startsWith("/NAME/"))
           			{
           				//lookup for Name
           				// cut out /Name/ string
           				dummyPara = rawParameter.substring(6);
           				if (dummyPara.length()>0)
           				{
           					searchParameter = dummyPara;
           					lookup4 = "Name";
           					if (searchParameter.equals("All")) lookup4 = "All";
           				}
           			}
            		if (rawParameter.startsWith("/ALL"))
           				lookup4 = "All";

            	}

    	System.out.println("LookUp in XML-Table for "+lookup4+" = "+searchParameter);

            out.println("<html>");
            out.println("<body bgcolor=\"white\">");
            out.println("<head>");

    	if (!((searchParameter == null) | (lookup4 == null)))
    		out.println("<title> Particle LOOKUP: "+lookup4+" = "+searchParameter+"</title>");
    	else
    		out.println("<title> Particle LOOKUP</title>");
            out.println("</head>");
            out.println("<body bgcolor=\"#FFFFFF\">");

    	// note that all links are created to be relative. this
    	// ensures that we can move the web application that this
    	// servlet belongs to to a different place in the url
    	// tree and not have any harmful side effects.

    	Iterator allParticles = yappi.getParticleProvider().getParticles();
    	out.println("\\documentclass[12pt,a4paper]{article}<br>");
    	out.println("\\pagestyle{myheadings}<br>");
    	out.println("\\begin{document}<br>");
    	out.println("\\markright{Particle Properties Summary Table}<br>");
    	out.println("\\noindent<br>");

    	while (lookup4 != null)
    	{
    		ParticleType testp = null;
    		if (lookup4.equals("Name"))
    		{
    			try{
    				testp = yappi.lookupName(searchParameter);
    				lookup4 = null;
    			} catch (Exception e) {testp = null;}
    		}
    		if (lookup4 != null)
    		if (lookup4.equals("PDGID"))
    		{
    			try{
    				testp = yappi.lookupData(lookup4,searchParameter);
    				lookup4 = null;
    			} catch (Exception e) { testp = null;}
    		}
    		if (lookup4 != null)
    		if (lookup4.equals("All"))
    			if (allParticles.hasNext())
    				testp = (ParticleType) allParticles.next();
    			else
    				lookup4 = null;
    		if (testp != null)
    			printParticleInfo(testp);
    		//else
    		//	out.println("\\Large no particle found with <font color=\"#ff0000\">"+lookup4+" = "+searchParameter+"</font><br>");
    	}
    	out.println("\\vspace{0.5cm}<br>");
    	out.println("\\fbox{\\tiny This document was created with YaPPI2TeX\\\\(c)2000 Patrick Hellwig, CERN Summer Student, All Rights Reserved\\\\}<br>");
    	out.println("\\end{document}<br>");
    	out.println("<br>");

            out.println("<h2> Family Structure: </h2>");

           	displayFamilyTree();
*/


    /*        out.println("<hr>");
    	out.println("<h2> Do you want to lookup another particle ? </h2>");
    	out.println("<FORM METHOD=GET ACTION=\"ParticleLookup\">");
    	out.println("Please insert PDGID: <INPUT TYPE=TEXT NAME=\"PDGID\"><P>");
    	out.println("Please insert Name : <INPUT TYPE=TEXT NAME=\"Name\"><P>");
            out.println("<INPUT TYPE=SUBMIT>");

            out.println("</FORM>");*/
 /*
            out.println("</BODY>");
            out.println("</html>");
    }
*/
}



