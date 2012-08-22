package hep.physics.yappi.util;

import hep.physics.particle.properties.ParticlePropertyProvider;
import hep.physics.yappi.Data;
import hep.physics.yappi.PDGID;
import hep.physics.yappi.ParticleType;
import hep.physics.yappi.XMLYappi;
import hep.physics.yappi.Yappi;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class YappiParticlePropertyProvider implements ParticlePropertyProvider
{
   private Yappi yappi;
   
   public YappiParticlePropertyProvider(String filename) throws Exception //FixMe: Better exception needed
   {
      yappi = new XMLYappi(filename);
   }
   
   public hep.physics.particle.properties.ParticleType get(int PDGID)
   {
      ParticleType tp = yappi.getParticle(new PDGID(PDGID));
      return new ParticleTypeImplementation(tp);
   }
   
   public Set types()
   {
      Iterator i = yappi.getParticles();
      Set set = new HashSet();
      while (i.hasNext())
      {
         // Fixme: Should not return Entry
         Map.Entry entry = (Map.Entry) i.next();
         set.add(new ParticleTypeImplementation((ParticleType) entry.getValue()));
      }
      return set;
   }
   
   private class ParticleTypeImplementation implements hep.physics.particle.properties.ParticleType
   {
      private ParticleType tp;
      private ParticleTypeImplementation(ParticleType tp)
      {
         this.tp = tp;
      }
      public String getName()
      {
         return tp.getName();
      }
      
      public int getPDGID()
      {
         PDGID pdg = tp.getPDGID();
         return pdg != null ? pdg.getID() : -999;
      }
      
      public int get2xSpin()
      {
         Data data = tp.getData("Spin");
         return data != null ? (int) data.getValue()*2 : -999;
      }
      
      public double getCharge()
      {
         Data data = tp.getData("Charge");
         return data != null ? data.getValue() : -999;
      }
      
      public double getMass()
      {
         Data data = tp.getData("Mass");
         return data != null ? data.getValue() : -999;
      }
      
      public double getWidth()
      {
         Data data = tp.getData("Width");
         return data != null ? data.getValue() : -999;
      }
      
      public String toString()
      {
         return tp.getName()+" (PDGid="+tp.getPDGID()+")";
      }
      public ParticlePropertyProvider getParticlePropertyProvider()
      {
         return YappiParticlePropertyProvider.this;
      }
   }
}
