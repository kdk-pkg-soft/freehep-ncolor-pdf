from hep.aida import *
from java.lang import *
from java.util import *

true = Boolean("true");
false = Boolean("false");

factory = IAnalysisFactory.create();
tree    = factory.createTreeFactory().create("UsingJAIDAFromJython.aida","xml",false,true);
hf      = factory.createHistogramFactory(tree);

tree.mkdir("/Histograms");
tree.cd("/Histograms");

h1 = hf.createHistogram1D("Histogram 1D",50,-3,3);
h2 = hf.createHistogram2D("Histogram 2D",40,-3,3,40,-3,3);
        
tree.mkdir("/Clouds");
tree.cd("/Clouds");

c1 = hf.createCloud1D("Cloud 1D");
c2 = hf.createCloud2D("Cloud 2D");

page1 = factory.createPlotterFactory().create("Page1");

page1.show();
page1.createRegions(2,2);

page1.region(0).plot(h1);
page1.region(1).plot(h2);
page1.region(2).plot(c1);
page1.region(3).plot(c2);

r = Random()

def fill():
          for i in range(10000):
                h1.fill(r.nextGaussian())
                h2.fill(r.nextGaussian(),r.nextGaussian())
                c1.fill(r.nextGaussian())
                c2.fill(r.nextGaussian(),r.nextGaussian())

fill()
tree.commit();