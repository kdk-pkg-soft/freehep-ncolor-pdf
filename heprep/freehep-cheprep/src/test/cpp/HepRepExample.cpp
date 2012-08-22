// Copyright 2002-2003, FreeHEP.

/**
 * Example on how to build a geometry in HepRep (version 2).
 *
 * Builds a simplified version of the BaBar detector geometry.
 *
 * This example uses the streamer. We are building the HepRep
 * tree as we go along. The order of building is important,
 * since the tree is instantly streamed to the XML output.
 * It is therefore NOT allowed to hang anything in the tree
 * at a point that has already been written.
 *
 * @author Joseph Perl
 * @author Mark Donszelmann
 */

#include <string>
#include <iostream>
#include <fstream>

#include "HEPREP/HepRep.h"
#include "cheprep/XMLHepRepFactory.h"

using namespace HEPREP;
using namespace cheprep;
using namespace std;

int main(int argc, char** argv) {

    char* fname = (char*)"HepRepExample.heprep";

    // create the factory for streaming to XML
	HepRepFactory* factory = new XMLHepRepFactory();

    // create a HepRep
	HepRep* heprep = factory->createHepRep();

    // create Layers
	string layer = "Detector Geometry";
	heprep->addLayer(layer);

    // create a TypeTree
    HepRepTreeID* treeID = factory->createHepRepTreeID("Geometry", "1.0");
    HepRepTypeTree* typeTree = factory->createHepRepTypeTree(treeID);
    heprep->addTypeTree(typeTree);

    // create Types
    HepRepType* geometryType = factory->createHepRepType(typeTree, "Geometry");
    geometryType->addAttValue("drawAs", string("Cylinder"));
    geometryType->addAttValue("layer", string("Detector Geometry"));

    HepRepType* magnetType = factory->createHepRepType(geometryType, "Magnet Coil");
    magnetType->addAttValue("Color", 1., 1., 1.);

    HepRepType* ecalType = factory->createHepRepType(geometryType, "Electromagnetic Calorimeter");
    ecalType->addAttValue("Color",1.,1.,0.);

    HepRepType* driftChamberType = factory->createHepRepType(geometryType, "Drift Chamber");

    HepRepType* tubeType = factory->createHepRepType(geometryType, "Support Tube");
    tubeType->addAttValue("Color",1.,0.,1.);

    HepRepType* vtxType = factory->createHepRepType(geometryType, "Silicon Vertex Detector");
    vtxType->addAttValue("Color",1.,.2,.2);

    HepRepType* beamType = factory->createHepRepType(geometryType, "Beam Pipe");
    beamType->addAttValue("Color",1.,1.,0.);


    // create an InstanceTree
    HepRepInstanceTree* instanceTree = factory->createHepRepInstanceTree("Simplified BaBar Detector", "Hand Coded", typeTree);
    heprep->addInstanceTree(instanceTree);

    // create Instances
    HepRepInstance* magnet = factory->createHepRepInstance(instanceTree, magnetType);
    magnet->addAttValue("Radius", 143.);
    factory->createHepRepPoint(magnet, 0., 0., -148.);
    factory->createHepRepPoint(magnet, 0., 0., 220.);

    HepRepInstance* ecal = factory->createHepRepInstance(instanceTree, ecalType);
    ecal->addAttValue("Radius", 122.);
    factory->createHepRepPoint(ecal, 0., 0., -144.);
    factory->createHepRepPoint(ecal, 0., 0., 216.);

    HepRepInstance* driftChamber = factory->createHepRepInstance(instanceTree, driftChamberType);

    HepRepInstance* driftChamberOut = factory->createHepRepInstance(driftChamber, driftChamberType);
    driftChamberOut->addAttValue("Color", 0., 1., 0.);
    driftChamberOut->addAttValue("Radius", 78.);
    factory->createHepRepPoint(driftChamberOut, 0., 0., -102.);
    factory->createHepRepPoint(driftChamberOut, 0., 0., 176.);

    HepRepInstance* driftChamberIn = factory->createHepRepInstance(driftChamber, driftChamberType);
    driftChamberIn->addAttValue("Color", 0., 1., 1.);
    driftChamberIn->addAttValue("Radius", 24.);
    factory->createHepRepPoint(driftChamberIn, 0., 0., -102.);
    factory->createHepRepPoint(driftChamberIn, 0., 0., 176.);

    HepRepInstance* tube = factory->createHepRepInstance(instanceTree, tubeType);
    tube->addAttValue("Radius", 21.);
    factory->createHepRepPoint(tube, 0., 0., -44.);
    factory->createHepRepPoint(tube, 0., 0., 78.);

    HepRepInstance* vtx = factory->createHepRepInstance(instanceTree, vtxType);
    vtx->addAttValue("Radius", 15.);
    factory->createHepRepPoint(vtx, 0., 0., -13.5);
    factory->createHepRepPoint(vtx, 0., 0., 26.);

    HepRepInstance* beam = factory->createHepRepInstance(instanceTree, beamType);
    beam->addAttValue("Radius", 2.5);
    factory->createHepRepPoint(beam, 0., 0., -144.);
    factory->createHepRepPoint(beam, 0., 0., 216.);

    // create the writer
    ostream* out = new ofstream(fname);
	HepRepWriter* writer = factory->createHepRepWriter(out, false, false);
    writer->write(heprep, fname);
    writer->close();

    delete writer;
    delete heprep;
    delete factory;

    return 0;
}
