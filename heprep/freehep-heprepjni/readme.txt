For more info on HepRep see: 

http://heprep.freehep.org


The idea is that you create Java objects from C++ and then
tell the Java objects to write themselves out as an XML file.

For this you need:

1. A java development kit 1.4.x, which is available from
http://java.sun.com

2. The freehep library, available from http://java.freehep.org

3. The C++ part of the freehep library, included in CVS. You need to
compile this with gmake.

Then you will find in directory: hep/graphics/heprep/jni
a file HepRepTestMain.cc which shows how a set of HepReps are made.

You can run this by cd'ing into this directory and running HepRepTest,
which will create a Factory, create HepReps and write several XML files.
