package hep.physics.yappi.test;

import hep.physics.yappi.*;

public class TestYappi {

    public static void main(String[] args) throws Exception {
        
        XMLYappi yappi = new XMLYappi();
        
        yappi.read("hep/physics/yappi/xml/2000/PDG-Family.xml");
        yappi.read("hep/physics/yappi/xml/2000/PDG-Properties.xml");
//        yappi.readFromXML("hep/physics/yappi/xml/2000/PDG-NormalDecayChannels.xml");
//        yappi.readFromXML("hep/physics/yappi/xml/2000/PDG-RareDecayChannels.xml");
//        yappi.readFromXML("hep/physics/yappi/xml/2000/PDG-LimitDecayChannels.xml");
      
        ParticleType particle = yappi.getParticle("pi0");  
        System.out.println(particle);
    }
}
