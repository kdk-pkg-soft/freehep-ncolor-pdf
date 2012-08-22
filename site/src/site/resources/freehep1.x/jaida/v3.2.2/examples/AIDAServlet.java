import hep.aida.*;
import hep.aida.ref.plotter.PlotterUtilities;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AIDAServlet extends HttpServlet
{
   private IPlotter plotter;
   /** Initializes the servlet.
    */
   public void init(ServletConfig config) throws ServletException
   {
      super.init(config);
      
      IAnalysisFactory af = IAnalysisFactory.create();
      ITree tree = af.createTreeFactory().create();
      IHistogramFactory hf = af.createHistogramFactory(tree);

      IHistogram1D h1 = hf.createHistogram1D("Test", 50, -4, 4);
      Random r = new Random();
      for (int i = 0; i < 10000; i++)
         h1.fill(r.nextGaussian());

      plotter = af.createPlotterFactory().create("Test");
      plotter.region(0).plot(h1); 
   }
   
   public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
   {      
      ServletOutputStream out = res.getOutputStream();  
      res.setContentType("image/png");
      PlotterUtilities.writeToFile(plotter,out,"png",null);
      out.close();
   }   
}
