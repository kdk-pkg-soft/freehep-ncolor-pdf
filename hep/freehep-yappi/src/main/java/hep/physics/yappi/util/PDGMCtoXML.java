// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi.util;

import java.io.*;

public class PDGMCtoXML 
{
	static int PDGID = 0;
	static double Pmass = 0;
	static double PMPErr = 0;
	static double PMMErr = 0;
	static String PName = "";
	static double Pwidth = 0;
	static double PWPErr = 0;
	static double PWMErr = 0;
	static BufferedWriter outfile;

	
	// Find out the monte carlo number 0..3
	protected static int getIDNumber(int i, String st)
	{
		String str1 = "";
		if (i > 3) return 0;
		if (st.trim()== " ") return 0;
		for (int j=0;j<=7;j++)
		{
			char chr = st.charAt(j+i*8+1);
			str1 += chr;
		}
		str1 = str1.trim();
		//System.out.println(str1);
		int wert = 0;
		try {wert = Integer.parseInt(str1);}
		catch (Exception e) {}
		return wert;
	}

	// Find out the value-field
	protected static double getvalue(String st)
	{
		String str1 = "";
		for (int j=0;j<=14;j++)
		{
			char chr = st.charAt(j+33);
			str1 += chr;
		}
		str1 = str1.trim();
		//System.out.println(str1);
		double wert = 0;
		try {wert = new Double(str1).doubleValue();}
		catch (Exception e) {}
		return wert;
	}
	
	// Find out the error
	protected static double geterror(String st, int i)
	{
		String str1 = "";
		for (int j=0;j<=8;j++)
		{
			char chr = st.charAt(j+49+i*9);
			str1 += chr;
		}
		str1 = str1.trim();
		//System.out.println(str1);
		double wert = 0;
		try {wert = new Double(str1).doubleValue();}
		catch (Exception e) {}
		return wert;
	}
	
	// Find out the name
	protected static String getname(String st)
	{
		String str1 = "";
		char chr = 'x';
		for (int j=0;chr!=' ';j++)
		{
			chr = st.charAt(j+67);
			//System.out.println(chr);
			str1 += chr;
		}
		str1 = str1.trim();
		//System.out.println(str1);
		return str1;
	}
	
	protected static void writeinfo()
	{
		try 
		{	
 		outfile.write("<Particle Name=\""+PName+"\">");outfile.newLine();
 		outfile.write("  <data Name=\"PDGID\" Value=\""+PDGID+"\" />");outfile.newLine();  
		outfile.write("  <data Name=\"Mass\" Value=\""+Pmass+"\" PError=\""+PMPErr+"\" MError=\""+PMMErr+"\"/>");outfile.newLine();  
	     	outfile.write("  <data Name=\"Width\" Value=\""+Pwidth+"\" PError=\""+PWPErr+"\" MError=\""+PWMErr+"\"/>");outfile.newLine();  
  		outfile.write("</Particle>");outfile.newLine();
		} catch (IOException e) 
		{
			System.out.println("Error .. "+e.toString());
		}		
	}
	
	public static void main(String[] arguments)
	{
		
		try 
		{	
			FileReader myfile = new FileReader("hep/physics/yappi/util/garren_98.mc");
			BufferedReader infile = new BufferedReader(myfile);
			
			FileWriter mywriter = new FileWriter("hep/physics/yappi/util/garren_98_mc.xml");
			outfile = new BufferedWriter(mywriter);
			outfile.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");outfile.newLine();
			outfile.write("<PPML Source=\"PDGConv\">");outfile.newLine();
			outfile.write(" ");outfile.newLine();
			boolean eof = false;
			while (!eof) 
			{	
				String line = infile.readLine();
				if (line == null) 
					eof = true;
				else
				{
				  line = line.trim();
				  if (line.charAt(0) == 'M') 
				  {
				  	if (PDGID != 0)
				  		{ //write the XML-info 
							writeinfo();
				  		}
				  	//System.out.println(line);
				  	PDGID = getIDNumber(0,line);
					Pmass = getvalue(line);
					PMPErr = geterror(line,0);
					PMMErr = geterror(line,1);
					PName = getname(line);
				  }
				  else if (line.charAt(0) == 'W') 
				  {	
				  	//System.out.println(line);
					Pwidth = getvalue(line);
					PWPErr = geterror(line,0);
					PWMErr = geterror(line,1);
				  }
				}
			}
			writeinfo();
			outfile.write("</PPML>");outfile.newLine();
			outfile.close();
			infile.close();
		} catch (IOException e) 
		{
			System.out.println("Error .. "+e.toString());
		}		
	}
}
